package models.setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.BaseModel;

@Entity
@Table(name="alipay_key")
@org.hibernate.annotations.Table(comment="支付宝秘钥信息管理", appliesTo = "alipay_key")
public class AlipayKey extends BaseModel{
	
	@Column(columnDefinition="varchar(255) comment '支付宝PC支付'")
	public String alipay_partner;
	
	@Column(columnDefinition="varchar(255) comment '支付宝PC支付'")
	public String alipay_key;
	
	@Column(columnDefinition="varchar(255) comment '支付宝PC支付'")
	public String alipay_log_path;
	
	@Column(columnDefinition="varchar(255) comment '支付宝PC支付'")
	public String alipay_seller_email;
	
	@Column(columnDefinition="varchar(255) comment '支付宝PC支付'")
	public String alipay_return_url;
	
	@Column(columnDefinition="varchar(255) comment '支付宝PC支付'")
	public String alipay_notify_url;

	@Override
	public String toString() {
		return "AlipayKey";
	}
}
