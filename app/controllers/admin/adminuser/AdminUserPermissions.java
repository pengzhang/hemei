package controllers.admin.adminuser;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import annotation.For;
import annotation.Login;
import commons.data.ResponseData;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.user.Permission;
import models.user.UserPermission;
import play.cache.Cache;
import play.db.jpa.JPA;
import play.mvc.Before;
import play.mvc.With;
import plugins.router.Get;
import plugins.router.Post;
import utils.json.Json;

@Login
@For(UserPermission.class)
@With(ActionIntercepter.class)
public class AdminUserPermissions extends CRUD{

	@Before
	static void menus(){
		renderArgs.put("nav", "user");
		renderArgs.put("menu", "AdminUserPermissions");
	}
	
	/**
	 * 获取角色拥有的权限列表
	 * @param uid
	 */
	@Get("/admin/adminuserpermissions/permission/own")
	public static void ownPermissions(long role_id) {
		List<UserPermission> permissions = UserPermission.find("role_id=?", role_id).fetch();
        renderJSON(ResponseData.response(true, permissions, "获取角色拥有的权限列表成功"));
    }
	
	/**
	 * 获取角色未角色的权限列表
	 * @param uid
	 */
	@Get("/admin/adminuserpermissions/permission/unown")
	public static void unownPermissions(long role_id) {
		//TODO 数据大之后,性能需优化
		List<Permission> permissions = Permission.find("id not in (select permission_id from UserPermission where role_id=?)", role_id).fetch();
        renderJSON(ResponseData.response(true, permissions,"获取角色未拥有的权限列表成功"));
    }
	
	/**
	 * 添加角色权限
	 * @param uid
	 * @param username
	 * @param permission_id
	 * @param permission_name
	 */
	@Post("/admin/adminuserpermissions/permission/create")
	public static void createUserPermission(){
		UserPermission userPermission = UserPermission.create("", params);
		UserPermission existUserPermission = UserPermission.find("role_id=? and permission_id=?", userPermission.role_id, userPermission.permission_id).first();
		if(existUserPermission == null){
			userPermission.save();
		}
		
		//更新缓存
		updatePermissionCache(userPermission.role_id);
		
		renderJSON(ResponseData.response(true, userPermission, "添加权限成功"));
	}
	
	/**
	 * 批量添加角色权限
	 */
	@Post("/admin/adminuserpermissions/permissions/create")
	public static void createUserPermissions(){
		String permissions = params.get("permission_data");
		if(StringUtils.isNotEmpty(permissions)){
			List<UserPermission> userPermissions = new Gson().fromJson(permissions, new TypeToken<List<UserPermission>>(){}.getType());
			for(UserPermission userPermission : userPermissions){
				UserPermission existUserPermission = UserPermission.find("role_id=? and permission_id=?", userPermission.role_id, userPermission.permission_id).first();
				if(existUserPermission == null){
					userPermission.save();
				}
			}
			
			//更新缓存
			updatePermissionCache(userPermissions.get(0).role_id);
			
			renderJSON(ResponseData.response(true, "添加权限成功"));
		}
		renderJSON(ResponseData.response(false, "添加权限失败"));
	}
	
	/**
	 * 移除角色权限
	 * @param uid
	 * @param permission_id
	 */
	@Get("/admin/adminuserpermissions/permission/remove")
	public static void removeUserPermission(long role_id, long permission_id){
		UserPermission.delete("role_id=? and permission_id=?", role_id, permission_id);
		
		//更新缓存
		updatePermissionCache(role_id);
		
		renderJSON(ResponseData.response(true, permission_id, "移除权限成功"));
	}
	
	/**
	 * 批量 移除角色权限
	 */
	@Post("/admin/adminuserpermissions/permissions/remove")
	public static void removeUserPermissions(){
		
		String permissions = params.get("permission_data");
		if(StringUtils.isNotEmpty(permissions)){
			List<UserPermission> userPermissions = new Gson().fromJson(permissions, new TypeToken<List<UserPermission>>(){}.getType());
			for(UserPermission userPermission : userPermissions){
				UserPermission.delete("role_id=? and permission_id=?", userPermission.role_id, userPermission.permission_id);
			}
			
			//更新缓存
			updatePermissionCache(userPermissions.get(0).role_id);
			
			renderJSON(ResponseData.response(true, "移除权限成功"));
		}
		renderJSON(ResponseData.response(false, "移除权限失败"));
	}
	
	private static void updatePermissionCache(long role_id){
		List<Permission> permissions = JPA.em().createNativeQuery("select * from permission p left join user_permission up on p.id=up.permission_id where up.role_id=:role_id",Permission.class).setParameter("role_id", role_id).getResultList();
		Cache.set("rid:"+role_id, Json.toJson(permissions).toString());
	}
	
}
