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
            User testUser = new User();
            testUser.setEmail("test@gmail.com");
            testUser.setName("I'm test user");
            testUser.setPassword(this.passwordEncoder.encode("123456"));

            User test_User = new User();
            test_User.setEmail("test_@gmail.com");
            test_User.setName("I'm test_ user");
            test_User.setPassword(this.passwordEncoder.encode("123456"));

            this.userRepository.save(testUser);
            this.userRepository.save(test_User);
        }

        // Nếu chưa có post nào -> tạo 10 post mẫu
        if (postCount == 0) {
            List<Post> posts = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Post post = new Post();
                post.setTitle("Bài Post số: " + i);
                post.setContent("Nội dung của bài Post số: " + i);
                User user = null;
                if (i % 2 == 0) {
                    user = this.userRepository.findById(Long.valueOf(1)).get();
                } else {
                    user = this.userRepository.findById(Long.valueOf(2)).get();
                }
                post.setUser(user);
                posts.add(post);
            }
            this.postRepository.saveAll(posts);
        }

        System.out.println(">>> End init database");
    }
}
