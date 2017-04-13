package utils.json;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * JSON工具
 * @author zp
 *
 */
public class Json {
	
	/**
	 * 将对象进行JSON序列化
	 * 去除导致循环引用的字段
	 * @param obj
	 * @param excludeFields 需要过滤的字段
	 * @return 序列化后的字符串
	 */
	public static String toJson(Object obj,String...excludeFields){
		ExclusionStrategy excludeStrategy = new SetterExclusionStrategy(excludeFields);
		Gson gson = new GsonBuilder().serializeNulls().setExclusionStrategies(excludeStrategy)
				.create();
        return gson.toJson(obj);
	}
	
	/**
	 * 将对象进行JSON序列化
	 * 去除导致循环引用的对象
	 * @param obj
	 * @param excludeClasses 需要过滤的类
	 * @return 序列化后的字符串
	 */
	public static String toJson(Object obj,Class...excludeClasses){
		ExclusionStrategy excludeStrategy = new SetterExclusionStrategy(excludeClasses);
		Gson gson = new GsonBuilder().setExclusionStrategies(excludeStrategy)
				.create();
		return gson.toJson(obj);
	}
	
	/**
	 * 将对象进行JSON序列化
	 * @param obj
	 * @return 序列化后的字符串
	 */
	public static String toJson(Object obj){
		return new Gson().toJson(obj);
	}

}
