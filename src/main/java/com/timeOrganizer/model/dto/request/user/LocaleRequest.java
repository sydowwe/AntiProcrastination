package com.timeOrganizer.model.dto.request.user;

import com.timeOrganizer.helper.AvailableLocales;
import com.timeOrganizer.model.dto.request.extendable.IRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class LocaleRequest implements IRequest
{
	private AvailableLocales locale;
}
