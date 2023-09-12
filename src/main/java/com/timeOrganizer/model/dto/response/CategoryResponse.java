package com.timeOrganizer.model.dto.response;

import lombok.Data;


@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String text;
    private String color;
    private String icon;
}