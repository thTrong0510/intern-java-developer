package com.trong.blog.blog.domain.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public class ReqCreateUserDTO {

    private String name;
    @NotBlank(message = "Email can not be blank")

    private String email;
    @NotBlank(message = "Password can not be blank")
    private String password;

    public ReqCreateUserDTO() {
    }

    public ReqCreateUserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
