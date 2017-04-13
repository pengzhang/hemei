package models.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.BaseModel;
import play.i18n.Messages;

@Entity
@Table(name="user_permission")
@org.hibernate.annotations.Table(comment="角色权限关系管理", appliesTo = "user_permission")
public class UserPermission extends BaseModel implements Serializable{

	@Column(columnDefinition="bigint comment '角色ID'")
	public long role_id;
	
	@Column(columnDefinition="varchar(100) comment '角色名称'")
	public String role_name;
	
	@Column(columnDefinition="bigint comment '权限ID'")
	public long permission_id;
	
	@Column(columnDefinition="varchar(100) comment '权限名称'")
	public String permission_name;
	
	public UserPermission() {
		super();
	}

	public UserPermission(long role_id, long permission_id) {
		super();
		this.role_id = role_id;
		this.permission_id = permission_id;
	}

	public UserPermission(long role_id, String role_name, long permission_id, String permission_name) {
		super();
		this.role_id = role_id;
		this.role_name = role_name;
		this.permission_id = permission_id;
		this.permission_name = permission_name;
	}

	@Override
	public String toString() {
		return Messages.get("role_id") + "/" + role_id + "/" + Messages.get("permission_id") + "/" + permission_id;
	}
}
