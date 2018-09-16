//package com.xuehai.impl;
//
//import com.m5173.framework.dataaccess.pagination.GenericPage;
//import com.m5173.framework.shared.exception.BusinessException;
//import com.m5173.mobile.pushMessage.common.manager.IPushMessageEOManager;
//import com.m5173.mobile.pushMessage.common.manager.ISwitchManager;
//import com.m5173.mobile.pushMessage.common.model.PushMessageEO;
//import com.m5173.mobile.pushMessage.common.model.SwitchEO;
//import com.m5173.mobile.pushMessage.common.utils.WeChatKeyHelper;
//import com.m5173.moblie.pushMessage.mgmt.dao.WeChatRedisDao;
//import com.m5173.moblie.pushMessage.mgmt.dao.rdb.IPushMessageEODao;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.util.StringUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by 339912 on 2017/9/1.
// */
//@Component
//public class PushMessageEOManagerImpl implements IPushMessageEOManager {
//    @Autowired
//    private IPushMessageEODao pushMessageEODao;
//    @Autowired
//    private ISwitchManager switchManager;
//    @Autowired
//    WeChatRedisDao weChatRedisDao;
//
//    /*资讯简报调用的添加方法*/
//    @Override
//    public void addMessage(Integer messageType, String messageTitle, String linkUrl, String messageCode) {
//        Map<String, Object> queryMap = new HashMap<>();
//        queryMap.put("is_locked", true);
//        List<PushMessageEO> list = pushMessageEODao.selectByMap(queryMap);
//        if (list == null || list.size() == 0) {
//            Calendar now = Calendar.getInstance();
//            String messageCodeC = "";
//            if(StringUtils.isNotBlank(messageCode)){
//                messageCodeC=messageCode;
//            }else {
//                messageCodeC=now.get(Calendar.YEAR) + ",1";
//            }
//            PushMessageEO pushMessageEO = new PushMessageEO();
//            pushMessageEO.setMessageTitle(messageTitle);
//            pushMessageEO.setMessageCode(messageCodeC);
//            pushMessageEO.setLinkUrl(linkUrl);
//            pushMessageEO.setMessageType(messageType);
//            pushMessageEO.setSuccess(false);
//            pushMessageEO.setSuccessTime(null);
//            SwitchEO switchEO = switchManager.query(1L);
//            pushMessageEO.setAutoSend(switchEO.getOffNo());
//            pushMessageEODao.insert(pushMessageEO);
//            weChatRedisDao.delKey(WeChatKeyHelper.getRedisPushMessage());
//            weChatRedisDao.addMessage(messageCode, linkUrl, messageTitle);
//        } else {
//            PushMessageEO pushMessageEO = list.get(0);
//            pushMessageEO.setMessageTitle(messageTitle);
//            pushMessageEO.setLinkUrl(linkUrl);
//            pushMessageEO.setMessageType(messageType);
//            if(StringUtils.isNotBlank(messageCode)){
//                pushMessageEO.setMessageCode(messageCode);
//            }
//            pushMessageEO.setSuccess(false);
//            pushMessageEO.setSuccessTime(null);
//            pushMessageEODao.update(pushMessageEO);
//            weChatRedisDao.delKey(WeChatKeyHelper.getRedisPushMessage());
//            weChatRedisDao.addMessage(pushMessageEO.getMessageCode(), linkUrl, messageTitle);
//        }
//
//    }
//
//    public void changeAuto() {
//        Map<String, Object> queryMap = new HashMap<>();
//        queryMap.put("is_locked", true);
//        List<PushMessageEO> list = pushMessageEODao.selectByMap(queryMap);
//        if (list.isEmpty()) {
//            throw new BusinessException("数据库没有数据");
//        }
//        PushMessageEO pushMessageEO = list.get(0);
//        Boolean flag = switchManager.update(1L);
//        pushMessageEO.setAutoSend(flag);
//        pushMessageEODao.update(pushMessageEO);
//    }
//
//    /*分页查询*/
//    @Override
//    public GenericPage<PushMessageEO> selectByMap(Map<String, Object> prams, int pageSize, int startIndex, String orderBy) {
//        return pushMessageEODao.selectByMap(prams, pageSize, startIndex, orderBy, true);
//    }
//}
