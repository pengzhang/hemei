package models.mall;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import annotation.Hidden;
import annotation.Price;
import models.BaseModel;
import play.db.jpa.Transactional;

@Entity
@Table(name="order_pay")
@org.hibernate.annotations.Table(comment="订单管理", appliesTo = "order_pay")
public class Order extends BaseModel{
	
	//支付信息
	
	@Column(nullable=false,columnDefinition="varchar(50) comment '商户订单号'")
	public String out_trade_no;
	
	@Column(columnDefinition="varchar(50) comment '银行流水号'")
	public String trade_no;
	
	@Price
	@Column(columnDefinition="int default 0 comment '金额'")
	public long total_fee;
	
	@Column(columnDefinition="varchar(20) comment '支付时间'")
	public String pay_date;
	
	@Column(columnDefinition="tinyint comment '支付方式:1_微信二维码,2:微信公众号,3_支付宝即时,4_支付手机网页'")
	public int trade_type;
	
	@Column(columnDefinition="tinyint comment '申请退款状态:0-假 ,1-真'")
	public boolean refund_status=false;
	
	@Column(columnDefinition="tinyint comment '退款状态:0-假 ,1-真'")
	public boolean isrefund=false;
	
	@Column(columnDefinition="varchar(50) comment '预支付订单号'")
	public String prepay_id;
	
	//购买人信息
	
	@Column(columnDefinition="varchar(100) comment '付款人'")
	public String openid;
	
	@Column(columnDefinition="varchar(100) comment '购买人姓名'")
	public String buyer_name;
	
	@Column(columnDefinition="varchar(100) comment '购买人电话'")
	public String buyer_mobile;
	
	@Column(columnDefinition="varchar(500) comment '购买人地址'")
	public String buyer_address;
	
	//用户部分
	
	@Hidden
	@Column(columnDefinition = "bigint default 0 comment '用户ID'")
	public long user_id;

	@Hidden
	@Column(columnDefinition = "varchar(100) comment '用户名'")
	public String username;

	@Hidden
	@Column(columnDefinition = "varchar(500) comment '用户头像'")
	public String avatar;
	
	//订单信息
	
	@Column(columnDefinition="varchar(255) comment '订单名称'")
	public String subject;
	
	@Column(columnDefinition="varchar(255) comment '订单说明'")
	public String body;
	
	//物流快递信息
	
	@Column(columnDefinition="varchar(255) comment '快递单号'")
	public String express_no;
	
	//支付返回地址
	
	@Column(nullable=false,columnDefinition="varchar(2000) comment '商品展示地址'")
	public String show_url;
	
	@Column(nullable=false,columnDefinition="varchar(2000) comment '返回地址'")
	public String return_url;
	
	@Column(columnDefinition="varchar(2000) comment '通知地址'")
	public String notify_url;
	
	public Order() {
	}

	/**
	 * 生成订单自动生成订单号
	 * @param d
	 */
	public Order(Date d){
		String date = DateFormatUtils.format(d, "yyyyMMddHHmmss");
		String serialNo = RandomStringUtils.randomNumeric(6);
		this.out_trade_no =  date  + serialNo;
	}
	
	/**
	 * 更新订单Openid
	 * @param out_trade_no
	 * @param openid
	 */
	@Transactional
	public static void updateOpenid(String out_trade_no, String openid) {
		Order order = Order.find("out_trade_no", out_trade_no).first();
		order.openid = openid;
		order.updateDate = new Date();
		order.save();
	}

	/**
	 * 选择支付方式
	 * @param out_trade_no
	 * @param trade_type
	 */
	@Transactional
	public static void selectPayWay(String out_trade_no, int trade_type) {
		Order order = Order.find("out_trade_no", out_trade_no).first();
		order.trade_type = trade_type;
		order.updateDate = new Date();
		order.save();
	}
	
	/**
	 * 选择支付方式(微信)
	 * @param out_trade_no
	 * @param trade_type
	 * @param openid
	 * @param perpay_id
	 */
	@Transactional
	public static void selectPayWay(String out_trade_no, int trade_type, String perpay_id) {
		Order order = Order.find("out_trade_no", out_trade_no).first();
		order.trade_type = trade_type;
		order.prepay_id = perpay_id;
		order.updateDate = new Date();
		order.save();
	}

	/**
	 * 支付成功更新状态
	 * @param out_trade_no
	 * @param trade_no
	 */
	@Transactional
	public static void notifyUpdateOrder(String out_trade_no, String trade_no) {
		Order order = Order.find("out_trade_no", out_trade_no).first();
		order.trade_no = trade_no;
		order.status = true;
		order.pay_date = String.valueOf(System.currentTimeMillis());
		order.updateDate = new Date();
		order.save();
	}
	
}
