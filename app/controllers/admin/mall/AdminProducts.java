package controllers.admin.mall;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.mall.Product;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(Product.class)
@With(ActionIntercepter.class)
public class AdminProducts extends CRUD {
	
	@Before
	static void menus(){
		renderArgs.put("nav", "mall");
		renderArgs.put("menu", "AdminProducts");
	}
}
