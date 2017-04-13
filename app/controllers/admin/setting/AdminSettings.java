package controllers.admin.setting;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.setting.Setting;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(Setting.class)
@With(ActionIntercepter.class)
public class AdminSettings extends CRUD {
	
	@Before
	static void menus(){
		renderArgs.put("nav", "admin");
		renderArgs.put("menu", "AdminSettings");
	}
}
