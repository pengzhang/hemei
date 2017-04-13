package controllers.admin.content;

import org.apache.commons.lang.math.NumberUtils;

import annotation.For;
import annotation.Login;
import commons.data.ResponseData;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.content.Comment;
import play.mvc.Before;
import play.mvc.With;
import plugins.router.Post;

@Login
@For(Comment.class)
@With(ActionIntercepter.class)
public class AdminComments extends CRUD {

	@Before
	static void menus() {
		renderArgs.put("nav", "service");
		renderArgs.put("menu", "AdminComments");
	}
	
	/**
	 * 回复评论
	 * @param id
	 */
	@Post("/admin/admincomments/reply")
	public static void reply(long id){
		System.out.println("==============================" + id);
		String body = params.get("comment");
		long user_id = NumberUtils.toLong(session.get("user_id"));
		
		Comment parent = Comment.findById(id);
		new Comment(parent.oid, parent.otype, body, parent.id, user_id).save();
		renderJSON(ResponseData.response(true, "评论成功"));
	}
	
	/**
	 * 实体添加评论
	 * @param id
	 */
	@Post("/admin/admincomments/object/create")
	public static void reply(long oid, String otype){
		String body = params.get("comment");
		long user_id = NumberUtils.toLong(session.get("user_id"));
		//如果实体为Comment则为评论回复
		if(otype.equalsIgnoreCase("Comment")){
			Comment parent = Comment.findById(oid);
			new Comment(parent.oid, parent.otype, body, parent.id, user_id).save();
		}else{
			new Comment(oid, otype, body, 0, user_id).save();
		}
		
		renderJSON(ResponseData.response(true, "评论成功"));
	}

}
