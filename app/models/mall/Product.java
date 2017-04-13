package models.mall;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.RandomStringUtils;

import annotation.Hidden;
import annotation.Price;
import models.BaseModel;
import play.data.validation.MaxSize;
import play.data.validation.Required;

@Entity
@Table(name="product")
@org.hibernate.annotations.Table(comment="商品", appliesTo = "product")
public class Product extends BaseModel {

	//商品基本信息
	
	@Required(message="填写商品编号")
	@Column(columnDefinition = "varchar(100) comment '商品编号'")
	public String product_num = RandomStringUtils.randomNumeric(10);
	
	@Required(message="填写商品名称")
	@Column(columnDefinition = "varchar(100) comment '商品名称'")
	public String product_name;
	
	@MaxSize(value=50000)
	@Column(columnDefinition = "text comment '商品描述'")
	public String product_detail;
	
	@Hidden
	@Column(columnDefinition = "varchar(5000) comment '商品图片(最多10张)'")
	public String product_image;
	
	
	//商品促销信息
	@MaxSize(value=2000)
	@Required(message="填写促销简介")
	@Column(columnDefinition = "varchar(2000) comment '促销简介'")
	public String sale_desc;
	
	
	//物流信息
	@Hidden
	//@Required(message="填写商品发货时间")
	@Column(columnDefinition = "varchar(255) comment '商品发货时间'")
	public String product_delivery_time;
	
	//价格
	@Price
	@Required(message="填写商品价格")
	@Column(columnDefinition = "int comment '商品价格(以分为单位)'")
	public int product_price;

	@Override
	public String toString() {
		return product_name;
	}
}
