package com.timeOrganizer.service;

import com.timeOrganizer.exception.BatchDeleteException;
import com.timeOrganizer.model.dto.mappers.AbstractInOutMapper;
import com.timeOrganizer.model.dto.request.extendable.IRequest;
import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import com.timeOrganizer.model.dto.response.general.SelectOptionResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.NameTextEntity;
import com.timeOrganizer.repository.IMyRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public abstract class MyService<ENTITY extends AbstractEntity, REPOSITORY extends IMyRepository<ENTITY>, REQUEST extends IRequest, RESPONSE extends IdResponse, MAPPER extends AbstractInOutMapper<ENTITY, REQUEST, RESPONSE>> implements IMyService
{
	protected final REPOSITORY repository;
	protected final MAPPER mapper;

	public RESPONSE getResponseById(long id) throws EntityNotFoundException
	{
		return mapper.convertToFullResponse(this.getById(id));
	}

	public List<RESPONSE> getAllAsResponse()
	{
		var test = repository.findAllByUserId(UserService.getLoggedUser().getId(), this.getSort());
		return mapper.convertToFullResponseList(test);
	}

	public List<ENTITY> getAll()
	{
		return repository.findAllByUserId(UserService.getLoggedUser().getId(), this.getSort());
	}

	public void deleteById(@NonNull long id) throws EntityNotFoundException
	{
		repository.deleteById(id);
	}

	public void batchDelete(List<IdRequest> idList) throws BatchDeleteException
	{
		List<Long> ids = idList.stream().map(IdRequest::getId).toList();
		int deletedIds = this.repository.batchDelete(ids);
		if (deletedIds != ids.size()) {
			throw new BatchDeleteException();
		}
	}

	protected ENTITY getById(long id) throws EntityNotFoundException
	{
		return repository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
	}

	public ENTITY getReference(long id) throws EntityNotFoundException
	{
		return this.repository.getReferenceById(id);
	}

	public RESPONSE insert(@NonNull REQUEST request) throws EntityNotFoundException, ConstraintViolationException, DuplicateKeyException, DataException
	{
		ENTITY entity = this.mapper.createEntityFromRequest(request, this.getDependencies(request));
		RESPONSE response = null;
		try {
			response = this.mapper.convertToFullResponse(this.repository.save(entity));
		} catch (DataIntegrityViolationException ex) {
			Throwable rootCause = ex.getRootCause();
			if (rootCause instanceof DuplicateKeyException) {
				throw new DuplicateKeyException(ex.getMessage());
			}
		}
		return response;
	}

	public RESPONSE updateById(long id, @NonNull REQUEST request) throws EntityNotFoundException, EntityExistsException
	{
		ENTITY entity = this.getById(id);
		entity = this.mapper.updateEntityFromRequest(entity, request, this.getDependencies(request));
		return this.mapper.convertToFullResponse(this.repository.save(entity));
	}

	protected abstract Map<String, ? extends AbstractEntity> getDependencies(REQUEST request);

	protected final Map<String, ? extends AbstractEntity> getMapFromDependencies(AbstractEntity... dependencies)
	{
		return Arrays.stream(dependencies)
			.collect(Collectors.toMap(
				entity -> entity.getClass().getSimpleName().split("\\$", 2)[0],
				entity -> entity
			));
	}

	protected Sort.Direction getSortDirection()
	{
		return Sort.Direction.DESC;
	}

	protected String getSortByProperties()
	{
		return "id";
	}

	protected Sort getSort()
	{
		return Sort.by(this.getSortDirection(), this.getSortByProperties());
	}

	protected <R> List<R> getDistinctList(List<ENTITY> list, Function<ENTITY, R> extractor)
	{
		return list.stream()
			.map(extractor)
			.distinct()
			.collect(Collectors.toList());
	}

	protected List<SelectOptionResponse> getDistinctOptionList(List<ENTITY> list, Function<ENTITY, NameTextEntity> extractor)
	{
		return this.getOptionResponseFromList(this.getDistinctList(list, extractor));
	}

	protected List<SelectOptionResponse> getOptionResponseFromList(List<? extends NameTextEntity> list)
	{
		return list.stream().map(entity -> new SelectOptionResponse(entity.getId(), entity.getName())).toList();
	}
}
