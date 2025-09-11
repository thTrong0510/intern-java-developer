package com.trong.blog.blog.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trong.blog.blog.domain.Post;
import com.trong.blog.blog.domain.User;
import com.trong.blog.blog.domain.dto.ResultPaginationDTO;
import com.trong.blog.blog.domain.dto.attach.General_;
import com.trong.blog.blog.domain.dto.attach.Meta;
import com.trong.blog.blog.domain.dto.response.post.ResPostDTO;
import com.trong.blog.blog.repository.PostRepository;
import com.trong.blog.blog.service.PostService;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post, User user) {
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public ResultPaginationDTO getAllPosts(Pageable pageable) {
        Page<Post> pagePost = this.postRepository.findAll(pageable);

        Meta meta = new Meta();

        // lấy từ pageable tức là lấy từ mấy params
        meta.setPage(pageable.getPageNumber() + 1);// lúc đầu đã -1
        meta.setPageSize(pageable.getPageSize());

        // này là lấy khi đã xún database rồi truy vấn lên lại
        meta.setPages(pagePost.getTotalPages());
        meta.setTotal(pagePost.getTotalElements());

        ResultPaginationDTO result = new ResultPaginationDTO();

        result.setResult(convertToResPostDTO(pagePost.getContent()));
        result.setMeta(meta);

        return result;
    }

    @Override
    public ResultPaginationDTO fetchPostsByUser(User user, Pageable pageable) {

        Page<Post> pagePost = this.postRepository.findByUser(user, pageable);

        Meta meta = new Meta();

        // lấy từ pageable tức là lấy từ mấy params
        meta.setPage(pageable.getPageNumber() + 1);// lúc đầu đã -1
        meta.setPageSize(pageable.getPageSize());

        // này là lấy khi đã xún database rồi truy vấn lên lại
        meta.setPages(pagePost.getTotalPages());
        meta.setTotal(pagePost.getTotalElements());

        ResultPaginationDTO result = new ResultPaginationDTO();

        result.setResult(convertToResPostDTO(pagePost.getContent()));
        result.setMeta(meta);

        return result;

    }

    @Override
    public Optional<Post> fetchPostById(Long id) {
        return this.postRepository.findById(id);
    }

    @Override
    public Post updatePost(Post updatedPost) {
        Post existingPost = this.postRepository.findById(updatedPost.getId()).get();

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        return this.postRepository.save(existingPost);
    }

    @Override
    public void deletePost(Long id) {
        this.postRepository.deleteById(id);
    }

    @Override
    public List<ResPostDTO> convertToResPostDTO(List<Post> posts) {
        List<ResPostDTO> postDTOs = posts.stream()
                .map(post -> new ResPostDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        new General_(post.getUser().getId(), post.getUser().getName())))
                .toList();
        return postDTOs;
    }
}
