package com.timeOrganizer.specifications;

import com.timeOrganizer.model.entity.Activity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ActivitySpecifications
{
	public static Specification<Activity> withFilter(long userId, Long activityId, Long roleId, Long categoryId, Boolean isFromToDoList, Boolean isUnavoidable)
	{
		return (root, query, cb) -> {
			Predicate predicate = cb.conjunction();
			predicate = cb.and(predicate, cb.equal(root.get("user").get("id"), userId));

			if (activityId != null) {
				predicate = cb.and(predicate, cb.equal(root.get("id"), activityId));
			} else {
				if (roleId != null) {
					predicate = cb.and(predicate, cb.equal(root.get("role").get("id"), roleId));
				}
				if (categoryId != null) {
					predicate = cb.and(predicate, cb.equal(root.get("category").get("id"), categoryId));
				}
			}
			if (isFromToDoList != null) {
				predicate = cb.and(predicate, cb.equal(root.get("isOnToDoList"), isFromToDoList));
			}
			if (isUnavoidable != null) {
				predicate = cb.and(predicate, cb.equal(root.get("isUnavoidable"), isUnavoidable));
			}
			query.orderBy(cb.asc(root.get("name")));
			return predicate;
		};
	}
}