package co.kr.ucs.spring.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExrUtil {
	
	
	public static String patternToValues(String str,Pattern p,String...values) {
		Matcher m = p.matcher(str);
		int i=0;
		while(m.find()) {
			System.out.println(m.group());
			str = str.replaceFirst(p.pattern(), values[i++]);
		}
		return str;
	}
}
