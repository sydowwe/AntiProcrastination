package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;


@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "to_do_list", schema = "test",
        uniqueConstraints = {
        @UniqueConstraint(name = "unique_userId_name",columnNames = {"userId", "name"}),
    }, indexes = @Index(name = "idx_userId_urgencyId",columnList = "userId,urgencyId")
)
public class ToDoList extends NameTextEntity {
    @Column(nullable = false)
    private boolean isDone = false;
    @ManyToOne
    @JoinColumn(name = "urgencyId", nullable = false)
    private Urgency urgency;

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
