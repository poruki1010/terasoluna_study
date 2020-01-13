<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Todo List</h1>
	<hr />
	<div id="todoList">
		<ul>
		<c:forEach items="${todos}" var="todo">
			<li><c:choose>
				<c:when test="${todo.finished}">
					<span class="strike">
					${f:h(todo.todoTitle)}
					</span>	
				</c:when>
				<c:otherwise>
					${f:h(todo.todoTitle)}
				</c:otherwise>
			</c:choose></li>
		</c:forEach>
		</ul>
	</div>
</body>
</html>