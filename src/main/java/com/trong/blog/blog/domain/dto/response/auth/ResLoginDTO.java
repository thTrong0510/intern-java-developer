package com.trong.blog.blog.domain.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trong.blog.blog.domain.dto.attach.auth.UserLogin_;

public class ResLoginDTO {
    @JsonProperty("access_token")
    private String accessToken;
    private UserLogin_ user;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUser(UserLogin_ user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public UserLogin_ getUser() {
        return user;
    }

    public ResLoginDTO() {
    }

    public ResLoginDTO(String accessToken, UserLogin_ user) {
        this.accessToken = accessToken;
        this.user = user;
    }

}
