package utils.json;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Gson 过滤字段工具
 * @author zp
 *
 */
public class SetterExclusionStrategy implements ExclusionStrategy {
	
	private String[] fields;
	private Class<?>[] excludeClasses;
	
	/**
	 * JSON过滤规则
	 * 添加需要过滤的字段
	 * @param fields
	 */
	public SetterExclusionStrategy(String... fields) {
		this.fields = fields;
	}
	
	/**
	 * JSON过滤规则
	 * 添加需要过滤的类
	 * @param excludeClasses
	 */
	public SetterExclusionStrategy(Class<?>... excludeClasses) {
		this.excludeClasses = excludeClasses;
	}

	/**
	 * 过滤类的方法
	 */
	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		
		if (this.excludeClasses == null) {
			return false;
		}

		for (Class<?> excludeClass : excludeClasses) {
			if (excludeClass.getName().equals(arg0.getName())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 过滤字段的方法
	 */
	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if (fields != null) {
			for (String name : fields) {
				if (f.getName().equals(name)) {
					/** true 代表此字段要过滤 */
					return true;
				}
			}
		}
		return false;
	}
}
