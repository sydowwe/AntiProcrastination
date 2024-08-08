package com.timeOrganizer.controller;

import com.timeOrganizer.exception.RequiredParameterMissingException;
import com.timeOrganizer.model.dto.response.extendable.NameTextResponse;
import com.timeOrganizer.model.dto.response.general.SelectOptionResponse;
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

    public List<SelectOptionResponse> mapToSelectOptionResponseList(List<? extends NameTextResponse> list) {
        return list.stream()
                .map(SelectOptionResponse::new)
                .collect(Collectors.toList());
    }

    public SelectOptionResponse mapToSelectOptionResponse(NameTextResponse nameTextResponse) {
        return new SelectOptionResponse(nameTextResponse);
    }
    public SelectOptionResponse mapToSelectOptionResponse(long id, String name) {
        return new SelectOptionResponse(id,name);
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
   /* protected LoggedUser getLoggedUser() throws UserNotInSecurityContext {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof LoggedUser)) {
            throw new EntityNotFoundException("User not in security context");
        }
        return (LoggedUser) authentication.getPrincipal();
    }*/
}
