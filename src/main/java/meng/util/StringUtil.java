package meng.util;

/**
 * 格式化字符串
 * 例如： formateString("xxx{0}bbb",1) = xxx1bbb
 * @author dell
 *
 */
public class StringUtil {
	
	public static String formateString(String str, String... params)
	{
		for (int i = 0; i < params.length; i++) {
			str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
		}
		return str;
	}

}
