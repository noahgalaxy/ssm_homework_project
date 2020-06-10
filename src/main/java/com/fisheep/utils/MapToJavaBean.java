package com.fisheep.utils;

import java.lang.reflect.Field;
import java.util.Map;

public class MapToJavaBean {

    /**
     *
     * @param map,属性map
     * @param javaBeanClass：目标字节码
     * @param <T> 实例类型
     * @return 返回创建好并且赋值的实例,异常返回null
     */
    public static <T> T mapToJavaBeanByReflect(Map<String , Object> map, Class<T> javaBeanClass){
        try {
            T t = javaBeanClass.newInstance();
            for(Map.Entry<String, Object> entry: map.entrySet()){

                try {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    Field declaredField = javaBeanClass.getDeclaredField(key);
                    declaredField.setAccessible(true);
                    declaredField.set(t, value);
                    declaredField.setAccessible(false);

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

            }

            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
