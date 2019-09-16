/**
 * 
 */
package com.paascloud;

import java.util.Map;

import com.alibaba.ttl.TransmittableThreadLocal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author xyy
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataSourceContextHolder {

    /**
     * The constant threadContext.
     */
    private final static ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new TransmittableThreadLocal<Map<String, Object>>();

    /**
     * Put.
     *
     * @param key   the key
     * @param value the value
     */
    public static void put(String key, Object value) {
        getContextMap().put(key, value);
    }

    /**
     * Remove object.
     *
     * @param key the key
     *
     * @return the object
     */
    public static Object remove(String key) {
        return getContextMap().remove(key);
    }

    /**
     * Get object.
     *
     * @param key the key
     *
     * @return the object
     */
    public static Object get(String key) {
        return getContextMap().get(key);
    }

    /**
     * 取得thread context Map的实例。
     *
     * @return thread context Map的实例
     */
    private static Map<String, Object> getContextMap() {
        return THREAD_CONTEXT.get();
    }

    /**
     * 清理线程所有被hold住的对象。以便重用！
     */
    public static void remove() {
        getContextMap().clear();
    }
}
