package com.timeOrganizer.model.dto.request;

import com.timeOrganizer.helper.MyIntTime;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class HistoryRequest implements IRequest{
    private long activityId;
    private String startTimestamp;
    private MyIntTime length;
}
