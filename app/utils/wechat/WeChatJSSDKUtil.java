package utils.wechat;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonObject;

import play.Play;
import play.cache.Cache;
import play.libs.WS;

public class WeChatJSSDKUtil {
	
	/**
	 * 获取微信的JSSDK的访问令牌
	 * <br>
	 * <br>需要配置:
	 * <br>wx.appid
	 * <br>wx.appsecret
	 * @return accesstoken字符串
	 */
	public static String wx_accesstoken(){
		String access_token = (String) Cache.get("wx_access_token");
		if(!StringUtils.isEmpty(access_token)){
			return access_token;
		}else{

			String appid = Play.configuration.getProperty("wx.appid");
			String appsecret = Play.configuration.getProperty("wx.appsecret");
			JsonObject obj = WS.url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret).get().getJson().getAsJsonObject();
			if(obj != null){
				access_token = obj.get("access_token").getAsString();
				if(!StringUtils.isEmpty(access_token)){
					System.out.println(access_token);
					Cache.set("wx_access_token", access_token, "2h");
					return access_token;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取JSSDK的Ticket
	 * @return Ticket字符串
	 */
	public static String jsapi_ticket(){
		String jsapi_ticket = (String) Cache.get("wx_jsapi_ticket");
		if(!StringUtils.isEmpty(jsapi_ticket)){
			return jsapi_ticket;
		}else{
			String access_token = wx_accesstoken();
			JsonObject tobj = WS.url("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi").get().getJson().getAsJsonObject();
			if(tobj != null){
				jsapi_ticket = tobj.get("ticket").getAsString();
				if(!StringUtils.isEmpty(jsapi_ticket)){
					System.out.println(jsapi_ticket);
					Cache.set("wx_jsapi_ticket", jsapi_ticket, "2h");
					return jsapi_ticket;
				}
			}
		}
		return null;
	}

}
