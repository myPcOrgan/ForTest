package com.xuehai.dto.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ：周黎钢.
 * @date ：Created in 14:24 2018/8/22
 * @description:
 */
@Data
@Accessors(chain = true)
public class Student {

    private ClassInfo classInfo;

    private Integer userId;

    private String userName;
}
