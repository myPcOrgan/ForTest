package com.xuehai.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhangcong
 * @date: 2018/3/22 14:22
 * @describe:常量类
 */
@Slf4j
public class ConstantUtil {

    /**
     * 成功
     */
    public static final long SUCCESS = 2000;

    /**
     * 失败
     */
    public static final long FAILED = 1000;


    /**
     * 学生
     */
    public static final String STUDENT = "student";

    /**
     * 老师
     */
    public static final String TEACHER = "teacher";

    /**
     * 行政班
     */
    public static final int CLAZZ = 0;

    /**
     * 客户端包名
     */
    public interface Application {

        /**
         * 服务端应用编号
         */
        String APP_ID = "SA105005";

        /**
         * 教师端包名，获取书架标识
         */
        String TEC_PACKAGENAME = "com.xh.alstch";

        /**
         * 学生包名
         */
        String STU_PACKAGENAME = "com.xh.alsstu";
    }

    /**
     * 科目
     */
    public interface Subject {
        /**
         * 科目：英语
         */
        int SUBJECTS_ENGLISH = 1;


        /**
         * 科目：语文
         */
        int SUBJECTS_CHINESE = 2;
    }

    /**
     * 书桌请求方式
     */
    public interface DeskRequestMethod {
        /**
         * 老师端请求我的书桌
         */
        int TEACHER_DESK = 1;

        /**
         * 学生端请求我的书桌
         */
        int STUDENT_DESK = 2;
    }

    /**
     * 优展和重做
     */
    public interface ExcellentShowState {
        /**
         * 优展和重做的默认值
         */
        int DEFAULT = 0;

        /**
         * 表示优展或者重做状态
         */
        int EXCELLENT_OR_REDO = 1;

        /**
         * 重做
         */
        int REDO = 2;
    }

    /**
     * 自练
     */
    public static final int SET_TYPE_PRACTICE = 1;

    /**
     * 任务类型
     */
    public static final int SET_TYPE_TASK = 2;

    /**
     * 书本权限
     */
    public static final int SET_TYPE_BOOK = 2;

    /**
     * 标记
     */
    public interface Flag {
        /**
         * 删除标记：正常
         */
        int FLAG_NORMAL = 1;

        /**
         * 删除标记：删除
         */
        int FLAG_DELETE = 2;

        /**
         * 已提交
         */
        int SUBMIT_STATE_YES = 1;

        /**
         * 未提交
         */
        int SUBMIT_STATE_NO = 2;
    }


    /**
     * 书本分类
     */
    public interface COLUMN {
        /**
         * 课内
         */
        int CATALOG_INCLASS = 1000;

        /**
         * 课内
         */
        int CATALOG_OUTSIDECLASS = 1200;

        /**
         * 听说考试
         */
        int CATALOG_HEARDTEST = 1300;
    }

    /**
     * 每页最大条目数
     */
    public static final int MAX_SIZE = 20;

    /**
     * Redis 前缀
     */
    public interface RedisPrefix {
        /**
         * 听说redis key前缀，所有存进Redis中的key都会带此前缀
         */
        String REDIS_PREFIX = "HEAR";

        /**
         * redis key分隔符
         */
        String REDIS_SPLIT = "_";

        /**
         * 分布式锁的KEY
         **/
        String LOCK_KEY = "HEAR_LOCK_KEY";
    }

    /**
     * 书架相关
     */
    public interface BookShelf {

        /**
         * redis 年级前缀
         */
        String REDIS_DICTIONARY_GRADE = "GRADE";

        /**
         * 习题库字典，年级
         */
        String DIC_PARAM_TYPE_GRADE = "grade";


        /**
         * redis 教材版本前缀
         */
        String REDIS_TEXTBOOKVERSION = "TEXTBOOKVERSION";

        /**
         * 习题库字典，教材版本
         */
        String DIC_PARAMTYPE_TEXTBOOKVERSION = "textBookVersion";
    }

    /**
     * 题型分类
     */
    public interface QuestionType {

        /**
         * 口语
         */
        int SYSTEM_SPEAKING = 13;

        /**
         * 口语题
         */
        int USER_QUESTION_SPEAKING = 1301;

        /**
         * 口语朗读
         */
        int USER_QUESTION_READING = 1302;


        /**
         * 听力
         */
        int SYSTEM_HEARING = 14;

        /**
         * 听小对话答题
         */
        int USER_QUESTION_SHORTCONVERSINE = 1401;

        /**
         * 听对话选图片
         */
        int USER_QUESTION_CHOOSEIMAGES = 1402;

        /**
         * 听长对话答题
         */
        int USER_QUESTION_LONGCONVERSINE = 1403;

        /**
         * 听短文答题
         */
        int USER_QUESTION_SHORT_ANSWER = 1404;

        /**
         * 听录音完成表格
         */
        int USER_QUESTION_RECORDING_COMPLETION_FORM = 1405;


        /**
         * 朗读
         */
        int SYSTEM_READING = 15;

        /**
         * 朗读题
         */
        int USER_QUESTION_READ_ALOUD = 1501;

        /**
         * 话题表述
         */
        int USER_QUESTION_DESCRIBE = 1502;

        /**
         * 朗读题
         */
        int USER_QUESTION_SAY = 1503;


        /**
         * 情景回答
         */
        int SYSTEM_SCENE_ANSWER = 16;

        /**
         * 情景回答
         */
        int USER_QUESTION_SCENE_ANSWER = 1601;


        /**
         * 对话或独白
         */
        int SYSTEM_DIALOGUE_MONOLOGUE = 17;


        /**
         * 听对话或独白答题
         */
        int USER_QUESTION_DIALOGUE_MONOLOGUE = 1701;


        /**
         * 情景对话
         */
        int SYSTEM_SITUATIONAL_DIALOGUE = 18;

        /**
         * 情景对话
         */
        int USER_QUESTION_SITUATIONAL_DIALOGUE = 18;

        /**
         * 材料
         */
        int SYSTEM_MATERIAL = 19;

        /**
         * 音频
         */
        int USER_QUESTION_AUDIO = 1901;
    }

    /**
     * 模板
     */
    public interface Template {
        /**
         * 模板只读为系统添加，不可编辑、删除
         */
        int TEMPLATE_READONLY_TRUE = 1;

        /**
         * 模板非只读为用户添加，可编辑、删除
         */
        int TEMPLATE_READONLY_FALSE = 2;
    }

    /**
     * 每本书每日自练次数
     */
    public static int PRACTICE_SETTING_BOOK = 6;


}
