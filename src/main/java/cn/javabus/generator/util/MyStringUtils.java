package cn.javabus.generator.util;

/**
 * Created by Owen on 6/18/16.
 */
public class MyStringUtils {

    /**
     * 首字母大写
     * @param name
     * @return
     */
    public static String upperFirstWord(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
//        name = name.substring(0, 1).toUpperCase() + name.substring(1);
//        return name;
    }

    /**
     *
     * convert string from slash style to camel style, such as my_course will convert to MyCourse
     * 下划线转 pascal
     * @param str
     * @return
     */
    public static String dbStringToCamelStyle(String str) {
        if (str != null) {
            if (str.contains("_")) {
                str = str.toLowerCase();
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(str.charAt(0)).toUpperCase());
                for (int i = 1; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c != '_') {
                        sb.append(c);
                    } else {
                        if (i + 1 < str.length()) {
                            sb.append(String.valueOf(str.charAt(i + 1)).toUpperCase());
                            i++;
                        }
                    }
                }
                return sb.toString();
            } else {
                String firstChar = String.valueOf(str.charAt(0)).toUpperCase();
                String otherChars = str.substring(1);
                return firstChar + otherChars;
            }
        }
        return null;
    }

}
