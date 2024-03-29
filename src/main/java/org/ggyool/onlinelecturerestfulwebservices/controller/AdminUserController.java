package org.ggyool.onlinelecturerestfulwebservices.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.ggyool.onlinelecturerestfulwebservices.domain.User;
import org.ggyool.onlinelecturerestfulwebservices.domain.UserNotFoundException;
import org.ggyool.onlinelecturerestfulwebservices.domain.UserV2;
import org.ggyool.onlinelecturerestfulwebservices.service.UserDaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminUserController {

    private final UserDaoService userDaoService;

    public AdminUserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping(value = "/v1/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = userDaoService.findAll();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping("/v2/users/{id}")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "name", "joinDate", "ssn", "grade"
        );
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping(value = "/users/{id}", params = "version=1")
    public MappingJacksonValue retrieveUserV1WithParam(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping(value = "/users/{id}", params = "version=2")
    public MappingJacksonValue retrieveUserV2WithParam(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "name", "joinDate", "ssn", "grade"
        );
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    public MappingJacksonValue retrieveUserV1WithHeader(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    public MappingJacksonValue retrieveUserV2WithHeader(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "name", "joinDate", "ssn", "grade"
        );
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1WithMime(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2WithMime(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "name", "joinDate", "ssn", "grade"
        );
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }
}
