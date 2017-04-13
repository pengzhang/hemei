package controllers.admin.adminuser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import annotation.For;
import annotation.Login;
import commons.data.ResponseData;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.user.Permission;
import play.db.jpa.JPA;
import play.i18n.Messages;
import play.mvc.Before;
import play.mvc.With;
import plugins.router.Get;

@Login
@For(Permission.class)
@With(ActionIntercepter.class)
public class AdminPermissions extends CRUD{

	@Before
	static void menus(){
		renderArgs.put("nav", "user");
		renderArgs.put("menu", "AdminPermissions");
		if(request.actionMethod.equals("list")){
			renderArgs.put("permissionTree", permissionTree(null));
		}
	}
	
	/**
	 * 获取某个角色的权限树
	 * @param role_id
	 */
	@Get("/admin/adminpermissions/permissions/role")
	public static void permissionRoleRelation(Long role_id){
		renderJSON(ResponseData.response(true,permissionTree(role_id), "获取用户的权限树成功"));
	}
	
	/**
	 * 将权限的action组装成树状结构
	 * @param role_id
	 * @return
	 */
	public static String permissionTree(Long role_id){
		Set<String> list = new HashSet<String>();
		
		Map<String, String> filters = new HashMap<String, String>();
		
		//所有权限
		List<Permission> permissions = Permission.findAll();
		
		//获取用户拥有的权限
		List<Permission> owns = new ArrayList<Permission>();
		if(role_id != null){
			owns = JPA.em().createNativeQuery("select * from permission p left join user_permission up on p.id=up.permission_id where up.role_id=:role_id",Permission.class).setParameter("role_id", role_id).getResultList();
		}
		
		//处理权限成树状结构
		for(Permission p : permissions){
			String[] actionPart = p.action.split("[.]");
			for(int i=0; i<actionPart.length; i++){
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"id\":\""+actionPart[i]+"\",");
				sb.append("\"name\":\""+Messages.get(actionPart[i])+"\",");
				
				//添加父节点
				if(i==0){
					//首节点
					sb.append("\"parent\":\"\"");
				}else{
					sb.append("\"parent\":\""+actionPart[i-1]+"\"");
				}
				
				//末节点添加权限数据
				if((actionPart.length==2 && i==1)||(actionPart.length==3 && i==2)){
					sb.append(",\"permission_id\":\""+p.id+"\"");
					sb.append(",\"permission_name\":\""+p.action+"\"");

				}
				//用户是否拥有该权限
				if(owns.contains(p)){
					sb.append(",\"isChecked\":\"true\"");
				}
				sb.append("}");
				
				//处理父节点添加
				if((actionPart.length==2 && i!=1)||(actionPart.length==3 && i!=2)){
					/*
					 * 1. 检查过滤器中是否有当前的ID? 有:->3, 没有 ->2
					 * 2. 将该节点信息添加到list中,并将该节点信息添加到过滤器中
					 * 3. 判断节点是否用户拥有的? 是->4, 否: 忽略
					 * 4. 检查当前节点是否与过滤器中的节点信息一致? 一致:忽略 , 不一致:->5
					 * 5. 移除list中的节点信息,添加当前节点信息
					 */
					if(!filters.keySet().contains(actionPart[i])){
						filters.put(actionPart[i],sb.toString());
						list.add(sb.toString());
					}else{
						if(owns.contains(p)){
							if(!filters.get(actionPart[i]).equals(sb.toString())){
								list.remove(filters.get(actionPart[i]));
								list.add(sb.toString());
							}
						}
					}
				}else{
					if(actionPart.length>1){
						list.add(sb.toString());
					}
				}

			}
		}
		return Arrays.toString(list.toArray());
	}
	
}
