package models.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.BaseModel;
import play.i18n.Messages;

@Entity
@Table(name="user_role")
@org.hibernate.annotations.Table(comment="用户角色关系管理", appliesTo = "user_role")
public class UserRole extends BaseModel implements Serializable {

	@Column(columnDefinition="bigint comment '用户ID'")
	public long user_id;
	
	@Column(columnDefinition="varchar(100) comment '用户名'")
	public String username;
	
	@Column(columnDefinition="bigint comment '角色ID'")
	public long role_id;
	
	@Column(columnDefinition="varchar(100) comment '角色名称'")
	public String role_name;

	public UserRole() {
		super();
	}

	public UserRole(long user_id, long role_id) {
		super();
		this.user_id = user_id;
		this.role_id = role_id;
	}

	public UserRole(long user_id, String username, long role_id, String role_name) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.role_id = role_id;
		this.role_name = role_name;
	}

	@Override
	public String toString() {
		return Messages.get("role_id") + "/" + role_id + "/" + Messages.get("user_id") + "/" + user_id;
	}
}
