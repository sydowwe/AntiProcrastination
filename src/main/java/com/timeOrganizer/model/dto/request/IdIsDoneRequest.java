package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class IdIsDoneRequest extends IdRequest{
    private boolean isDone;
}
