package controllers.admin.assist;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.content.Todo;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(Todo.class)
@With(ActionIntercepter.class)
public class AdminTodos extends CRUD {

	@Before
	static void menus(){
		renderArgs.put("nav", "admin");
		renderArgs.put("menu", "AdminTodos");
	}
}
