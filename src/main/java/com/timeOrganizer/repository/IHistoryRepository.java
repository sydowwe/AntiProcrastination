package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.History;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IHistoryRepository extends IMyRepository<History>, JpaSpecificationExecutor<History> {

}
