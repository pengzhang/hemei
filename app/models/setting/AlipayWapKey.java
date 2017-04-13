package models.setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.BaseModel;

@Entity
@Table(name="alipay_wap_key")
@org.hibernate.annotations.Table(comment="手机支付宝秘钥信息管理", appliesTo = "alipay_wap_key")
public class AlipayWapKey extends BaseModel{
	
	@Column(columnDefinition="varchar(255) comment '支付宝WAP支付'")
	public String alipay_wap_partner;
	
	@Column(columnDefinition="varchar(1000) comment '支付宝WAP支付'")
	public String alipay_wap_private_key;
	
	@Column(columnDefinition="varchar(255) comment '支付宝WAP支付'")
	public String alipay_wap_log_path;
	
	@Column(columnDefinition="varchar(255) comment '支付宝WAP支付'")
	public String alipay_wap_return_url;
	
	@Column(columnDefinition="varchar(255) comment '支付宝WAP支付'")
	public String alipay_wap_notify_url;

}
