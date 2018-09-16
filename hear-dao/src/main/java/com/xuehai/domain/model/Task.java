package com.xuehai.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author: zhangcong
 * @date: 2018/3/20 10:48
 * @describe: 任务表
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@Document(collection = "xhls_task")
@CompoundIndexes({
        @CompoundIndex(name = "idx_xhls_task", def = "{'bookType':1,'subjects':1,'status':1}")
})
public class Task implements Serializable {

    private static final long serialVersionUID = 3081647037819583207L;

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 书本类型,0表示全部
     */
    private int bookType;

    /**
     * 科目（1：英语；2：语文）
     */
    private int subjects;

    /**
     * 测试遍数
     */
    private int testPass;

    /**
     * 是否可以反复听自已录制的音频(默认2，1:是；2:否)
     */
    private int audioWhetherOpen;

    /**
     * 截止上交时间
     */
    private long endTime;

    /**
     * 测试要求(默认0，1：测试前须完成“逐句练”)
     */
    private int testRequire;

    /**
     * 背诵设置,是否隐藏正文(1:是；2否)
     */
    private int reciteSet;

    /**
     * 创建人ID
     */
    private int createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建人学校ID
     */
    private int schoolId;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 最后修改时间
     */
    private long modifyTime;

    /**
     * 删除标记(1:正常；2:删除)
     */
    private int status;

}
