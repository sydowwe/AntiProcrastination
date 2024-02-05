package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.AbstractInOutMapper;
import com.timeOrganizer.model.dto.request.IRequest;
import com.timeOrganizer.model.dto.response.IdResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.repository.IMyRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
public abstract class MyService<ENTITY extends AbstractEntity, REPOSITORY extends IMyRepository<ENTITY>, REQUEST extends IRequest, RESPONSE extends IdResponse, MAPPER extends AbstractInOutMapper<ENTITY, REQUEST, RESPONSE>> implements IMyService {
    protected final REPOSITORY repository;
    protected final MAPPER mapper;
    //TODO MAKE userService Optional


    public RESPONSE getResponseById(long id) throws EntityNotFoundException {
        return mapper.convertToFullResponse(this.getById(id));
    }

    public List<RESPONSE> getAll(long userId) {
        var test = repository.findAllByUserId(userId, this.getSort());
        return mapper.convertToFullResponseList(test);
    }

    public void deleteById(@NonNull long id) throws EntityNotFoundException {
        repository.deleteById(id);
    }

    public boolean isPresent(long id) {
        try {
            this.getReference(id);
        } catch (EntityNotFoundException e) {
            return false;
        }
        return true;
    }
    protected ENTITY getById(long id) throws EntityNotFoundException{
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }
    public ENTITY getReference(long id) throws EntityNotFoundException {
        return this.repository.getReferenceById(id);
    }
    public RESPONSE insert(@NonNull REQUEST request, User userReference) throws EntityNotFoundException, EntityExistsException {
        ENTITY entity = this.mapper.createEntityFromRequest(request,userReference,this.getDependencies(request));
        return this.mapper.convertToFullResponse(this.repository.save(entity));
    }
    public RESPONSE updateById(long id,@NonNull REQUEST request)  throws EntityNotFoundException, EntityExistsException{
        ENTITY entity = this.getById(id);
        entity = this.mapper.updateEntityFromRequest(entity,request,this.getDependencies(request));
        return this.mapper.convertToFullResponse(this.repository.save(entity));
    }
    protected abstract Map<String,? extends AbstractEntity> getDependencies(REQUEST request);
    protected Sort.Direction getSortDirection(){
        return Sort.Direction.DESC;
    }
    protected String getSortByProperties(){
        return "id";
    }
    protected Sort getSort(){
        return Sort.by(this.getSortDirection(), this.getSortByProperties());
    }
}
