package com.timeOrganizer.model.dto.request.history;

import com.timeOrganizer.helper.MyIntTime;
import com.timeOrganizer.model.dto.request.extendable.ActivityIdRequest;
import lombok.Getter;

import java.time.Instant;

@Getter
@SuppressWarnings("unused")
public class HistoryRequest extends ActivityIdRequest
{
	private Instant startTimestamp;
    private MyIntTime length;
}
