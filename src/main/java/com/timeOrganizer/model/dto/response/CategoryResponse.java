package com.timeOrganizer.model.dto.response;

import com.timeOrganizer.model.dto.response.extendable.NameTextColorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CategoryResponse extends NameTextColorResponse {
    private String icon;
}