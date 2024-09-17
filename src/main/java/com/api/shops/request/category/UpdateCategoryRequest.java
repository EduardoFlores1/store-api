package com.api.shops.request.category;

public class UpdateCategoryRequest {
    private Long id;
    private String name;

    public UpdateCategoryRequest() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
