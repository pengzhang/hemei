package models.content;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import annotation.Exclude;
import annotation.Hidden;
import models.BaseModel;
import play.data.validation.MaxSize;

/**
 * 内容访问统计管理
 * @author zp
 *
 */
@Entity
@Table(name="content_access_statis")
@org.hibernate.annotations.Table(comment = "内容访问统计管理", appliesTo = "content_access_statis")
public class ContentAccessStatis extends BaseModel implements Serializable {

	//评论的对象
	@Column(columnDefinition="bigint default 0 comment '对象ID'")
	public long oid;
	
	@Column(columnDefinition="varchar(100) comment '对象类型'")
	public String otype;
	
	//计数部分
	@Column(columnDefinition = "bigint comment '浏览总数'")
	public long view_total = 0;  //浏览总数
	
	@Column(columnDefinition = "bigint comment '评论总数'")
	public long comment_total = 0; //评论总数
	
	@Column(columnDefinition = "bigint comment '赞总数'")
	public long like_total = 0; //赞总数
	
}
