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
@Table(schema = "extension", uniqueConstraints = @UniqueConstraint(name = "web_extension_data_unique_user_id_domain", columnNames = {"user_id", "domain"}))
public class WebExtensionData extends AbstractEntity
{
	private String domain;
	private String title;
	private int duration;
	private Instant startTimestamp;
	@ManyToOne
	@JoinColumn(name = "activity_id")
	private Activity activity;
}
