package org.ggyool.onlinelecturerestfulwebservices.controller;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import org.ggyool.onlinelecturerestfulwebservices.domain.Post;
import org.ggyool.onlinelecturerestfulwebservices.domain.User;
import org.ggyool.onlinelecturerestfulwebservices.domain.UserNotFoundException;
import org.ggyool.onlinelecturerestfulwebservices.repository.PostRepository;
import org.ggyool.onlinelecturerestfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequestMapping("/jpa")
@RestController
public class UserJpaController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserJpaController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("ID[%s] not found", id)));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .build();

    }

    @Transactional
    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable int id) {
        User savedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("ID[%s] not found", id)));

        savedUser.setName(user.getName());
        return savedUser;
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id) {
        User savedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("ID[%s] not found", id)));
        return savedUser.getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable int id) {
        User savedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("ID[%s] not found", id)));

        post.setUser(savedUser);
        Post savedPost = postRepository.save(post);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .build();
    }
}
