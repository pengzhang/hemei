package controllers.admin.mall;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import annotation.For;
import annotation.Login;
import commons.data.ResponseData;
import controllers.CRUD;
import controllers.admin.ActionIntercepter;
import models.mall.Order;
import models.mall.OrderItem;
import play.Play;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.libs.Codec;
import play.mvc.Before;
import play.mvc.With;
import plugins.router.Get;
import plugins.router.Post;
import utils.wechat.WeChatJSSDKUtil;

@Login(unless={"order_express","order_add_express"})
@For(Order.class)
@With(ActionIntercepter.class)
public class AdminOrders extends CRUD {
	
	@Before
	static void menus(){
		renderArgs.put("nav", "mall");
		renderArgs.put("menu", "AdminOrders");
	}
	
	public static void list(int page, String search, String searchFields, String orderBy, String order, boolean status) {
		ObjectType type = ObjectType.get(getControllerClass());
		notFoundIfNull(type);
		if (page < 1) {
			page = 1;
		}
		String where = (String) request.args.get("where");
		if(StringUtils.isEmpty(order)){ order="DESC"; }
		if(StringUtils.isEmpty(orderBy)){ orderBy="id"; }
		List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, where);
		Long count = type.count(search, searchFields, where);
		Long totalCount = type.count(null, null, where);
		try {
			render(type, objects, count, totalCount, page, orderBy, order, status);
		} catch (TemplateNotFoundException e) {
			render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order, status);
		}
	}
	
	/**
	 * 获取订单详情
	 * @param id
	 */
	@Get("/admin/adminorders/order/detail")
	public static void order_detail(String id){
		Order order = Order.find("out_trade_no", id).first();
		List<OrderItem> order_items = OrderItem.find("order_id", order.id).fetch();
		String domain = request.host;
		render(order, order_items, domain);
	}
	
	/**
	 * 通过微信扫码功能
	 * 添加订单快递信息
	 * @param id
	 */
	@Get("/admin/adminorders/order/express")
	public static void order_express(String id){
		
		String noncestr = RandomStringUtils.randomAlphanumeric(6);
		String jsapi_ticket = WeChatJSSDKUtil.jsapi_ticket();
		String timestamp = String.valueOf(System.currentTimeMillis());
		String url = "http://"+request.host+request.url;
		
		StringBuffer sb = new StringBuffer();
		sb.append("jsapi_ticket="+jsapi_ticket);
		sb.append("&noncestr="+noncestr);
		sb.append("&timestamp="+timestamp);
		sb.append("&url="+url);
		
		renderArgs.put("appid", Play.configuration.getProperty("wx.appid","wxc44f09ea20190909"));
		renderArgs.put("noncestr", noncestr);
		renderArgs.put("jsapi_ticket", jsapi_ticket);
		renderArgs.put("timestamp", timestamp);
		renderArgs.put("url", url);
		renderArgs.put("signature", Codec.hexSHA1(sb.toString()));
		render(id);
	}
	
	/**
	 * 添加订单的快递信息
	 * @param id
	 */
	@Post("/admin/adminorders/order/express")
	public static void order_add_express(String id){
		String express_no = params.get("express_no");
		Order order = Order.find("out_trade_no", id).first();
		order.express_no = express_no;
		order.save();
		renderJSON(ResponseData.response(true, "快递单号记录成功"));
	}
	
	/**
	 * 查询订单是否存在快递单号
	 * @param id
	 */
	@Get("/admin/adminorders/order/exist/expressno")
	public static void order_exist_express(String id){
		Order order = Order.find("out_trade_no", id).first();
		if(StringUtils.isNotEmpty(order.express_no)){
			renderJSON(ResponseData.response(true, order.express_no, "快递单号"));
		}
		renderJSON(ResponseData.response(false, "暂无快递单号"));
	}
}
