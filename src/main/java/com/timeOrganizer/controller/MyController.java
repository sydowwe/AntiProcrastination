package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.response.IdNameResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public abstract class MyController {
    @RequestMapping(value = "/common", method = RequestMethod.GET)
    public String commonEndpoint() {
        return "This is a common endpoint";
    }
    public List<IdNameResponse> mapToIdNameResponse(List<? extends AbstractEntity> list) {
        return list.stream()
                .map(IdNameResponse::new)
                .collect(Collectors.toList());
    }
}
