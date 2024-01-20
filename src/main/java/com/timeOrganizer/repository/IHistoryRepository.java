package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.History;
import com.timeOrganizer.model.groupedData.HistoryGroupedByDate;
import org.intellij.lang.annotations.Language;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHistoryRepository extends IMyRepository<History>, JpaSpecificationExecutor<History> {
    @Override
    List<History> findAllByUserId(long userId, Sort sort);
    @Query("SELECT DATE(h.start) as date, " +
            "       COLLECT(h) as historyList " +
            "FROM History h " +
            "GROUP BY DATE(h.start)")
    List<HistoryGroupedByDate> findRecordsGroupedByDate();
}
