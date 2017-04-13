package controllers.admin.content;

import java.util.List;

import annotation.For;
import annotation.Login;
import commons.data.ResponseData;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.category.CategoryItem;
import play.mvc.Before;
import play.mvc.With;
import plugins.router.Get;
import plugins.router.Post;

@Login
@For(CategoryItem.class)
@With(ActionIntercepter.class)
public class AdminCategoryItems extends CRUD {
	
	@Before
	static void menus(){
		renderArgs.put("nav", "service");
		renderArgs.put("menu", "AdminCategoryItems");	
	}
	
	/**
	 * 获取分类实体列表
	 * @param oid
	 * @param otype
	 */
	@Get("/admin/admincategoryitems/items")
	public static void searchCategory(long oid, String otype){
		List<CategoryItem> categoryItems = CategoryItem.find("oid=? and otype=?", oid, otype).fetch();
		renderJSON(ResponseData.response(true, categoryItems, "获取分类实体成功"));
	}
	
	
	/**
	 * 实体到添加分类Item
	 * @param id
	 * @param cid
	 * @param category
	 */
	@Post("/admin/admincategoryitems/item/add")
	public static void addCategoryItem(long id, long cid, String otype, String category){
		CategoryItem ci = CategoryItem.find("oid=? and category_id=?", id, cid).first();
		if(ci == null){
			new CategoryItem(id, otype , cid).save();
		}
		renderJSON(ResponseData.response(true, "文章添加分类成功"));
	}
	
	/**
	 * 删除实体的分类Item
	 * @param id
	 * @param cid
	 */
	@Get("/admin/admincategoryitems/item/remove")
	public static void removeCategoryItem(long id, long cid){
		CategoryItem.delete("oid=? and category_id=?", id, cid);
		renderJSON(ResponseData.response(true, "文章删除分类成功"));
	}
}
