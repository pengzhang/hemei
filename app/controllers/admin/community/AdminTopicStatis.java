package controllers.admin.community;

import annotation.For;
import annotation.Login;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.content.Article;
import play.mvc.Before;
import play.mvc.With;
import utils.sql.HMSQL;

@Login
@For(Article.class)
@With(ActionIntercepter.class)
public class AdminTopicStatis extends CRUD {
	
	@Before
	static void menus(){
		if(request.actionMethod.equals("list")){
			request.args.put("where", HMSQL.parseParamFilter(params.get("filter")) + " and article_type=2 ");
		}
		renderArgs.put("nav", "community");
		renderArgs.put("menu", "AdminTopicStatis");
	}
	
}
