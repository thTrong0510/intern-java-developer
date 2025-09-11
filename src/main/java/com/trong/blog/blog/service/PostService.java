package com.trong.blog.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trong.blog.blog.domain.Post;
import com.trong.blog.blog.domain.User;
import com.trong.blog.blog.domain.dto.ResultPaginationDTO;
import com.trong.blog.blog.domain.dto.response.post.ResPostDTO;

@Service
public interface PostService {

    Post createPost(Post post, User user);

    ResultPaginationDTO getAllPosts(Pageable pageable);

    List<Post> fetchPostsByUser(User user);

    Optional<Post> fetchPostById(Long id);

    Post updatePost(Post updatedPost);

    void deletePost(Long id);

    List<ResPostDTO> convertToResPostDTO(List<Post> posts);
}
