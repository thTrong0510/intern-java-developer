package com.trong.blog.blog.domain.dto.response.post;

import com.trong.blog.blog.domain.dto.attach.General_;

public class ResPostDTO {
    private Long id;
    private String title;
    private String content;
    private General_ user;

    public ResPostDTO() {
    }

    public ResPostDTO(Long id, String title, String content, General_ user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public General_ getuser() {
        return user;
    }

    public void setuser(General_ user) {
        this.user = user;
    }

}
