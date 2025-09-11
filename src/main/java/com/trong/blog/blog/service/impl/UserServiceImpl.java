package com.trong.blog.blog.service.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.trong.blog.blog.domain.User;
import com.trong.blog.blog.domain.dto.response.user.ResCreateUserDTO;
import com.trong.blog.blog.domain.dto.response.user.ResUserDTO;
import com.trong.blog.blog.repository.UserRepository;
import com.trong.blog.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public void deleteUserById(long id) {
        if (this.userRepository.findById(id) != null) {
            this.userRepository.deleteById(id);
        }
    }

    public Optional<User> fetchUserById(long id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> fetchUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Boolean checkExistedEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        return new ResCreateUserDTO(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }

    public ResUserDTO convertToResUserDTO(User user) {

        // Role_ role = new Role_();
        // if (user.getRole() != null) {
        // role.setId(user.getRole().getId());
        // role.setName(user.getRole().getName());
        // }
        return new ResUserDTO(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }

    public void updateRefreshToken(String refreshToken, String email) {
        // User user = this.userRepository.findByEmail(email).isPresent() ?
        // this.userRepository.findByEmail(email).get()
        // : null;
        // if (user != null) {
        // user.setRefreshToken(refreshToken);
        // this.userRepository.save(user);
        // }
    }

    public User fetchUserByRefreshToken(String refreshToken, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(refreshToken, email);
    }
}
