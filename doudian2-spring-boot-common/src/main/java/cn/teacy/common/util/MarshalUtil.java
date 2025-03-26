package cn.teacy.common.util;


import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


/**
 * API 参数序列化工具
 *
 * @see <a href="https://connect.douyinec.com/view/article/3/19/13">Java 调用说明</a>`
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MarshalUtil {

    public static String marshal(Object o) {
        String raw = CustomGson.toJson(o);
        Map<?, ?> m = CustomGson.fromJson(raw, LinkedTreeMap.class); // 执行反序列化，把所有JSON对象转换成LinkedTreeMap
        return CustomGson.toJson(m); // 重新序列化，保证JSON所有层级上Key的有序性
    }

    public static String toJson(Object o) {
        return CustomGson.toJson(o);
    }

    private static final Gson CustomGson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // 使用下划线分隔符
            .registerTypeAdapter(LinkedTreeMap.class, newMapSerializer()) // 定制LinkedTreeMap序列化，确保所有key按字典序排序
            .registerTypeAdapter(Integer.class, newNumberSerializer()) // 定制数值类型的序列化，确保整数输出不带小数点
            .registerTypeAdapter(Long.class, newNumberSerializer()) // 同上
            .registerTypeAdapter(Double.class, newNumberSerializer()) // 同上
            .disableHtmlEscaping() // 禁用Html Escape，确保符号不转义：&<>='
            .create();

    // 为LinkedTreeMap定制的序列化器
    private static JsonSerializer<LinkedTreeMap<?, ?>> newMapSerializer() {
        return (src, typeOfSrc, context) -> {
            List<String> keys = src.keySet().stream().map(Object::toString).sorted().toList();
            JsonObject o = new JsonObject();
            for (String k : keys) {
                o.add(k, context.serialize(src.get(k)));
            }
            return o;
        };
    }

    // 为Number定制化的序列化器
    private static <T extends Number> JsonSerializer<T> newNumberSerializer() {
        return (number, type, context) -> {
            if (number instanceof Integer) {
                return new JsonPrimitive(number.intValue());
            }
            if (number instanceof Long) {
                return new JsonPrimitive(number.longValue());
            }
            if (number instanceof Double) {
                long longValue = number.longValue();
                double doubleValue = number.doubleValue();
                if (longValue == doubleValue) {
                    return new JsonPrimitive(longValue);
                }
            }
            return new JsonPrimitive(number);
        };
    }

    public static void main(String[] args) {
        System.out.println(marshal(Map.of(
                "plain_text", "&<>='/ô汉😀",
                "auth_id", "12345",
                "is_support_index", false,
                "sensitive_type", 2
        )));
    }


}
