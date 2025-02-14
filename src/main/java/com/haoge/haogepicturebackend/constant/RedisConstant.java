package com.haoge.haogepicturebackend.constant;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.util.DigestUtils;

/**
 * RedisKeyConstant 类用于生成 Redis 键的常量和工具方法。
 * 该类提供了统一的 Redis 键前缀管理，确保键的生成符合规范。
 */
public class RedisConstant {

    private static final String COMMON_KEY_PREFIX = "haoge:picture:";

    /**
     * 私有构造函数，防止实例化。
     * 该类仅包含静态方法和常量，不需要实例化。
     */
    private RedisConstant() {
        throw new UnsupportedOperationException("RedisKeyConstant is a utility class and cannot be instantiated");
    }

    /**
     * 获取带有公共前缀的 Redis 键，支持不定参数。
     *
     * @param objectKey 前缀的对象键
     * @param keys      Redis 键的各个部分
     * @return 完整的 Redis 键
     * @throws IllegalArgumentException 如果传入的 keys 数组为空或所有元素都为空
     */
    public static String getRedisKey(Object objectKey, String... keys) {
        if (keys == null || keys.length == 0) {
            throw new IllegalArgumentException("At least one key part must be provided");
        }

        // 构建公共前缀
        StringBuilder redisKeyBuilder = new StringBuilder(COMMON_KEY_PREFIX);

        // 处理 objectKey
        if (objectKey != null) {
            String keyPart = objectKey instanceof String ? (String) objectKey
                    : DigestUtils.md5DigestAsHex(JSONUtil.toJsonStr(objectKey).getBytes());
            redisKeyBuilder.append(keyPart).append(":");
        }

        // 拼接其他部分
        for (String key : keys) {
            if (key != null && !key.trim().isEmpty()) {
                redisKeyBuilder.append(key).append(":");
            }
        }

        // 删除最后一个多余的 ":"，如果有的话
        int length = redisKeyBuilder.length();
        if (length > 0 && redisKeyBuilder.charAt(length - 1) == ':') {
            redisKeyBuilder.deleteCharAt(length - 1);
        }

        return redisKeyBuilder.toString();
    }

    /**
     * 获取缓存过期时间，随机5-10分钟
     */
    public static int getExpireTime() {
        return 300 + RandomUtil.randomInt(0, 300);
    }
}