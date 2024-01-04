package com.timeOrganizer.controller;

import com.timeOrganizer.exception.RequiredParameterMissingException;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public abstract class MyController {
    @PostMapping("/common")
    public String commonEndpoint() {
        return "This is a common endpoint";
    }

    public List<IdLabelResponse> mapToIdNameResponseList(List<? extends AbstractEntity> list) {
        return list.stream()
                .map(IdLabelResponse::new)
                .collect(Collectors.toList());
    }

    public IdLabelResponse mapToIdNameResponse(AbstractEntity entity) {
        return new IdLabelResponse(entity);
    }

    public Map<String, Object> oneParamResponse(String key, Object value) {
        Map<String, Object> response = HashMap.newHashMap(1);
        response.put(key, value);
        return response;
    }

    public Object oneParamRequestValue(String request, boolean isString) throws RequiredParameterMissingException, NumberFormatException {
        if (request == null) {
            throw new RequiredParameterMissingException("Request body param is missing");
        }
        if (request.length() >= 2 && request.startsWith("\"") && request.endsWith("\"")) {
            request = request.substring(1, request.length() - 1).trim();
        }
        if (request.isEmpty()) {
            throw new RequiredParameterMissingException("Request body param is missing");
        }
        return isString ? request : switch (request) {
            case "true" -> true;
            case "false" -> false;
            case "null" -> null;
            default -> {
                if (request.contains(".")) {
                    yield Double.parseDouble(request);
                } else {
                    yield Integer.parseInt(request);
                }
            }
        };

    }
}
