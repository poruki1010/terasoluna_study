package com.example.fuku.domain.service.todo;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

import com.example.fuku.domain.model.Todo;
import com.example.fuku.domain.repository.todo.TodoRepository;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

	private static final long MAX_UNFINISHED_COUNT = 5;

	@Inject
	TodoRepository todoRepository;
	
	private Todo findOne(String todoId) {
		Todo todo = todoRepository.findById(todoId).orElse(null);
		if(todo == null) {
			ResultMessages messages = ResultMessages.error();
			messages.add(ResultMessage.fromText("[E404]The requested Todo is not found.(id="+todoId+")"));
			throw new ResourceNotFoundException(messages);
		}
		return todo;
	}

	@Override 
	@Transactional (readOnly = true)
	public Collection<Todo> findAll() {
		return todoRepository.findAll();
	}

	@Override
	public Todo create(Todo todo) {
		long unfinishedCount = todoRepository.countByFinished(false);
		if(unfinishedCount >= MAX_UNFINISHED_COUNT ) {
			ResultMessages messages = ResultMessages.error();
			messages.add(ResultMessage.fromText("[E001]The count of un-finished Todo must not be over"+MAX_UNFINISHED_COUNT+"."));
			throw new BusinessException(messages);
		}
		String todoId = UUID.randomUUID().toString();
		Date createdAt = new Date();
		todo.setTodoId(todoId);
		todo.setCreatedAt(createdAt);
		todo.setFinished(false);
		todoRepository.create(todo);

		return todo;
	}

	@Override
	public Todo finish(String todoId) {
		Todo todo = findOne(todoId);
		if(todo.isFinished()) {
			ResultMessages messages = ResultMessages.error();
			messages.add(ResultMessage.fromText("[E002]The requested Todo id already finished.(id="+todoId+")"));
			throw new BusinessException(messages);
		}
		todo.setFinished(true);
		todoRepository.updateById(todo);
		return todo;
	}

	@Override
	public void delete(String todoId) {
		Todo todo = findOne(todoId);
		todoRepository.deleteById(todo);
	}

}
