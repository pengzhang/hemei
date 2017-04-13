package models.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import annotation.Exclude;
import models.BaseModel;

@Entity
@Table(name = "category_item")
@org.hibernate.annotations.Table(comment = "分类实体管理", appliesTo = "category_item")
public class CategoryItem extends BaseModel {

	//对象信息
	@Exclude
	@Column(columnDefinition = "bigint default 0 comment '对象ID'")
	public long oid;

	@Exclude
	@Column(columnDefinition = "varchar(100) comment '对象类型'")
	public String otype;

	// 分类信息
	@Exclude
	@Column(columnDefinition = "bigint default 0 comment '分类ID'")
	public long category_id;
	
	public CategoryItem() {
		super();
	}

	public CategoryItem(long oid, String otype, long category_id) {
		super();
		this.oid = oid;
		this.otype = otype;
		this.category_id = category_id;
	}

}
