//package com.xuehai.impl;
//
//import com.m5173.framework.shared.exception.BusinessException;
//import com.m5173.mobile.common.utils.DateUtil;
//import com.m5173.mobile.pushMessage.common.manager.ISwitchManager;
//import com.m5173.mobile.pushMessage.common.model.SwitchEO;
//import com.m5173.moblie.pushMessage.mgmt.dao.WeChatRedisDao;
//import com.m5173.moblie.pushMessage.mgmt.dao.rdb.ISwitchDao;
//import org.apache.commons.lang3.time.DateUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by 339912 on 2017/9/5.
// */
//@Component
//public class SwitchManagerImpl implements ISwitchManager {
//
//    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//    @Autowired
//    private ISwitchDao switchDao;
//    @Autowired
//    private WeChatRedisDao weChatRedisDao;
//
//    public SwitchEO query(Long id){
//        return switchDao.selectById(id);
//    }
//
//    public Boolean update(Long id){
//       SwitchEO switchEO = switchDao.selectById(id);
//       if (null == switchEO){
//           throw new BusinessException("没有对应开关id");
//       }
//       if (switchEO.getOffNo()){
//           weChatRedisDao.lock(switchEO.getJobId(),2, TimeUnit.MINUTES);
//       }else {
//           weChatRedisDao.unlock(switchEO.getJobId());
//       }
//       switchEO.setOffNo(!switchEO.getOffNo());
//       switchDao.update(switchEO);
//       return switchEO.getOffNo();
//    }
//
//}
