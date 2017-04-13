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
 * 文章
 * @author zp
 *
 */
@Entity
@Table(name="article")
@org.hibernate.annotations.Table(comment = "文章管理", appliesTo = "article")
public class Article extends BaseModel implements Serializable {

	//文章部分
	
	@Column(columnDefinition = "varchar(500) comment '文章标题'")
	public String title;
	
	@MaxSize(value=50000)
	@Column(columnDefinition = "text comment '文章内容'")
	public String content;
	
	@Hidden
	@Column(columnDefinition = "varchar(500) comment '封面图'")
	public String cover;
	
	@Column(columnDefinition = "varchar(100) comment '作者'")
	public String author;
	
	//用户部分
	
	@Hidden
	@Column(columnDefinition = "bigint comment '用户ID'")
	public long user_id;
	
	@Hidden
	@Column(columnDefinition = "varchar(100) comment '用户名'")
	public String username;
	
	@Hidden
	@Column(columnDefinition = "varchar(500) comment '用户头像'")
	public String avatar;
	
	
	//计数部分
	
	@Hidden
	@Column(columnDefinition = "bigint comment '浏览总数'")
	public long view_total = 0;  //浏览总数
	
	@Hidden
	@Column(columnDefinition = "bigint comment '评论总数'")
	public long comment_total = 0; //评论总数
	
	@Hidden
	@Column(columnDefinition = "bigint comment '赞总数'")
	public long like_total = 0; //赞总数
	
	
	//类型: 用于兼容社群帖子
	
	@Exclude
	@Column(columnDefinition = "tinyint default 1 comment '类型:1:文章,2:帖子'")
	public int article_type = 1; 
	
	
	//帖子特有属性
	@Exclude
	@Column(columnDefinition = "tinyint default 0 comment '类型:0:普通,2:置顶'")
	public boolean recommend = false; 
	
	@Exclude
	@Column(columnDefinition = "tinyint default 1 comment '类型:0:私有,1:公开'")
	public boolean open = true; 
	
	@Exclude
	@Column(columnDefinition = "tinyint default 0 comment '类型:0:普通,1:精华'")
	public boolean quality = false; 
	

	public Article() {
		super();
	}

	public Article(String title, String content, String cover, String author) {
		super();
		this.title = title;
		this.content = content;
		this.cover = cover;
		this.author = author;
	}

	@Override
	public String toString() {
		return title;
	}
	
	
	
}
