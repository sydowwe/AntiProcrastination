package com.timeOrganizer.model.entity;

import com.timeOrganizer.helper.MyIntTime;
import com.timeOrganizer.helper.MyIntTimeDBConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(schema = "public")
//TODO MAYBE ADD indexing
public class History extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;
    @Column(nullable = false)
    private Instant startTimestamp;
    @Convert(converter = MyIntTimeDBConverter.class)
    @Column(nullable = false)
    private MyIntTime length;
	@Column(nullable = false)
	private Instant endTimestamp;
}
