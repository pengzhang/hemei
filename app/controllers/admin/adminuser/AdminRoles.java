package controllers.admin.adminuser;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.user.Role;
import play.mvc.Before;
import play.mvc.With;

@Login
@For(Role.class)
@With(ActionIntercepter.class)
public class AdminRoles extends CRUD{

	@Before
	static void menus(){
		renderArgs.put("nav", "user");
		renderArgs.put("menu", "AdminRoles");
		if(request.actionMethod.equals("list")){
			renderArgs.put("permissionTree", AdminPermissions.permissionTree(null));
		}
	}
	
}
