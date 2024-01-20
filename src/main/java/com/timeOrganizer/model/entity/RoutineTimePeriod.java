package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "routine_time_period", schema = "test", uniqueConstraints = @UniqueConstraint(name = "unique_userId_name", columnNames = {"userId", "text"}))
public class RoutineTimePeriod extends AbstractEntity {
    @Column(nullable = false)
    private String text;
    private String color;
    private int length;
    @OneToMany(mappedBy = "timePeriod")
    @ToString.Exclude
    private List<RoutineToDoList> toDoListItems;
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
