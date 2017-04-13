package controllers.admin.setting;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.setting.AlipayWapKey;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(AlipayWapKey.class)
@With(ActionIntercepter.class)
public class AdminAlipayWapKeys extends CRUD {
	
	@Before
	static void menus(){
		renderArgs.put("nav", "service");
		renderArgs.put("menu", "AdminAlipayWapKeys");
	}
}
