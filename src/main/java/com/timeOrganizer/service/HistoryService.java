package com.timeOrganizer.service;

import com.timeOrganizer.exception.HistoryNotFoundException;
import com.timeOrganizer.model.dto.request.HistoryRequest;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.History;
import com.timeOrganizer.repository.IHistoryRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class HistoryService implements IHistoryService{
    private final ActivityService activityService;
    private final IHistoryRepository historyRepository;

    @Autowired
    public HistoryService(IHistoryRepository historyRepository, ActivityService activityService) {
        this.historyRepository = historyRepository;
        this.activityService = activityService;
    }
    @Override
    public History addActivityToHistory(HistoryRequest historyRequest) {
        Activity activity = activityService.getActivityById(historyRequest.getActivityId());
        LocalDate date = LocalDate.parse(historyRequest.getDate());
        var timeOfStartObj = historyRequest.getTimeOfStart();
        LocalTime timeOfStart = (LocalTime.of(timeOfStartObj.getHour(),timeOfStartObj.getMinute(),timeOfStartObj.getSecond()));
        var lengthObj = historyRequest.getLength();
        LocalTime length = LocalTime.of(lengthObj.getHour(), lengthObj.getMinute(),lengthObj.getSecond());
        History history = new History(
                activity.getName(),
                activity,
                date,
                timeOfStart,
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
            history.setActivityName(activity.getName());
        }
        if (historyRequest.getTimeOfStart() != null) {
            var timeOfStart = historyRequest.getTimeOfStart();
            history.setTimeOfStart(LocalTime.of(timeOfStart.getHour(),timeOfStart.getMinute(),timeOfStart.getSecond()));
        }
        if (historyRequest.getLength() != null) {
            var length = historyRequest.getLength();
            history.setLength(LocalTime.of(length.getHour(), length.getMinute(),length.getSecond()));
        }
        return historyRepository.save(history);
    }

    @Override
    public History getHistoryById(Long id) {
        return historyRepository.findById(id)
                .orElseThrow(() -> new HistoryNotFoundException(id));
    }

    @Override
    public List<History> getAllActivities() {
        return historyRepository.findAll();
    }
}
