package com.leaf.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 拷贝工具封装
 */

public class BeanCopyUtils {
    private BeanCopyUtils(){}

    /**
     * 单个对象复制
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyBean(Object source,Class<T> clazz) {
        //创建目标对象
        T result = null;
        try {
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    /**
     * list集合拷贝
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> copyBeanList(List<?> list,Class<T> clazz){
        return list.stream().map(l -> copyBean(l, clazz)).collect(Collectors.toList());
    }
}
