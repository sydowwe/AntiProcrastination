package com.timeOrganizer.model.dto.request.history;

import com.timeOrganizer.helper.MyIntTime;
import com.timeOrganizer.model.dto.request.extendable.IRequest;
import lombok.Getter;

import java.time.Instant;

@Getter
@SuppressWarnings("unused")
public class HistoryRequest implements IRequest {
    private long activityId;
	private Instant startTimestamp;
    private MyIntTime length;
}
