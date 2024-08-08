package com.timeOrganizer.model.entity;

import com.timeOrganizer.service.UserService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements IEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id = 0;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@ToString.Exclude
	private User user;

	public AbstractEntity()
	{
		try {
			user = Objects.requireNonNull(UserService.getLoggedUser()).getReference();
		} catch (Exception e) {
			user = null;
		}
	}

	protected AbstractEntity(User user)
	{
		this.user = user;
	}
	@CreatedDate
	@Column(updatable = false, nullable = false)
	private Instant createdTimestamp;
	@LastModifiedDate
	@Column(nullable = false)
	private Instant modifiedTimestamp;
}