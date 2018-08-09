package com.xuehai.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.math.BigInteger;
import java.util.*;

/**
 * @author ：周黎钢.
 * @date ：Created in 13:54 2018/6/8
 * @description:
 */
public class BeanMapper {

    /**
     * 将源对象转换为目标对象,使用PropertyUtils将不会进行隐式转换，类型不同将抛出异常，但效率更高
     *
     * @param source 源对象
     * @param dest   目标对象
     */
    public static <T> T map(Object source, T dest) {
        try {
            PropertyUtils.copyProperties(dest, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dest;
    }

    /**
     * 将一组源对象转换为一组目标对象
     */
    public static <T> List<T> mapList(Collection sourceList, Class<T> dest) {
        List<T> list = new LinkedList();
        sourceList.forEach(e -> {
            Object destObject = null;
            try {
                destObject = dest.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            list.add(copyProperties(e, (T) destObject));
        });
        return list;
    }

    /**
     * 深拷贝对象
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static <T> T copyProperties(Object source, T target) {
        try {
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return target;
    }

    /**
     * 深拷贝对象
     *
     * @param source               源对象
     * @param target               目标对象
     * @param ignorePropertiesName 需要忽略复制的熟悉名称
     */
    public static void copyProperties(Object source, Object target, String... ignorePropertiesName) {
        try {
            BeanUtils.copyProperties(source, target, ignorePropertiesName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 深拷贝对象, 忽略空值属性
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        try {
            BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 获取空值属性列表
     *
     * @param source 源对象
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());

            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];

        return emptyNames.toArray(result);
    }

    /**
     * 对比两个bean对象，有属性值一样的对象放到目标集合里
     */
    public static void getSamePropertyValuesToList(Object source, Object target, List targetList) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper tg = new BeanWrapperImpl(target);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        PropertyDescriptor[] tgPds = tg.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            for (PropertyDescriptor tgpd : tgPds) {
                if (src.getPropertyValue(pd.getName()) != null && tg.getPropertyValue(tgpd.getName()) != null &&
                        pd.getName().equals(tgpd.getName()) && src.getPropertyValue(pd.getName()).equals(tg.getPropertyValue(tgpd.getName()))) {
                    targetList.add(target);
                }
            }
        }
    }

    /**
     * map转换成bean
     */
    public static void mapToBean(Map source, Object dest) {
        Iterator names = source.keySet().iterator();
        while (names.hasNext()) {
            String name = (String) names.next();
            if (PropertyUtils.isWriteable(dest, name)) {
                Object value = source.get(name);
                if (value != null) {
                    if(value instanceof BigInteger){
                        value=Long.valueOf(value.toString());
                    }
                    try {
                        PropertyUtils.setSimpleProperty(dest, name, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * map集合转换成bean集合
     */
    public static <T> List<T> mapListToBeanList(List<Map> source, Class<T> dest) {
        List<T> result = new LinkedList<>();
        source.forEach((map) -> {
            try {
                T target = dest.newInstance();
                mapToBean(map, target);
                result.add(target);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return result;
    }
}
