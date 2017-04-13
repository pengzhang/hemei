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
 * 社群帖子管理
 * @author zp
 *
 */
@Entity
@Table(name="article_subject")
@org.hibernate.annotations.Table(comment = "社群帖子管理", appliesTo = "article_subject")
public class ArticleSubject extends BaseModel implements Serializable {
	
	@Exclude
	@Column(columnDefinition = "bigint default 0 comment '关联的实体文章ID'")
	public long article_id; 
	
	@Exclude
	@Column(columnDefinition = "tinyint default 0 comment '类型:0:普通,2:置顶'")
	public boolean recommend = false; 
	
	@Exclude
	@Column(columnDefinition = "tinyint default 1 comment '类型:0:私有,1:公开'")
	public boolean open = true; 
	
	@Exclude
	@Column(columnDefinition = "tinyint default 0 comment '类型:0:普通,1:精华'")
	public boolean quality = false; 
	
}
