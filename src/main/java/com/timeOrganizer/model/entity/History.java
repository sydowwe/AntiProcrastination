package com.timeOrganizer.model.entity;

import com.timeOrganizer.helper.MyIntTime;
import com.timeOrganizer.helper.MyIntTimeDBConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "history", schema = "test")
//TODO MAYBE ADD indexing
public class History extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "activityId",nullable = false)
    private Activity activity;
    @Column(nullable = false)
    private ZonedDateTime start;
    @Convert(converter = MyIntTimeDBConverter.class)
    @Column(nullable = false)
    private MyIntTime length;
    public int getLengthInSeconds() {
        return length.getHours() * 3600 + length.getMinutes() * 60 + length.getSeconds();
    }
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
