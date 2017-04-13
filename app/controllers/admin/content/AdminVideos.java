package controllers.admin.content;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.category.Category;
import models.category.CategoryItem;
import models.content.Video;
import play.data.binding.Binder;
import play.data.validation.Password;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.libs.Crypto;
import play.mvc.Before;
import play.mvc.With;
import utils.sql.HMSQL;

@Login
@For(Video.class)
@With(ActionIntercepter.class)
public class AdminVideos extends CRUD {
	
	@Before
	static void menus(){
		if(request.actionMethod.equals("list")){
			request.args.put("where", HMSQL.parseParamFilter(params.get("filter")));
		}
		renderArgs.put("nav", "content");
		renderArgs.put("menu", "AdminVideos");
	}
	
	/**
	 * 创建视频
	 * @throws Exception
	 */
	public static void create() throws Exception {
		ObjectType type = ObjectType.get(getControllerClass());
		notFoundIfNull(type);
		Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
		constructor.setAccessible(true);
		Model object = (Model) constructor.newInstance();
		Binder.bindBean(params.getRootParamNode(), "object", object);

		validation.valid(object);
		if (validation.hasErrors()) {
			renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
			try {
				render(request.controller.replace(".", "/") + "/blank.html", type, object);
			} catch (TemplateNotFoundException e) {
				render("CRUD/blank.html", type, object);
			}
		}
		for(Field f : object.getClass().getFields()){
			if( f.isAnnotationPresent(Password.class)){
				f.set(object, Crypto.passwordHash(StringUtils.defaultString((String)f.get(object),"")));
			}
			if (f.getName().equals("user_id")) {
				f.set(object, NumberUtils.toLong(session.get("user_id")));
			}
			if (f.getName().equals("username")) {
				f.set(object, session.get("username"));
			}
			if (f.getName().equals("avatar")) {
				f.set(object, session.get("avatar"));
			}
		}
		object._save();
		String category  = params.get("object.category");
		
		if(StringUtils.isNotEmpty(category)){
			List<Category> categories = new Gson().fromJson(category, new TypeToken<List<Category>>(){}.getType());
			for(Category c : categories){
				new CategoryItem((Long)object._key(), "Video", c.id).save();
			}
		}
		
		flash.success(play.i18n.Messages.get("crud.created", type.modelName));
		if (params.get("_save") != null) {
			redirect(request.controller + ".list");
		}
		if (params.get("_saveAndAddAnother") != null) {
			redirect(request.controller + ".blank");
		}
		redirect(request.controller + ".show", object._key());
	}
}
