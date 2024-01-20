package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "routine_to_do_list", schema = "test",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_userId_name",columnNames = {"userId", "name"}),
        }, indexes = @Index(name = "idx_userId_timePeriodId",columnList = "userId,timePeriodId")
)
public class RoutineToDoList extends NameTextEntity {
    @Column(nullable = false)
    private boolean isDone = false;
    @ManyToOne
    @JoinColumn(name = "timePeriodId", nullable = false)
    private RoutineTimePeriod timePeriod;

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}