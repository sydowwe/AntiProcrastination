package com.timeOrganizer.model.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class EntityWithActivity extends AbstractEntity
{
	@ManyToOne(optional = false)
	@JoinColumn(name = "activity_id", nullable = false)
	private Activity activity;
}
