package com.example.fuku.domain.service.todo;
import java.util.Collection;

import com.example.fuku.domain.model.Todo;

public interface TodoService {
	Collection<Todo> findAll();
	Todo create(Todo todo);
	Todo finish(String todoId);
	void delete(String todoId);

}
