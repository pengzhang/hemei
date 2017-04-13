package models.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import annotation.Hidden;
import models.BaseModel;
import play.data.validation.Required;

/**
 * 友情链接
 * @author zp
 *
 */
@Entity
@Table(name="friendlink")
@org.hibernate.annotations.Table(comment="友情链接", appliesTo = "friendlink")
public class FriendLink extends BaseModel implements Serializable {
	
	@Required(message="请填写链接名称")
	@Column(columnDefinition="varchar(100) comment '链接名称'")
	public String link_name;
	
	@Required(message="请填写链接URL")
	@Column(columnDefinition="varchar(1000) comment '链接URL'")
	public String link_url;
	
	@Hidden
	@Column(columnDefinition="varchar(500) comment '链接图标'")
	public String link_favicon;
	
	@Column(columnDefinition="int comment '链接顺序'")
	public int link_sequence;
	
	public FriendLink() {
		super();
	}

	@Override
	public String toString() {
		return link_name;
	}
}
