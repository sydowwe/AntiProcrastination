package com.timeOrganizer.model.groupedData;

import com.timeOrganizer.model.entity.History;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;


@AllArgsConstructor
@Data
public class HistoryGroupedByDate {
    private ZonedDateTime date;
    private List<History> historyList;
}
