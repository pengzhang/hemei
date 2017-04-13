package controllers.admin.content;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.assist.Attachment;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(Attachment.class)
@With(ActionIntercepter.class)
public class AdminAttachments extends CRUD {

	@Before
	static void menus() {
		renderArgs.put("nav", "content");
		renderArgs.put("menu", "AdminAttachments");
	}
	
}
