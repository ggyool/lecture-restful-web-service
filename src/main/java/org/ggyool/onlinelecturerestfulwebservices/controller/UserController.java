package org.ggyool.onlinelecturerestfulwebservices.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.ggyool.onlinelecturerestfulwebservices.domain.User;
import org.ggyool.onlinelecturerestfulwebservices.domain.UserNotFoundException;
import org.ggyool.onlinelecturerestfulwebservices.service.UserDaoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserDaoService userDaoService;
    private final ObjectMapper objectMapper;

    public UserController(UserDaoService userDaoService, ObjectMapper objectMapper) {
        this.userDaoService = userDaoService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/users")
    public ResponseEntity<MappingJacksonValue> retrieveAllUsers() {
        List<EntityModel<User>> result = new ArrayList<>();
        List<User> users = userDaoService.findAll();

        for (User user : users) {
            EntityModel<User> entityModel = EntityModel.of(user);
            entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withSelfRel());
            result.add(entityModel);
        }

        // apply filters
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        CollectionModel<EntityModel<User>> collectionModel = CollectionModel.of(result, linkTo(methodOn(this.getClass()).retrieveAllUsers()).withSelfRel());

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(collectionModel);
        mappingJacksonValue.setFilters(filterProvider);

        return ResponseEntity.ok(
            mappingJacksonValue
        );
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id) throws JsonProcessingException {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));

        return applyFilter(model);
    }

    private MappingJacksonValue applyFilter(EntityModel<User> model) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(model);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @PostMapping("users")
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        // 기존에 내가 하던 방식 - 위의 방식은 전체 url이 다 나오고 아래는 api 관련 부분만 나옴
//        String uriValue = "/users/" +  savedUser.getId();
//        URI uri2 = URI.create(uriValue);
        return ResponseEntity.created(uri)
                .build();
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable int id, @Valid @RequestBody User user) {
        User updatedUser = userDaoService.update(id, user);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userDaoService.deleteById(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    public abstract class CustomMixin {
        @JsonProperty
        private String value;
        @JsonProperty
        private String filter;
    }
}
