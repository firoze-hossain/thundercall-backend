package com.roze.thundercall.service.impl;

import com.roze.thundercall.dto.CollectionRequest;
import com.roze.thundercall.dto.CollectionResponse;
import com.roze.thundercall.entity.Collection;
import com.roze.thundercall.entity.User;
import com.roze.thundercall.entity.Workspace;
import com.roze.thundercall.exception.ResourceNotFoundException;
import com.roze.thundercall.mapper.CollectionMapper;
import com.roze.thundercall.repository.CollectionRepository;
import com.roze.thundercall.repository.WorkspaceRepository;
import com.roze.thundercall.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;
    private final WorkspaceRepository workspaceRepository;

    @Override
    @Transactional
    public CollectionResponse createCollection(CollectionRequest request, User user) {
        Workspace workspace = workspaceRepository.findByOwner(user)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No workspace found"));
        Collection collection = collectionMapper.toEntity(request);
        collection.setWorkspace(workspace);
        collection.setRequests(new ArrayList<>());
        Collection savedCollection = collectionRepository.save(collection);
        return collectionMapper.toShortResponse(savedCollection);
    }

    @Override
    public List<CollectionResponse> getUserCollections(User user) {
        List<Collection> collections = collectionRepository.findByWorkspaceOwner(user);
        return collections.stream().map(collectionMapper::toShortResponse).toList();
    }

    @Override
    public CollectionResponse getCollectionById(Long id, User user) {
        Collection collection = collectionRepository.findByIdAndWorkspaceOwner(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));
        return collectionMapper.toResponse(collection);
    }

    @Override
    @Transactional
    public CollectionResponse updateCollection(Long id, CollectionRequest request, User user) {
        Collection collection = collectionRepository.findByIdAndWorkspaceOwner(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));
        collection.setName(request.name());
        collection.setDescription(request.description());
        Collection updatedCollection = collectionRepository.save(collection);
        return collectionMapper.toShortResponse(updatedCollection);

    }

    @Override
    @Transactional
    public void deleteCollection(Long id, User user) {
        Collection collection = collectionRepository.findByIdAndWorkspaceOwner(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));
        collectionRepository.delete(collection);
    }
}
