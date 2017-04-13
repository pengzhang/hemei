package controllers.admin.adminuser;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.user.AdminUser;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(AdminUser.class)
@With(ActionIntercepter.class)
public class AdminMangers extends CRUD {
	
	@Before
	static void menus(){
		renderArgs.put("nav", "user");
		renderArgs.put("menu", "AdminMangers");
	}
	
}
