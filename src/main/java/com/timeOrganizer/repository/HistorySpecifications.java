package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.History;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class HistorySpecifications
{
	public static Specification<History> withFilter(long userId, Long activityId, Long roleId, Long categoryId, Boolean isFromToDoList, Boolean isUnavoidable, Instant dateFrom, Instant dateTo, Long hoursBack)
	{
		return (root, query, cb) -> {
			Predicate predicate = cb.conjunction();
			predicate = cb.and(predicate, cb.equal(root.get("user").get("id"), userId));

			if (activityId != null) {
				predicate = cb.and(predicate, cb.equal(root.get("activity").get("id"), activityId));
			} else {
				if (roleId != null) {
					predicate = cb.and(predicate, cb.equal(root.get("activity").get("role").get("id"), roleId));
				}
				if (categoryId != null) {
					predicate = cb.and(predicate, cb.equal(root.get("activity").get("category").get("id"), categoryId));
				}
			}
			if (isFromToDoList != null) {
				predicate = cb.and(predicate, cb.equal(root.get("activity").get("isOnToDoList"), isFromToDoList));
			}
			if (isUnavoidable != null) {
				predicate = cb.and(predicate, cb.equal(root.get("activity").get("isUnavoidable"), isUnavoidable));
			}
			if (dateFrom != null) {
				predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("startTimestamp"), dateFrom));
			}

			if (dateTo != null) {
				predicate = cb.and(
					predicate,
					cb.lessThanOrEqualTo(
						root.get("endTimestamp"), dateTo
					)
				);
				if (hoursBack != null) {
					Instant dateMinusHours = dateTo.minus(hoursBack + 24, ChronoUnit.HOURS);
					predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("startTimestamp"), dateMinusHours));
				}

			}
			query.orderBy(cb.asc(root.get("startTimestamp")));
			return predicate;
		};
	}

	public static Specification<History> withFilter(long userId, Long activityId, Long roleId, Long categoryId)
	{
		return withFilter(userId, activityId, roleId, categoryId, null, null, null, null, null);
	}
}