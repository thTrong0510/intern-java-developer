package com.trong.blog.blog.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trong.blog.blog.domain.Post;
import com.trong.blog.blog.domain.User;
import com.trong.blog.blog.repository.PostRepository;
import com.trong.blog.blog.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(UserRepository userRepository,
            PostRepository postRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> Start init database");

        long userCount = this.userRepository.count();
        long postCount = this.postRepository.count();

        // User
        if (userCount == 0) {
            User adminUser = new User();
            adminUser.setEmail("test@gmail.com");
            adminUser.setName("I'm test user");
            adminUser.setPassword(this.passwordEncoder.encode("123456"));

            this.userRepository.save(adminUser);
        }

        // Nếu chưa có post nào -> tạo 10 post mẫu
        if (postCount == 0) {
            List<Post> posts = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Post post = new Post();
                post.setTitle("Bài Post số: " + i);
                post.setContent("Nội dung của bài Post số: " + i);
                User user = this.userRepository.findById(Long.valueOf(1)).get();
                post.setUser(user);
                posts.add(post);
            }
            this.postRepository.saveAll(posts);
        }

        System.out.println(">>> End init database");
    }
}
