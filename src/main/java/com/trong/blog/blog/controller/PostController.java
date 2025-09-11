package com.trong.blog.blog.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trong.blog.blog.domain.Post;
import com.trong.blog.blog.domain.User;
import com.trong.blog.blog.domain.dto.ResultPaginationDTO;
import com.trong.blog.blog.service.PostService;
import com.trong.blog.blog.service.UserService;
import com.trong.blog.blog.util.SecurityUtil;
import com.trong.blog.blog.util.annotation.ApiMessage;
import com.trong.blog.blog.util.exception.IdInvalidException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/posts")
    @ApiMessage("Create a post")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : null;
        User user = this.userService.fetchUserByEmail(email).get();
        return ResponseEntity.ok(postService.createPost(post, user));
    }

    @GetMapping("/posts")
    @ApiMessage("get all posts with pagination")

    public ResponseEntity<ResultPaginationDTO> getAllPosts(@RequestParam("page") Optional<String> pageOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        String sPage = pageOptional.isPresent() ? pageOptional.get() : "";
        String sPageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : "";
        int page = Integer.parseInt(sPage) - 1;
        int pageSize = Integer.parseInt(sPageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @GetMapping("/post/{id}")
    @ApiMessage("get a post")
    public ResponseEntity<Post> fetchPostById(@PathVariable Long id) throws IdInvalidException {
        Optional<Post> post = this.postService.fetchPostById(id);
        if (post.isEmpty()) {
            throw new IdInvalidException("Post: " + id + " not found");
        }
        return ResponseEntity.ok(post.get());
    }

    @GetMapping("/user/posts")
    @ApiMessage("get all post with pagination by user")
    public ResponseEntity<ResultPaginationDTO> fetchPostsByUser(@RequestParam("page") Optional<String> pageOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) throws IdInvalidException {

        String sPage = pageOptional.isPresent() ? pageOptional.get() : "";
        String sPageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : "";
        int page = Integer.parseInt(sPage) - 1;
        int pageSize = Integer.parseInt(sPageSize);
        Pageable pageable = PageRequest.of(page, pageSize);

        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : null;
        User user = this.userService.fetchUserByEmail(email).get();

        return ResponseEntity.ok(postService.fetchPostsByUser(user, pageable));
    }

    // Update
    @PutMapping("/posts")
    @ApiMessage("Update a post")

    public ResponseEntity<Post> updatePost(@RequestBody Post post) throws IdInvalidException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : null;

        Optional<Post> postDb = this.postService.fetchPostById(post.getId());
        if (postDb.isEmpty()) {
            throw new IdInvalidException("Post: " + post.getId() + " isn't exist");
        }
        User user = this.userService.fetchUserById(postDb.get().getUser().getId()).get();
        if (!email.equals(user.getEmail())) {
            throw new IdInvalidException("You have not permission");
        }
        return ResponseEntity.ok(postService.updatePost(post));
    }

    // Delete
    @DeleteMapping("/posts/{id}")
    @ApiMessage("delete a post")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) throws IdInvalidException {
        Optional<Post> post = this.postService.fetchPostById(id);
        if (post.isEmpty()) {
            throw new IdInvalidException("Post: " + id + " not found");
        }

        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : null;
        User user = this.userService.fetchUserById(post.get().getUser().getId()).get();
        if (!email.equals(user.getEmail())) {
            throw new IdInvalidException("You have not permission");
        }
        this.postService.deletePost(id);
        return ResponseEntity.ok(null);
    }
}
