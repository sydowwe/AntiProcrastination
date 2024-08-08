package com.timeOrganizer.model.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class NameTextColorEntity extends NameTextEntity
{
    private String color;

	public NameTextColorEntity(String name, String text, String color, User user)
	{
		super(name, text, user);
		this.color = color;
	}
}
