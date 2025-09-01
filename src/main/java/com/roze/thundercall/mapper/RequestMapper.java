package com.roze.thundercall.mapper;

import com.roze.thundercall.dto.ApiRequest;
import com.roze.thundercall.dto.RequestResponse;
import com.roze.thundercall.entity.Request;
import com.roze.thundercall.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final CollectionRepository collectionRepository;

    public Request toEntity(ApiRequest apiRequest) {
        Request request = Request.builder()
                .name(apiRequest.name())
                .description(apiRequest.description())
                .method(apiRequest.method())
                .url(apiRequest.url())
                .headers(apiRequest.headers())
                .body(apiRequest.body())
                .build();
        return request;
    }

    public RequestResponse toResponse(Request request) {
        return RequestResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .method(request.getMethod())
                .url(request.getUrl())
                .headers(request.getHeaders())
                .body(request.getBody())
                .collectionId(request.getCollection().getId())
                .collectionName(request.getCollection().getName())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
}
