package com.trong.blog.blog.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.trong.blog.blog.domain.User;
import com.trong.blog.blog.domain.dto.response.user.ResCreateUserDTO;
import com.trong.blog.blog.domain.dto.response.user.ResUserDTO;

@Service
public interface UserService {

    public User saveUser(User user);

    public void deleteUserById(long id);

    public Optional<User> fetchUserByEmail(String email);

    public Optional<User> fetchUserById(long id);

    public Boolean checkExistedEmail(String email);

    public ResCreateUserDTO convertToResCreateUserDTO(User user);

    public ResUserDTO convertToResUserDTO(User user);

    public void updateRefreshToken(String refreshToken, String email);

    public User fetchUserByRefreshToken(String refreshToken, String email);
}
