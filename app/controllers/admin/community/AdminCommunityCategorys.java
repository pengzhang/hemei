package controllers.admin.community;

import java.util.Arrays;
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
public class AdminCommunityCategorys extends CRUD {

	@Before
	static void menus(){
		if(request.actionMethod.equals("list")){
			request.args.put("where", HMSQL.parseParamFilter(params.get("filter")) + " and category_type=4 ");
		}
		renderArgs.put("nav", "community");
		renderArgs.put("menu", "AdminCommunityCategorys");
		renderArgs.put("categories",Json.toJson(Category.find("category_type=? and status=?", 4, false).fetch()));
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

	protected static ObjectType createObjectType(Class<? extends Model> type) {
		return new CustomObjectType(type);
	}

	public static class CustomObjectType extends ObjectType {

		public CustomObjectType(Class<? extends Model> modelClass) {
			super(modelClass);
		}

		@Override
		public List<ObjectField> getFields() {
			String[] fields = {"strength","category_desc"};
			List<ObjectField> result = super.getFields();
			for (ObjectField objectField : result) {
				if (Arrays.asList(fields).contains(objectField.name)) {
					objectField.type = "hidden";
				}
			}
			return result;
		}
	}  
}
