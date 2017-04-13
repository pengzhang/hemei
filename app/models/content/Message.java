package models.content;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import annotation.Hidden;
import models.BaseModel;
import play.data.validation.MaxSize;
import play.data.validation.Required;
/**
 * 留言
 * @author zp
 *
 */
@Entity
@Table(name="message")
@org.hibernate.annotations.Table(comment = "留言管理", appliesTo = "message")
public class Message extends BaseModel implements Serializable{
	
	//评论
	@Required(message="请填写留言标题")
	@Column(columnDefinition="varchar(1000) comment '留言标题'")
	public String title;
	
	@MaxSize(value=500)
	@Column(columnDefinition="varchar(1000) comment '留言内容'")
	public String content;
	
	//用户部分
	@Hidden
	@Column(columnDefinition = "bigint comment '用户ID'")
	public long user_id;
	
}
