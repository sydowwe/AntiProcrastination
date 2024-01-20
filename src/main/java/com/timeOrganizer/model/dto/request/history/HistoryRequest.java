package com.timeOrganizer.model.dto.request.history;

import com.timeOrganizer.helper.MyIntTime;
import com.timeOrganizer.model.dto.request.IRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class HistoryRequest implements IRequest {
    private long activityId;
    private String startTimestamp;
    private MyIntTime length;
}
