package controllers.admin.content;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.content.Message;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(Message.class)
@With(ActionIntercepter.class)
public class AdminMessages extends CRUD {

	@Before
	static void menus() {
		renderArgs.put("nav", "service");
		renderArgs.put("menu", "AdminMessages");
	}
}
