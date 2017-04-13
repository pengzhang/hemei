package controllers.admin.setting;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.setting.FrontNavigation;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(FrontNavigation.class)
@With(ActionIntercepter.class)
public class AdminFrontNavigations extends CRUD {
	
	@Before
	static void menus(){
		renderArgs.put("nav", "admin");
		renderArgs.put("menu", "AdminFrontNavigations");
	}
}
