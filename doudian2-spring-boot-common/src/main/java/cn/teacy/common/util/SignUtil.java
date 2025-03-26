package cn.teacy.common.util;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUtil {

    // 计算签名
    public static String sign(String appKey, String appSecret, String method, String timestamp, String paramJson) {
        // 按给定规则拼接参数
        String paramPattern = "app_key" + appKey + "method" + method + "param_json" + paramJson + "timestamp" + timestamp + "v2";
        String signPattern = appSecret + paramPattern + appSecret;
        System.out.println(signPattern);
        log.debug("Sign pattern: {}", signPattern);

        return hmac(signPattern, appSecret);
    }

    public static String signForValidate(String appKey, String appSecret, String paramJson, String timestamp, String method) {
        String signPattern = appSecret + "app_key" + appKey + "param_json" + paramJson + "timestamp" + timestamp + appSecret;
        return StrUtil.equals(method, "md5", true) ? md5(signPattern) : hmac(signPattern, appSecret);
    }

    // 计算hmac
    public static String hmac(String plainText, String appSecret) {
        Mac mac;
        try {
            byte[] secret = appSecret.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(secret, "HmacSHA256");

            mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return "";
        }

        byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] digest = mac.doFinal(plainBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b: digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String md5(String plainText) {
        return DigestUtils.md5Hex(plainText);
    }

    public static void main(String[] args) {

        System.out.println(sign(
                "test_app_key",
                "test_app_secret",
                "test_method",
                String.valueOf(System.currentTimeMillis() / 1000),
                MarshalUtil.marshal(Map.of(
                        "plain_text", "&<>='/ô汉😀",
                        "auth_id", "12345",
                        "is_support_index", false,
                        "sensitive_type", 2
                ))
        ));
    }

}
