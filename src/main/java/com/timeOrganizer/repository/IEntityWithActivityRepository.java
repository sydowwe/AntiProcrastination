package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.EntityWithActivity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IEntityWithActivityRepository<ENTITY extends EntityWithActivity> extends IMyRepository<ENTITY>
{
}
