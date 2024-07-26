package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "alarm", schema = "public", uniqueConstraints = @UniqueConstraint(name = "plannerTask_unique_userId_startTimestamp", columnNames = {"userId", "startTimestamp"}))
public class Alarm extends AbstractEntity
{
	@Column(nullable = false)
	private Instant startTimestamp;
	@ManyToOne(optional = false)
	@JoinColumn(name = "activityId", nullable = false)
	private Activity activity;
	@Column(name = "isActive", nullable = false)
	private boolean isActive;
//	@Column(nullable = false)
//	private boolean isRepeating;
//	private Long repeatIntervalInDays;
//	private String repeatOnDays;
}
