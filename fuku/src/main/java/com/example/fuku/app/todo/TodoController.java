package com.example.fuku.app.todo;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.fuku.domain.model.Todo;
import com.example.fuku.domain.service.todo.TodoService;
import com.github.dozermapper.core.inject.Inject;

@Controller
@RequestMapping("todo")
public class TodoController {

	@Inject
	TodoService todoService;
	
	@ModelAttribute
	public TodoForm setUpForm() {
		TodoForm form = new TodoForm();
		return form;
	}
	
	@GetMapping("list")
	public String list(Model model){
		Collection<Todo> todos = todoService.findAll();
		model.addAttribute("todos",todos);
		return "todo/list";
		
	}
	
}
