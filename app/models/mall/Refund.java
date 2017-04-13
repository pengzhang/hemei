package models.mall;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.time.DateFormatUtils;

import models.BaseModel;
@Entity
@Table(name="order_refund")
@org.hibernate.annotations.Table(comment="退款管理", appliesTo = "order_refund")
public class Refund extends BaseModel{
	
	@Column(nullable=false,columnDefinition="varchar(50) comment '退款批次号 '")
	public String refund_no;

	@Column(nullable=false,columnDefinition="varchar(50) comment '商户订单号'")
	public String out_trade_no;
	
	@Column(columnDefinition="varchar(50) comment '银行流水号'")
	public String trade_no;
	
	@Column(columnDefinition="bigint comment '金额'")
	public long total_fee;
	
	@Column(columnDefinition="tinyint comment '支付方式:1_微信,2:支付宝'")
	public int trade_type;
	
	@Column(columnDefinition="varchar(100) comment '付款人'")
	public String openid;
	
	public static String generateRefundNum() {
		String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		String serialNo = String.valueOf(System.currentTimeMillis()).substring(6);
		String orderId = date + serialNo;
		return orderId;
	}
}
