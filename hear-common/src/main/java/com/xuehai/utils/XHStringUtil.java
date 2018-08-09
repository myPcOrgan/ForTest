package com.xuehai.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA
 *
 * @author: zhangcong
 * @date: 2017/9/14 13:31
 * @description: StringUtil类
 */
@Slf4j
public class XHStringUtil {


    /**
     * 提供（相对）精确的除法运算
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    小数点后保留位数
     * @return 两个参数的商
     */
    public static Double division(Double dividend, Double divisor, int scale) {
        if (divisor == 0) {
            return 0.0;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static int totalPageNum(int totalNum, int pageSize) {
        int pageNum = 0;
        pageNum = totalNum / pageSize;
        if (totalNum % pageSize != 0) {
            pageNum++;
        }
        return pageNum;
    }

    /**
     * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_UP
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static float round(float v, int scale) {
        return round(v, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v         需要四舍五入的数字
     * @param scale     小数点后保留几位
     * @param roundMode 指定的舍入模式
     * @return 四舍五入后的结果
     */
    public static float round(float v, int scale, int roundMode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        return b.setScale(scale, roundMode).floatValue();
    }

    /**
     * 提供精确的减法
     *
     * @param n1 需要四舍五入的数字
     * @param n2 需要四舍五入的数字
     * @return 四舍五入后的结果
     */
    public static float subtract(float n1, float n2) {
        BigDecimal totalIntegral = new BigDecimal(n1).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal tempIntegral = new BigDecimal(n2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return totalIntegral.subtract(tempIntegral).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 提供精确的加法
     *
     * @param n1 需要四舍五入的数字
     * @param n2 需要四舍五入的数字
     * @return 四舍五入后的结果
     */
    public static float add(float n1, float n2) {
        BigDecimal totalIntegral = new BigDecimal(n1).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal tempIntegral = new BigDecimal(n2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return totalIntegral.add(tempIntegral).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 四舍五入数字
     *
     * @param n1
     * @return
     */
    public static float formatNum(float n1) {
        BigDecimal num = new BigDecimal(n1).setScale(1, BigDecimal.ROUND_HALF_UP);
        return num.floatValue();
    }


    /**
     * @param prefix 前缀
     * @param split  分隔符
     * @param params 参数
     * @author zhangcong
     * @date 21:02 2017/11/14
     * @describe 生成Redis的主键
     */
    public static String generateRedisKey(String prefix, String split, String... params) {
        String primaryKey = null;
        StringBuffer stringBuffer = new StringBuffer(ConstantUtil.RedisPrefix.REDIS_PREFIX).append(split);
        if (StringUtils.isNotBlank(prefix)) {
            stringBuffer.append(prefix).append(StringUtils.isNotBlank(split) ? split : "");
        }

        if (null != params) {
            for (String key : params) {
                stringBuffer.append(key).append(StringUtils.isNotBlank(split) ? split : "");
            }
        }

        if (StringUtils.isNotBlank(split) && StringUtils.isNotBlank(stringBuffer.toString())) {
            primaryKey = stringBuffer.toString().substring(0, stringBuffer.length() - 1);
        }
        return primaryKey;
    }

    /**
     * @param hashSet
     * @author zhangcong
     * @date 14:13 2017/12/14
     * @describe HashSet转List
     */
    public static List hashSetToList(HashSet hashSet) {
        if (CollectionUtils.isEmpty(hashSet)) {
            return null;
        }
        return new ArrayList<>(hashSet);
    }

    /**
     * @param num
     * @author zhangcong
     * @date 10:54 2017/12/27
     * @describe 正数转负数
     */
    public static float unAbs(float num) {
        return (num > 0) ? -num : num;
    }

    /**
     * @param word
     * @author HJQ
     * @date 11:01 2018/2/6
     * @description 分割字符串成List
     */
    public static List<String> splitToList(String word) {
        String str = ",";
        if (StringUtils.isNotEmpty(word)) {
            if (word.contains(str)) {
                return Arrays.asList(word.split(str));
            } else {
                return Arrays.asList(word);
            }
        }
        return Collections.emptyList();
    }

    /**
     * @param classIdList
     * @author HJQ
     * @date 9:23 2018/2/8
     * @description List转String
     */
    public static String listToString(List<String> classIdList) {
        String classId = "";
        if (!CollectionUtils.isEmpty(classIdList)) {
            classId = classIdList.stream().collect(Collectors.joining(","));
        }
        return classId;
    }

}
