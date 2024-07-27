package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.History;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class HistorySpecifications {
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
	            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("start"), dateFrom));
            }

            if (dateTo != null) {
	            Expression<Long> startInSeconds = cb.function("date_part", Long.class, cb.literal("EPOCH"), root.get("start"));
                Expression<Long> lengthInSeconds = root.get("length").as(Long.class);
	            predicate = cb.and(
                    predicate,
		            cb.lessThanOrEqualTo(
			            cb.sum(startInSeconds, lengthInSeconds), dateTo.getEpochSecond()
                    )
                );
                if (hoursBack != null) {
                    Instant dateMinusHours = dateTo.minus(hoursBack, ChronoUnit.HOURS);
	                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("start"), dateMinusHours));
                }
            }
		    query.orderBy(cb.asc(root.get("start")));
            return predicate;
        };
    }
}