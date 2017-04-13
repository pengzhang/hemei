package models.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import annotation.Hidden;
import models.BaseModel;

@Entity
@Table(name="todo")
@org.hibernate.annotations.Table(comment = "待办事项", appliesTo = "todo")
public class Todo extends BaseModel {

	@Column(columnDefinition = "varchar(500) comment '代办事项'")
	public String todo;
	
	@Hidden
	@Column(columnDefinition = "bigint comment '创建者'")
	public long create_user_id; //创建者
	
	@Hidden
	@Column(columnDefinition = "bigint comment '完成者'")
	public long done_user_id; //完成者
	
}
