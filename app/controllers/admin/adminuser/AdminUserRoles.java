package controllers.admin.adminuser;

import java.util.List;

import annotation.For;
import annotation.Login;
import commons.data.ResponseData;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.user.Role;
import models.user.UserRole;
import play.mvc.Before;
import play.mvc.With;
import plugins.router.Get;
import plugins.router.Post;

@Login
@For(UserRole.class)
@With(ActionIntercepter.class)
public class AdminUserRoles extends CRUD{

	@Before
	static void menus(){
		renderArgs.put("nav", "user");
		renderArgs.put("menu", "AdminUserRoles");
	}
	
	/**
	 * 获取用户拥有的角色列表
	 * @param uid
	 */
	@Get("/admin/adminuserroles/role/own")
	public static void ownRoles(long uid) {
		List<UserRole> roles = UserRole.find("user_id=?", uid).fetch();
        renderJSON(ResponseData.response(true, roles, "获取用户拥有的角色列表成功"));
    }
	
	/**
	 * 获取用户未用户的角色列表
	 * @param uid
	 */
	@Get("/admin/adminuserroles/role/unown")
	public static void unownRoles(long uid) {
		//TODO 数据大之后,性能需优化
		List<Role> roles = Role.find("id not in (select role_id from UserRole where user_id=?)", uid).fetch();
        renderJSON(ResponseData.response(true, roles,"获取用户未拥有的角色列表成功"));
    }
	
	/**
	 * 添加用户角色
	 * @param uid
	 * @param username
	 * @param role_id
	 * @param role_name
	 */
	@Post("/admin/adminuserroles/role/create")
	public static void createUserRole(){
		UserRole userRole = UserRole.create("", params);
		userRole.save();
		renderJSON(ResponseData.response(true, "添加角色成功"));
	}
	
	/**
	 * 移除用户角色
	 * @param uid
	 * @param role_id
	 */
	@Get("/admin/adminuserroles/role/remove")
	public static void removeUserRole(long user_id, long role_id){
		UserRole.delete("user_id=? and role_id=?", user_id, role_id);
		renderJSON(ResponseData.response(true, "移除角色成功"));
	}
}
