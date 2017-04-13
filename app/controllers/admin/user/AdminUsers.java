package controllers.admin.user;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.user.User;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(User.class)
@With(ActionIntercepter.class)
public class AdminUsers extends CRUD {
	
	@Before
	static void menus(){
		renderArgs.put("nav", "user");
		renderArgs.put("menu", "Users");
	}
	
}
