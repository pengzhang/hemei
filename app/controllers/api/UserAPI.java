package controllers.api;

import commons.data.ResponseData;
import play.mvc.Controller;
import plugins.router.Post;

public class UserAPI extends Controller{
	
	/**
	 * 用户登录接口
	 * @param username 用户名
	 * @param password 密码
	 */
	@Post("/api/user/login")
	public static void login(String username, String password){
		boolean status = false;
		String message = "";
		renderJSON(ResponseData.response(status, message));
	}

}
