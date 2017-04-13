package controllers.admin.setting;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.setting.WechatKey;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(WechatKey.class)
@With(ActionIntercepter.class)
public class AdminWechatKeys extends CRUD {
	
	@Before
	static void menus(){
		renderArgs.put("nav", "service");
		renderArgs.put("menu", "AdminWechatKeys");
	}
}
