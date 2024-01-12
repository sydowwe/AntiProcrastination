package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class IdIsDoneRequest implements IRequest{
    private long id;
    private boolean isDone;
}
