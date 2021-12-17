package cn.enncy.funny.utils;


import com.alibaba.fastjson.JSONObject;

import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;

/**
 * 加密解密工具类
 *
 * @author: enncy
 */
public class SecurityUtils {

    private static final String KEY = "db6c6c411bb7467e996fbd5fda687b18";

    public static class BASE64 {

        public static String encode(String str) {
            return Base64.getEncoder().encodeToString(str.getBytes());
        }

        public static String decode(String str) {
            return new String(Base64.getDecoder().decode(str));

        }
    }

    public static class MD5 {
        /**
         * MD5 加密
         *
         * @param str 被加密的字符串
         * @return: java.lang.String
         */
        public static String encryption(String str, Long time) throws UnsupportedEncodingException {
            System.out.println(str);
            return encryption(str + time);
        }
        public static String encryption(String str) throws UnsupportedEncodingException {
            String s = URLEncoder.encode((str + KEY), "utf-8").toLowerCase(Locale.ROOT);

            return DigestUtils.md5DigestAsHex(s.getBytes(StandardCharsets.UTF_8));

        }
        /**
         * 对 json 字符串加密
         *
         * @param jsonStr 被加密的 json 字符串
         * @return: java.lang.String
         */
        public static String encryptionJSON(String jsonStr) throws UnsupportedEncodingException {
            //这里使用fastjson ，内部会自动进行ascii 码排序。
            String json = JSONObject.parseObject(jsonStr).toJSONString();
            return SecurityUtils.MD5.encryption(json);

        }
    }

    public static class AES {


        /**
         * 生成密钥对象
         */
        private static SecretKey generateKey(byte[] key) throws Exception {
            // 根据指定的 RNG 算法, 创建安全随机数生成器
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // 设置 密钥key的字节数组 作为安全随机数生成器的种子
            random.setSeed(key);

            // 创建 AES算法生成器
            KeyGenerator gen = KeyGenerator.getInstance("AES");
            // 初始化算法生成器
            gen.init(128, random);

            // 生成 AES密钥对象, 也可以直接创建密钥对象: return new SecretKeySpec(key, ALGORITHM);
            return gen.generateKey();
        }

        /**
         * 数据加密: 明文 -> 密文
         */
        public static byte[] encrypt(byte[] plainBytes) throws Exception {
            // 生成密钥对象
            SecretKey secKey = generateKey(KEY.getBytes());

            // 获取 AES 密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化密码器（加密模型）
            cipher.init(Cipher.ENCRYPT_MODE, secKey);

            // 加密数据, 返回密文
            return cipher.doFinal(plainBytes);
        }

        /**
         * 数据解密: 密文 -> 明文
         */
        public static byte[] decrypt(byte[] cipherBytes) throws Exception {
            // 生成密钥对象
            SecretKey secKey = generateKey(KEY.getBytes());

            // 获取 AES 密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化密码器（解密模型）
            cipher.init(Cipher.DECRYPT_MODE, secKey);

            // 解密数据, 返回明文
            return cipher.doFinal(cipherBytes);
        }

    }

}
