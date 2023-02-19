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

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

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
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Time length = new Time(historyRequest.getLength());

        Timestamp timeOfStart = Timestamp.valueOf(historyRequest.getTimeOfStart());
        History history = new History(
                activity.getName(),
                activity,
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
            history.setTimeOfStart(Timestamp.valueOf(historyRequest.getTimeOfStart()));
        }
        if (historyRequest.getLength() != null) {
            history.setLength(new Time(historyRequest.getLength()));
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
