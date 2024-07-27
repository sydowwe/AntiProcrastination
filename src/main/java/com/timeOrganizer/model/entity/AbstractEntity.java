package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Setter
@ToString
public abstract class AbstractEntity implements IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    @ToString.Exclude
    private User user;
    @Column(name = "createdAt", updatable = false)
    @ColumnDefault("localtimestamp()")
    private LocalDateTime createdAt;
    protected  AbstractEntity(User user){
        this.user = user;
    }
}