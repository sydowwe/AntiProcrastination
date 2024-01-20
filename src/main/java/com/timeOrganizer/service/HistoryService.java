package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.HistoryMapper;
import com.timeOrganizer.model.dto.request.history.HistoryFilterRequest;
import com.timeOrganizer.model.dto.request.history.HistoryRequest;
import com.timeOrganizer.model.dto.response.history.HistoryResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.History;
import com.timeOrganizer.repository.HistorySpecifications;
import com.timeOrganizer.repository.IHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HistoryService extends MyService<History ,IHistoryRepository,HistoryRequest,HistoryResponse,HistoryMapper> implements IHistoryService {
    private final ActivityService activityService;
    @Autowired
    public HistoryService(IHistoryRepository repository, HistoryMapper mapper, UserService userService, ActivityService activityService) {
        super(repository, mapper, userService);
        this.activityService = activityService;
    }
    @Override
    public List<HistoryResponse> filter(HistoryFilterRequest filterRequest) {
        Specification<History> spec = HistorySpecifications.withFilter(
                filterRequest.getActivityId(),
                filterRequest.getRoleId(),
                filterRequest.getCategoryId(),
                filterRequest.getIsFromToDoList(),
                filterRequest.getIsUnavoidable(),
                filterRequest.getDateFrom(),
                filterRequest.getDateTo(),
                filterRequest.getHoursBack());
        return this.mapper.convertToFullResponseList(this.repository.findAll(spec));
    }

    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(HistoryRequest request) {
        return Map.of("activity",activityService.getReference(request.getActivityId()));
    }
}
