package com.edu.mju.ugomall.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 通过反射方式获取对象名
 *
 * @author 67072
 */
public class GetFieldValueByName {

    /**
     * 通过反射机制获取属性值
     *
     * @param fieldName
     * @param o
     * @return
     */
    public Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过对象名设置属性
     * @param fieldName
     * @param object
     * @param value
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void setFieldValueByFieldName(String fieldName, Object object, Object value) throws NoSuchFieldException, IllegalAccessException {
        Class c = object.getClass();
        Field field = c.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
//        try {
//
//        Class c = object.getClass();
//        Field f = c.getDeclaredField(fieldName);
//        f.setAccessible(true);
//        f.set(object, value);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
