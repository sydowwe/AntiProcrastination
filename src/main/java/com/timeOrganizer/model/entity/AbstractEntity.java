package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements IEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @Setter
    private User user;
	@CreatedDate
	@Column(updatable = false, nullable = false)
	private Instant createdTimestamp = Instant.now();
	@LastModifiedDate
	@Column(nullable = false)
	private Instant modifiedTimestamp;
/*    @PrePersist
    protected void prePersist() {
    }
    @PreUpdate
    protected void preUpdate() {
    }*/
}