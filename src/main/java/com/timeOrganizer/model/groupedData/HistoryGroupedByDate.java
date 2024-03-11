package com.timeOrganizer.model.groupedData;

import com.timeOrganizer.model.entity.History;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;


@AllArgsConstructor
@Data
public class HistoryGroupedByDate {
    private Instant date;
    private List<History> historyList;
}
