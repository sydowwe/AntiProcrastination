package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.History;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class HistorySpecifications {
    public static Specification<History> withFilter(Long activityId, Long roleId, Long categoryId, Boolean isFromToDoList, Boolean isUnavoidable, Instant dateFrom, Instant dateTo, Long hoursBack) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (activityId != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("activity").get("id"), activityId));
            } else {
                if (roleId != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("activity").get("role").get("id"), roleId));
                }
                if (categoryId != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("activity").get("category").get("id"), categoryId));
                }
            }
            if (isFromToDoList != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("activity").get("isOnToDoList"), isFromToDoList));
            }
            if (isUnavoidable != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("activity").get("isUnavoidable"), isUnavoidable));
            }
            if (dateFrom != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("start"), dateFrom));
            }

            if (dateTo != null) {
                Expression<Long> startInSeconds = criteriaBuilder.function("UNIX_TIMESTAMP", Long.class, root.get("start"));
                Expression<Long> lengthInSeconds = root.get("length").as(Long.class);

                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.sum(startInSeconds, lengthInSeconds), dateTo.getEpochSecond())
                );
                if (hoursBack != null) {
                    Instant dateMinusHours = dateTo.minus(hoursBack, ChronoUnit.HOURS);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("start"), dateMinusHours));
                }
            }

            return predicate;
        };
    }

}
