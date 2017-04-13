package models.content;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import annotation.Exclude;
import annotation.Hidden;
import models.BaseModel;
import models.user.User;
import play.data.validation.MaxSize;
/**
 * 评论
 * @author zp
 *
 */
@Entity
@Table(name="comment")
@org.hibernate.annotations.Table(comment = "评论管理", appliesTo = "comment")
public class Comment extends BaseModel implements Serializable{

	//评论的对象
	@Exclude
	@Column(columnDefinition="bigint default 0 comment '评论对象ID'")
	public long oid;
	
	@Exclude
	@Column(columnDefinition="varchar(100) comment '评论对象类型'")
	public String otype;
	
	//评论
	@MaxSize(value=500)
	@Column(columnDefinition="varchar(1000) comment '评论内容'")
	public String comment;
	
	//父评论部分
	@Hidden
	@Column(columnDefinition="bigint comment '父评论ID'")
	public long pid = 0;
	
	//用户部分
	@Hidden
	@Column(columnDefinition = "bigint comment '用户ID'")
	public long user_id;
	
	public Comment() {
		super();
	}

	public Comment(long oid, String otype, String comment, long pid, long user_id) {
		super();
		this.oid = oid;
		this.otype = otype;
		this.comment = comment;
		this.pid = pid;
		this.user_id = user_id;
	}
}
