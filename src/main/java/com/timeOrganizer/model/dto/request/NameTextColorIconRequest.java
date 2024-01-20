package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class NameTextColorIconRequest extends TextColorRequest {
    private String name;
    private String icon;
}
