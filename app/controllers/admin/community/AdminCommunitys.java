package controllers.admin.community;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.category.Category;
import play.db.Model;
import play.mvc.Before;
import play.mvc.With;
import utils.json.Json;
import utils.sql.HMSQL;

@Login
@For(Category.class)
@With(ActionIntercepter.class)
public class AdminCommunitys extends CRUD {
	
	@Before
	static void menus(){
		if(request.actionMethod.equals("list")){
			request.args.put("where", HMSQL.parseParamFilter(params.get("filter")) + " and category_type=2 ");
		}
		renderArgs.put("nav", "community");
		renderArgs.put("menu", "AdminCommunitys");
		renderArgs.put("categories",Json.toJson(Category.find("category_type=? and status=?", 2, false).fetch()));
	}
	
	public static void delete(String id) throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        notFoundIfNull(object);
        try {
        	removeParent(NumberUtils.toLong(id));
            object._delete();
        } catch (Exception e) {
            flash.error(play.i18n.Messages.get("crud.delete.error", type.modelName));
            redirect(request.controller + ".show", object._key());
        }
        flash.success(play.i18n.Messages.get("crud.deleted", type.modelName));
        redirect(request.controller + ".list");
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
