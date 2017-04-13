package models.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.BaseModel;

/**
 * 前台菜单
 * @author zp
 *
 */
@Entity
@Table(name="front_navigation")
@org.hibernate.annotations.Table(comment="前台菜单", appliesTo = "front_navigation")
public class FrontNavigation extends BaseModel implements Serializable {
	
	@Column(columnDefinition="varchar(100) comment '菜单名称'")
	public String nav_name;
	
	@Column(columnDefinition="varchar(1000) comment '菜单URL'")
	public String nav_url;
	
	@Column(columnDefinition="int comment '菜单顺序'")
	public int nav_sequence;
	
	public FrontNavigation() {
		super();
	}

	public FrontNavigation(String nav_name, String nav_url, int nav_sequence) {
		super();
		this.nav_name = nav_name;
		this.nav_url = nav_url;
		this.nav_sequence = nav_sequence;
	}

	@Override
	public String toString() {
		return nav_name;
	}
}
