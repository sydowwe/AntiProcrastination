package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "webExtension", schema = "extension", uniqueConstraints = @UniqueConstraint(name = "role_unique_userId_name", columnNames = {"userId", "domain"}))
public class WebExtensionData extends AbstractEntity
{
	private String domain;
	private String title;
	private int duration;
	private Instant startTimestamp;
	@ManyToOne
	@JoinColumn(name = "activityId")
	private Activity activity;
}
