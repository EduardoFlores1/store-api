package com.api.shops.request.category;

public class AddCategoryRequest {
    private String name;

    public AddCategoryRequest(String name) {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
