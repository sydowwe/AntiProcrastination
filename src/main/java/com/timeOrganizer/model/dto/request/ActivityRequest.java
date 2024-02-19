package com.timeOrganizer.model.dto.request;

import com.timeOrganizer.model.dto.request.extendable.NameTextRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class ActivityRequest extends NameTextRequest {
    private Boolean isOnToDoList;
    private boolean isUnavoidable;
    private long roleId;
    private long categoryId;
}
