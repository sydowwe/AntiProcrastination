package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.WebExtensionData;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IWebExtensionDataRepository extends IMyRepository<WebExtensionData>
{
	Optional<WebExtensionData> findByDomainAndUserId(String name, long userId);
}
