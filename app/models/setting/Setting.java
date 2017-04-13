package models.setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.BaseModel;

@Entity
@Table(name="setting")
@org.hibernate.annotations.Table(comment="系统设置", appliesTo = "setting")
public class Setting extends BaseModel {

	@Column(columnDefinition="varchar(100) comment '网站名称'")
	public String site_name;
	
	@Column(columnDefinition="varchar(100) comment '网站域名'")
	public String site_domain;
	
	public Setting() {
		super();
	}
	
	public Setting(String site_name, String site_domain) {
		super();
		this.site_name = site_name;
		this.site_domain = site_domain;
	}
	
	@Override
	public String toString() {
		return site_name;
	}
}
