package com.timeOrganizer.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdResponse implements IResponse{
    private long id;
}
