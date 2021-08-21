package cn.enncy.funny.utils;


import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * //TODO
 * <br/>Created in 9:50 2021/8/20
 *
 * @author: enncy
 */
public class StringUtils {
    /**
     * 驼峰转下划线
     * @param str   目标字符串
     * @return: java.lang.String
     */
    public static String humpToUnderline(String str) {
        String regex = "([A-Z])";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group();
            str = str.replaceAll(target, "_"+target.toLowerCase());
        }
        return str;
    }

    /**
     * 下划线转驼峰
     * @param str   目标字符串
     * @return: java.lang.String
     */
    public static String underlineToHump(String str) {
        String regex = "_(.)";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group(1);
            str = str.replaceAll("_"+target, target.toUpperCase());
        }
        return str;
    }


    /**
     *  长度转换器, 中文字符串占2个长度,其他字符占1个
     *
     * @return: int
     */
    public static int lengthConverter(String str){
        String pattern = "[\\u4e00-\\u9fa5]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        int length = 0;
        while (m.find()){
            length += 2;
        }
        length += str.replaceAll(pattern, "").length();
        return length;
    }


    public static String getRquestBaseUrl(HttpServletRequest request){
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

}
