package utils.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.ResultTransformer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import commons.data.PageData;
import exceptions.ServiceException;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Model;

/**
 * 用于构建一些复杂的SQLQuery
 * @author zp
 *
 */
public class HMSQL {

	/**
	 * 构建Map形式SQLQuery <br>
	 * 基于Hibernate的createNativeQuery构建  <br>
	 * @param sql 复杂的SQL<br>
	 * @return Query <br>
	 * 
	 * <br>举例SQL: <br>
	 * select u.id, u.username, p.title from post p left join user u on u.id=p.uid where u.id=:uid
	 *
	 */
	public static Query query(String sql){
		Query query  = JPA.em().createNativeQuery(sql);
		//query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		//上面返回的Map的key为大写字母,以下修改小写字母.两行代码执行效果一样.
		query.unwrap(SQLQuery.class).setResultTransformer(new ResultTransformer(){
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {   
			    Map result = new HashMap(tuple.length);   
			    for ( int i=0; i<tuple.length; i++ ) {   
			        String alias = aliases[i];   
			        if ( alias!=null ) {   
			            result.put( alias.toLowerCase(), tuple[i] );   
			        }   
			    } 
			    return result;   
			}

			@Override
			public List transformList(List list) {
				return list;
			}  
		});  
		return query;
	}
	
	
	/**
	 * 构建Model Entity形式的SQLQuery, <br>
	 * 基于Hibernate的createNativeQuery构建  <br>
	 * @param sql 复杂的SQL<br>
	 * @param clazz Model实体 <br>
	 * @return Query <br>
	 * 
	 * <br>举例SQL: <br>
	 * select p.* from post p left join user u on u.id=p.uid where u.id=:uid
	 *
	 */
	public static Query query(String sql, Class clazz){
		Query query  = JPA.em().createNativeQuery(sql, clazz);
		return query;
	}
	
	/**
	 * 多条件查询和搜索数据
	 * @param clazz Model实体对象
	 * @param page 页数
	 * @param size 每页条数
	 * @param search  搜索关键词
	 * @param searchFields 搜索字段
	 * @param orderBy 排序字段
	 * @param order 排序
	 * @param where 复合的where条件
	 * @return Model的实体对象集合
	 * @throws ServiceException
	 */
	public static List<play.db.Model> findByPage(Class clazz, int page, int size, String search, String searchFields, String orderBy, String order, String where) throws ServiceException {
		int offset = (page - 1) * size;
		List<String> properties = searchFields == null ? new ArrayList<String>(0) : Arrays.asList(searchFields.split("[ ]"));
		try {
			return Model.Manager.factoryFor(clazz).fetch(offset, size, orderBy, order, properties, search, where);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询数据失败");
		}
	}


	/**
	 * 多条件查询和搜索数据
	 * @param clazz Model实体对象
	 * @param page 页数
	 * @param size 每页条数
	 * @param search  搜索关键词
	 * @param searchFields 搜索字段
	 * @param orderBy 排序字段
	 * @param order 排序
	 * @param where 复合的where条件
	 * @return PageData形式的Model的实体对象集合
	 * @throws ServiceException
	 * {rows:对象集合, total:总条数}
	 */
	public static PageData findByPageData(Class clazz, int page, int size, String search, String searchFields, String orderBy, String order, String where) throws ServiceException {
		PageData pageData = new PageData();
		int offset = (page - 1) * size;
		List<String> properties = searchFields == null ? new ArrayList<String>(0) : Arrays.asList(searchFields.split("[ ]"));
		try {
			pageData.total = Model.Manager.factoryFor(clazz).count(properties, search, where);
			pageData.rows = Model.Manager.factoryFor(clazz).fetch(offset, size, orderBy, order(order), properties, search, where);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询数据失败");
		}
	}
	
	private static String order(String order){
		if(StringUtils.isEmpty(order)){
			return order;
		}else if(order.equalsIgnoreCase("asc")){
			return "ASC";
		}else if(order.equalsIgnoreCase("desc")){
			return "DESC";
		}else{
			return order;
		}
	}
	
	/**
	 * 将页面的搜索的过滤添加组合成SQL语句形式
	 * @param filter 页面筛选的json
	 * @return  id>0 and filter=xxx
	 * <br>
	 * 暂时只支持组合and形式的组合
	 */
	public static String parseParamFilter(String filter){
		StringBuilder where = new StringBuilder();
		where.append("id>0");
		try{
			JsonArray array = new JsonParser().parse(filter).getAsJsonArray();
			Iterator<JsonElement> elements = array.iterator();

			while(elements.hasNext()){
				JsonElement element = elements.next();
				JsonObject object = element.getAsJsonObject();
				int max = object.get("ValueList").getAsJsonArray().size();
				
				if(max > 0){
					where.append(" and ");
					boolean isMultiple = object.get("isMultiple").getAsBoolean();
					String field = object.get("id").getAsString();
					//复合条件
					if(isMultiple){
						for(int i = 0; i < max; i++){
							String value = object.get("ValueList").getAsJsonArray().get(i).getAsString();
							where.append(field);
							where.append(value);
							if(i < max-1){
								where.append(" and ");
							}
						}
					}else{
						String value = object.get("ValueList").getAsJsonArray().get(0).getAsString();
						where.append(field);
						where.append(value);
					}
				}

			}
		}catch(Exception e){
			Logger.info("params filter json parse error, is not jsonarray");
		}

		return where.toString();
	}
	
}
