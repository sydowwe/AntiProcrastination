package com.timeOrganizer.model.dto.response.history;

import com.timeOrganizer.helper.MyIntTime;
import com.timeOrganizer.model.dto.response.activity.ActivityResponse;
import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class HistoryResponse extends IdResponse {
    private ActivityResponse activity;
    private Instant startTimestamp;
    private MyIntTime length;
	private Instant endTimestamp;
}
