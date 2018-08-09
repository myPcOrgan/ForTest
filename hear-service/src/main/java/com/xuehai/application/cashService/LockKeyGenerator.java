package com.xuehai.application.cashService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author ：周黎钢.
 * @date ：Created in 16:45 2018/7/24
 * @description:
 */
@Service
public class LockKeyGenerator<T> implements CacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);
        final Object[] args = pjp.getArgs();
        final Parameter[] parameters = method.getParameters();
        StringBuilder builder = new StringBuilder();
        // 默认解析方法里面带 CacheParam 注解的属性,如果没有尝试着解析实体对象中的
        for (int i = 0; i < parameters.length; i++) {
            final CacheParam annotation = parameters[i].getAnnotation(CacheParam.class);
            if (annotation == null) {
                continue;
            }
            //如果把下面注释打开，则设置redis的key的时候是把参数值当做key，不打开注释的时候是把对象当做参数
//            if (ifSelfType(parameters[i])) {
//                final Object object = args[i];
//                final Field[] fields = object.getClass().getDeclaredFields();
//                for (Field field : fields) {
//                    final CacheParam annotation1 = field.getAnnotation(CacheParam.class);
//                    if (annotation1 == null) {
//                        continue;
//                    }
//                    field.setAccessible(true);
//                    builder.append(lockAnnotation.delimiter()).append(ReflectionUtils.getField(field, object));
//                }
//            } else {
            builder.append(lockAnnotation.delimiter()).append(args[i]);
//            }
        }
        return lockAnnotation.prefix() + builder.toString();
    }

    /**
     * 判断是否是自定义的类
     *
     * @param parameter
     * @return
     */
    private boolean ifSelfType(Parameter parameter) {
        String className = parameter.getType().getName();
        if (className.contains("com.xuehai") || className.contains("com.xhtech")) {
            return true;
        }
        return false;
    }
}
