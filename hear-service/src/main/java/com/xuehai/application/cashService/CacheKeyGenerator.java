package com.xuehai.application.cashService;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author ：周黎钢.
 * @date ：Created in 16:44 2018/7/24
 * @description: key生成器
 */
public interface CacheKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}

