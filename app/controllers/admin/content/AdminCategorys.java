package controllers.admin.content;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import annotation.For;
import annotation.Login;
import commons.data.ResponseData;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.category.Category;
import play.mvc.Before;
import play.mvc.With;
import plugins.router.Get;
import plugins.router.Post;
import utils.json.Json;
import utils.sql.HMSQL;

@Login
@For(Category.class)
@With(ActionIntercepter.class)
public class AdminCategorys extends CRUD {
	
	@Before
	static void menus(){
		if(request.actionMethod.equals("list")){
			request.args.put("where", HMSQL.parseParamFilter(params.get("filter")) + " and category_type=0 ");
		}
		renderArgs.put("nav", "service");
		renderArgs.put("menu", "AdminCategorys");
		renderArgs.put("categories",Json.toJson(Category.find("status", false).fetch()));
	}
	
	/**
	 * 搜索分类
	 * @param category
	 */
	@Get("/admin/admincategorys/search")
	public static void searchCategory(String category){
		List<Category> categories = new ArrayList<Category>();
		if(StringUtils.isNotEmpty(category)){
			categories = Category.find("category like %?%", category).fetch();
		}else{
			categories = Category.find("pid > ?", 0L).fetch(20);
		}
		renderJSON(ResponseData.response(true, categories, "搜索分类成功"));
	}
	
	/**
	 * 创建分类
	 */
	@Post("/admin/admincategorys/node")
	public static void categoryNodeAdd(){
		Category category = Category.create("", params);
		category.save();
		renderJSON(ResponseData.response(true, category, "创建分类成功"));
		
	}
	
	/**
	 * 修改分类
	 * @param id
	 */
	@Post("/admin/admincategorys/node/modify")
	public static void categoryNodeModify(long id){
		Category category = Category.findById(id);
		Category.edit(params.getRootParamNode(), "", category, null);
		category.save();
		renderJSON(ResponseData.response(true, category, "修改分类成功"));
	}
	
	/**
	 * 删除分类
	 * @param id
	 */
	@Get("/admin/admincategorys/node/remove")
	public static void categoryNodeRemove(long id){
		removeParent(id);
		Category.delete("id", id);
		renderJSON(ResponseData.response(true, "删除分类成功"));
	}
	
	private static void removeParent(long id){
		List<Category> categories = Category.find("pid", id).fetch();
		if(categories.size()>0){
			for(Category category : categories){
				removeParent(category.id);
			}
		}
		Category.delete("pid", id);
	}
}
