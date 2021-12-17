package cn.enncy.funny.utils;


import cn.enncy.funny.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 9:50 2021/8/20
 *
 * @author: enncy
 */
public class StringUtils {
    /**
     * 驼峰转下划线
     *
     * @param str 目标字符串
     * @return: java.lang.String
     */
    public static String humpToUnderline(String str) {
        String regex = "([A-Z])";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group();
            str = str.replaceAll(target, "_" + target.toLowerCase());
        }
        return str;
    }

    /**
     * 下划线转驼峰
     *
     * @param str 目标字符串
     * @return: java.lang.String
     */
    public static String underlineToHump(String str) {
        String regex = "_(.)";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group(1);
            str = str.replaceAll("_" + target, target.toUpperCase());
        }
        return str;
    }


    /**
     * 长度转换器, 中文字符串占2个长度,其他字符占1个
     *
     * @return: int
     */
    public static int lengthConverter(String str) {
        String pattern = "[\\u4e00-\\u9fa5]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        int length = 0;
        while (m.find()) {
            length += 2;
        }
        length += str.replaceAll(pattern, "").length();
        return length;
    }


    public static String getRequestBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    public static boolean hasSpecialCharacters(String str) {
        return Pattern.compile("[^\\w]").matcher(str).find();
    }

    public static boolean notEmpty(String str) {
        return str != null && str.length() != 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    /**
     * 判断是否超出范围
     *
     * @param str  字符串
     * @param min  最小值
     * @param max  最大值
     * @param zhCN 是否计入中文
     * @return boolean
     */
    public static boolean outOfRange(String str, int min, int max, boolean zhCN) {
        if (isEmpty(str)) {
            return true;
        }
        if (zhCN) {
            if (lengthConverter(str) < min || lengthConverter(str) > max) {
                return true;
            }

        } else {
            if (str.length() < min || str.length() > max) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否超出范围
     *
     * @param str 字符串
     * @param min 最小值
     * @param max 最大值
     * @return boolean
     */
    public static boolean outOfRange(String str, int min, int max) {
        return outOfRange(str, min, max, false);
    }


    /**
     * 根据 map 创建 query string
     *
     * @param map map
     * @return java.lang.String
     */
    public static String createQueryString(Map<String, Object> map) {
        return map.entrySet().stream().map(k -> encode(k.getKey()) + "=" + encode(k.getValue().toString())).collect(Collectors.joining("&"));
    }

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
