package controllers.admin;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.handler.codec.http.HttpHeaders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import annotation.Login;
import exceptions.ControllerException;
import models.assist.AccessLog;
import models.user.Permission;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Finally;
import play.mvc.Http;

public class ActionIntercepter extends Controller {

	/*
	 * Custom Configuration
	  #Check User Login
	  check.login=enabled
	  login.url=http://user.hm55.cn/login
	  #Check User Permission  
	  check.permission=enabled
	 */

	@Before()
	private static void actionBeforeProcess() {
		//TODO 设置默认权限
		checkLogin();
		checkPermissions();
		accessLog();
	}

	@After
	private static void actionAfterProcess() {
	}
	
	@Catch(value = ControllerException.class, priority = 2)
	private static void actionControllerExceptionProcess(ControllerException ce) {
		ce.printStackTrace();
		Logger.error("controller exception %s", ce.getMessage());
		error(ce.getMessage());
	}

	@Catch(value = Throwable.class, priority = 1)
	private static void actionExceptionProcess(Throwable throwable) {
		throwable.printStackTrace();
		Logger.error("exception %s", throwable.getMessage());
		error(throwable.getMessage());
	}
	
	@Finally
    static void log() {
        //Logger.info("Response contains : " + response.out);
    }
	
	private static void checkLogin(){
		String login = Play.configuration.getProperty("check.login", "disabled");
		if(login.equals("enabled") && (session.get("username") == null)){
			String login_url = Play.configuration.getProperty("login.url");
			if(StringUtils.isEmpty(login_url)){
				login_url = "/login";
			}
			try {
				Class controller = Class.forName("controllers." + request.action.substring(0, request.action.lastIndexOf(".")));
				if (controller.isAnnotationPresent(Login.class)) {
					boolean flag = true;
					String[] except = ((Login) controller.getAnnotation(Login.class)).unless();//排除的Action

					for(String action : except){
						if(request.actionMethod.equals(action)){
							flag = false;
						}
					}

					if(flag && StringUtils.isEmpty(session.get("uid"))){
						redirect(login_url);
					}
				}
			} catch (ClassNotFoundException e) {
				notFound();
			}

		}
	}
	
	private static void checkPermissions(){
		String permisson = Play.configuration.getProperty("check.permission", "disabled");
		if(permisson.equals("enabled")){
			boolean flag = true;
			try{
				//通过role_id获取缓存中的权限列表
				String rids = session.get("rids");
				List<String> ridArray = new Gson().fromJson(rids, new TypeToken<List<String>>(){}.getType());
				for(String rid : ridArray){
					String cache= (String) Cache.get("rid:"+rid);
					List<Permission> permissions = new Gson().fromJson(cache, new TypeToken<List<Permission>>(){}.getType());
					//权限列表为空,认为没有任何权限
					if(permissions != null && permissions.size()>0){
						for(Permission p : permissions){
							if(request.action.equals(p.action)){
								flag = false;
								break;
							}
						}
					}
				}
				if(flag){
					forbidden();
				}
			}catch(Exception e){
				forbidden();
			}
		}
	}
	
	private static void accessLog(){
		boolean _log2Db = Boolean.parseBoolean(Play.configuration.getProperty("accesslog.log2db", "false"));
		if(_log2Db){
			Http.Header referrer = request.headers.get(HttpHeaders.Names.REFERER.toLowerCase());
			Http.Header userAgent = request.headers.get(HttpHeaders.Names.USER_AGENT.toLowerCase());
			new AccessLog(
					request.host, 
					request.remoteAddress, 
					(StringUtils.isEmpty(request.user)) ? "-" : request.user, 
							request.url,
							request.date,
							(referrer != null) ? referrer.value() : "", 
									(userAgent != null) ? userAgent.value() : ""
					).save();
		}
	}

}
