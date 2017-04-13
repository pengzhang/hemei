package models.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.BaseModel;
import play.data.validation.Password;

@Entity
@Table(name="user")
@org.hibernate.annotations.Table(comment="用户管理", appliesTo = "user")
public class User extends BaseModel implements Serializable {

	@Column(columnDefinition="varchar(100) comment '用户名'")
	public String username;
	@Password
	@Column(columnDefinition="varchar(100) comment '密码'")
	public String password;
	
	@Column(columnDefinition="varchar(100) comment '用户邮箱'")
	public String email;
	
	@Column(columnDefinition="varchar(30) comment '手机号'")
	public String mobile;
	
	@Column(columnDefinition="varchar(1000) comment '用户头像'")
	public String avatar;

	public User() {
		super();
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public User(String username, String password, String avatar) {
		super();
		this.username = username;
		this.password = password;
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return username;
	}
}
