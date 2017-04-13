package models.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import annotation.Hidden;
import models.BaseModel;
import play.data.validation.MaxSize;

@Entity
@Table(name="category")
@org.hibernate.annotations.Table(comment = "分类管理", appliesTo = "category")
public class Category extends BaseModel {
	
	@Hidden
	@Column(columnDefinition="bigint default 0 comment '父ID'")
	public long pid;
	
	@Column(columnDefinition="varchar(255) comment '分类名称'")
	public String category;
	
	@Hidden
	@Column(name="category_type",columnDefinition="tinyint default 0 comment '分类类型'")
	public int categoryType = 0;
	
	public Category() {
		super();
	}

	public Category(long pid, String category) {
		super();
		this.pid = pid;
		this.category = category;
	}
	
	public Category(long pid, String category, int categoryType) {
		super();
		this.pid = pid;
		this.category = category;
		this.categoryType = categoryType;
	}

	@Override
	public String toString() {
		return category;
	}
	
	
}
