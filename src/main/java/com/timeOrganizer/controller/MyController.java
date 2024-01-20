package com.timeOrganizer.controller;

import com.timeOrganizer.exception.RequiredParameterMissingException;
import com.timeOrganizer.exception.UserNotInSecurityContext;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.dto.response.NameTextResponse;
import com.timeOrganizer.security.LoggedUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public abstract class MyController {
    @PostMapping("/common")
    public String commonEndpoint() {
        return "This is a common endpoint";
    }

    public List<IdLabelResponse> mapToIdNameResponseList(List<? extends NameTextResponse> list) {
        return list.stream()
                .map(IdLabelResponse::new)
                .collect(Collectors.toList());
    }

    public IdLabelResponse mapToIdNameResponse(NameTextResponse nameTextResponse) {
        return new IdLabelResponse(nameTextResponse);
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
    protected URI getCreatedResourceURI(long id){
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
    protected LoggedUser getLoggedUser() throws UserNotInSecurityContext {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof LoggedUser)) {
            throw new EntityNotFoundException("User not in security context");
        }
        return (LoggedUser) authentication.getPrincipal();
    }
}
