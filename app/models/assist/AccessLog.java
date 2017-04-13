package models.assist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.google.gson.Gson;

import models.BaseModel;

/**
 * 访问日志
 * @author zp
 *
 */

@Entity
@Table(name="access_log")
@org.hibernate.annotations.Table(comment="访问日志", appliesTo = "access_log")
public class AccessLog extends BaseModel implements Serializable{
	
	@Column(columnDefinition="varchar(50) comment '服务器域名或IP'")
	public String host;
	
	@Column(columnDefinition="varchar(50) comment '远程IP'")
	public String remote_ip;
	
	@Column(columnDefinition="varchar(50) comment '用户名'")
	public String username;
	
	@Column(columnDefinition="varchar(2000) comment '请求地址'")
	public String request_url;
	
	@Column(columnDefinition="datetime comment '访问日期'")
	public Date access_date;
	
	@Column(columnDefinition="varchar(2000) comment 'ReferUrl'")
	public String refer_url;
	
	@Column(columnDefinition="varchar(1000) comment 'UserAgent'")
	public String user_agent;
	
	public AccessLog() {
	}
	
	public AccessLog(String host, String remote_ip, String user, String request_url, Date access_date, String refer_url, String user_agent) {
		super();
		this.host = host;
		this.remote_ip = remote_ip;
		this.username = user;
		this.request_url = request_url;
		this.access_date = access_date;
		this.refer_url = refer_url;
		this.user_agent = user_agent;
	}

	public static Map<String, String> getRecentAccessRecord(int day) {

		List<String> chart_title = new ArrayList<String>();
		List<Long> chart_content = new ArrayList<Long>();
		for(int i=0 ; i<day ; i++ ){
			String title = DateFormatUtils.format(DateUtils.addDays(new Date(), -i), "yyyy-MM-dd");
			chart_title.add(title);
			Date start = DateUtils.truncate(DateUtils.addDays(new Date(), -i), Calendar.DAY_OF_MONTH);
			Date end = DateUtils.truncate(DateUtils.addDays(new Date(), -(i-1)), Calendar.DAY_OF_MONTH);
			chart_content.add(AccessLog.count("access_date >= ? and access_date < ?", start, end ));
		}

		Map<String,String> map = new HashMap<String,String>();
		map.put("chart_title", new Gson().toJson(chart_title).toString());
		map.put("chart_content", new Gson().toJson(chart_content).toString());

		return map;
	}

}
