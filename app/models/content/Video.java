package models.content;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import annotation.Hidden;
import models.BaseModel;
import play.data.validation.MaxSize;

/**
 * 视频
 * @author zp
 *
 */
@Entity
@Table(name="video")
@org.hibernate.annotations.Table(comment = "视频管理", appliesTo = "video")
public class Video extends BaseModel implements Serializable {

	//文章部分
	
	@Column(columnDefinition = "varchar(500) comment '视频标题'")
	public String title;
	
	@MaxSize(value=5000)
	@Column(columnDefinition = "varchar(5000) comment '视频简介'")
	public String video_desc;
	
	@Hidden
	@Column(columnDefinition = "varchar(2000) comment '视频链接'")
	public String video_url;
	
	@Hidden
	@Column(columnDefinition = "varchar(500) comment '封面图'")
	public String cover;
	
	@Column(columnDefinition = "varchar(100) comment '作者'")
	public String author;
	
	//用户部分
	@Hidden
	@Column(columnDefinition = "bigint comment '用户ID'")
	public long user_id;
	

	public Video() {
		super();
	}

	@Override
	public String toString() {
		return title;
	}
	
	
	
}
