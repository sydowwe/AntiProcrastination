package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class NameTextColorIconRequest implements IRequest {
    private String name;
    private String text;
    private String color;
    private String icon;
}
