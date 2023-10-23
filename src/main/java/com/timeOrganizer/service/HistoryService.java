package com.timeOrganizer.service;

import com.timeOrganizer.exception.HistoryNotFoundException;
import com.timeOrganizer.helper.MyIntTime;
import com.timeOrganizer.model.dto.request.HistoryRequest;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.History;
import com.timeOrganizer.repository.IHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService implements IHistoryService{
    private final IHistoryRepository historyRepository;
    private final ActivityService activityService;

    @Override
    public History addActivityToHistory(HistoryRequest historyRequest) {
        Activity activity = activityService.getActivityById(historyRequest.getActivityId());
        var lengthObj = historyRequest.getLength();
        LocalTime length = LocalTime.of(lengthObj.getHours(), lengthObj.getMinutes(),lengthObj.getSeconds());
        ZonedDateTime start = ZonedDateTime.parse(historyRequest.getStartTimestamp());
        History history = new History(
                activity,
                start,
                length
        );
        return historyRepository.save(history);
    }

    @Override
    public void deleteHistoryById(@NotNull Long id) {
        historyRepository.deleteById(id);
    }
    @Override
    public History updateHistoryById(Long id,@NotNull HistoryRequest historyRequest) {

        History history = this.getHistoryById(id);

        if (historyRequest.getActivityId() != null && activityService.isActivityPresent(historyRequest.getActivityId())) {
            Activity activity = activityService.getActivityById(historyRequest.getActivityId());
            history.setActivity(activity);
        }
        if (historyRequest.getStartTimestamp() != null) {
            history.setStart(ZonedDateTime.parse(historyRequest.getStartTimestamp()));
        }
        MyIntTime length = historyRequest.getLength();
        history.setLength(LocalTime.of(length.getHours(), length.getMinutes(),length.getSeconds()));

        return historyRepository.save(history);
    }

    @Override
    public History getHistoryById(Long id) {
        return historyRepository.findById(id)
                .orElseThrow(() -> new HistoryNotFoundException(id));
    }

    @Override
    public List<History> getAllRecords() {
        return historyRepository.findAll();
    }
    @Override

    public List<History> getLastXHoursRecords(ZonedDateTime startFrom,long hoursBack) {

        return historyRepository.getHistoriesByStartBetweenOrderByStart(startFrom.minusHours(hoursBack),startFrom.plusHours(24));
    }
}
