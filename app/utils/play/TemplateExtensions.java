package utils.play;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import play.Logger;
import play.i18n.Messages;
import play.templates.JavaExtensions;
import utils.date.RelativeDateFormat;

/**
 * Play FrameWork的模块扩展
 * @author zp
 *
 */
public class TemplateExtensions extends JavaExtensions {

	/**
	 * 切割字符串
	 * @param str 字符串
	 * @param length 要保留的长度
	 * @return 处理后的字符串
	 * <br>
	 * <br>String str = "abc123456";
	 * <br>str.cutString(str, 10); == "abc123456"
	 * <br>str.cutString(str, 3); == "abc..."
	 */
	public static String cutString(String str, int length) {
		if (length < 0) {
			return "";
		}
		if (length > str.length()) {
			return str.substring(0, str.length());
		}
		return str.substring(0, length) + "...";
	}
	
	/**
	 * 切割字符串
	 * @param str 字符串
	 * @param beginIndex 开始位置
	 * @param endIndex  结束位置
	 * @return 处理后的字符串
	 * <br>
	 * <br>String str = "abc123456";
	 * <br>str.cutString(str, 1, 3); == "bc1..."
	 * <br>str.cutString(str, 1, 10); == "bc123456"
	 */
	public static String cutString(String str, int beginIndex, int endIndex) {
		if (beginIndex < 0) {
			return "";
		}
		if (endIndex > str.length()) {
			return str.substring(beginIndex, str.length());
		}
		int subLen = endIndex - beginIndex;
		if (subLen < 0) {
			return "";
		}
		return str.substring(beginIndex, endIndex) + "...";
	}
	
	/**
	 * 将字符串转换成人民币形式
	 * @param str 要转换的字符串
	 * @return xxx.xx形式
	 */
	public static String rmb(String str) {
		return String.format("%.2f", Float.parseFloat(str));
	}
	
	/**
	 * 将特殊字符串国际化
	 * @param str 要国际化的字符串
	 * @return 国际化后的字符串
	 * <br> 
	 * user.name = 用户名称
	 */
	public static String i18n(String str) {
		String[] messages = str.split("[.]");
		StringBuilder sb = new StringBuilder();
		for(String msg : messages){
			sb.append(Messages.get(msg));
		}
		return sb.toString();
	}
	
	/**
	 * 将时间戳字符串转换成日期字符串形式
	 * @param str 时间戳字符串
	 * @return 日期字符串
	 * <br>
	 * 1234567 == "2000-01-01 00:00:00"
	 */
	public static String date(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(Long.parseLong(str)));
	}
	
	/**
	 * 将时间戳字符串转换成相对时间形式
	 * @param str 时间戳字符串
	 * @return 相对时间字符串
	 * <br>
	 * 123456 == "2天前"
	 */
	public static String relativeDate(Long str) {
		return RelativeDateFormat.format(new Date(str));
	}

	/**
	 * 获取HTML字符串的第一张图片地址
	 * @param str HTML字符串
	 * @return 第一张图片地址
	 */
	public static String firstImage(String str) {
		String image = "";
		try {
			Document doc = Jsoup.parseBodyFragment(str);
			image = doc.getElementsByTag("img").first().attr("src");
		} catch (Throwable t) {
			image = "";
		}
		return image;
	}
	
	/**
	 * 获取HTML字符串中Text文字
	 * @param str HTML字符串
	 * @return Text文字
	 * <br>
	 * &lt;p&gt;123456&lt;p&gt;  == 123456
	 * 
	 */
	public static String htmlText(String str) {
		String text = "";
		try {
			Document doc = Jsoup.parseBodyFragment(str);
			text = doc.getAllElements().text();
		} catch (Throwable t) {
			text = "";
		}
		return text;
	}

	/**
	 * 将时间戳进行特殊格式化
	 * @param str 时间戳字符串
	 * @return 格式化后的日期字符串
	 * <br>
	 * <br>如果时间戳的YEAR与当前时间的YEAR一致 
	 * <br>MM-dd HH:mm
	 * <br>否则
	 * <br>yy-MM-dd HH:mm
	 */
	public static String dateFormatYY(Long str) {

		try {
			Calendar currenDate = Calendar.getInstance();
			currenDate.setTime(new Date());
			int currentYear = currenDate.get(Calendar.YEAR);

			Date date = new Date(str);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (cal.get(Calendar.YEAR) == currentYear) {
				return DateFormatUtils.format(date, "MM-dd HH:mm");
			} else {
				return DateFormatUtils.format(date, "yy-MM-dd HH:mm");
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return "";
		}

	}

}