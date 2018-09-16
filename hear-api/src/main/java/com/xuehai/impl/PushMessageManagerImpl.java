//package com.xuehai.impl;
//
//import com.m5173.framework.common.AbstractBaseService;
//import com.m5173.framework.logging.enums.LogCategory;
//import com.m5173.framework.shared.exception.BusinessException;
//import com.m5173.mobile.pushMessage.common.manager.ISwitchManager;
//import com.m5173.mobile.pushMessage.common.manager.PushMessageManager;
//import com.m5173.mobile.pushMessage.common.model.PushMessageEO;
//import com.m5173.mobile.pushMessage.common.utils.MapToPramsUtil;
//import com.m5173.mobile.pushMessage.common.utils.WeChatKeyHelper;
//import com.m5173.mobile.pushMessage.common.utils.WebUtil;
//import com.m5173.moblie.pushMessage.mgmt.dao.WeChatRedisDao;
//import com.m5173.moblie.pushMessage.mgmt.dao.rdb.IPushMessageEODao;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONNull;
//import net.sf.json.JSONObject;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.time.DateFormatUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//
//import java.util.*;
//import java.util.concurrent.*;
//
//@Controller
//public class PushMessageManagerImpl extends AbstractBaseService implements PushMessageManager {
//    @Value("${wechat.appid}")
//    private String appid = "";
//    @Value("${wechat.secret}")
//    private String secret = "";
//    @Value("${wechat.grant_type}")
//    private String grant_type = "";
//    @Value("${wechat.baseUrl}")
//    private String baseUrl = "";
//    @Value("${wechat.templateId}")
//    private String templateId = "";
//    @Autowired
//    private WeChatRedisDao weChatRedisDao;
//    @Autowired
//    private IPushMessageEODao pushMessageEODao;
//    @Autowired
//    private ISwitchManager switchManager;
//    private int count = 0;
//
//    //获取accessToken并存入redis
//    @Override
//    public String getAccessToken() {
//        //获取accessToken并存入redis
//        String accessToken = null;
//        Map params = new LinkedHashMap();
//        params.put("grant_type", grant_type);
//        params.put("appid", appid);
//        params.put("secret", secret);
//        String url = MapToPramsUtil.mapToPrams(baseUrl + "token?", params, "&");
//        String json = WebUtil.urlLink(url, "get", "");
//        JSONObject jsonObject = JSONObject.fromObject(json);
//        if (jsonObject.get("access_token") != null) {
//            accessToken = jsonObject.getString("access_token");
//            weChatRedisDao.addAccessToken(accessToken);
//        }
//        if (jsonObject.get("errcode") != null && jsonObject.getString("errcode").equals("40164")) {
//            throw new BusinessException("IP地址无效");
//        }
//        logger.info(LogCategory.SYSTEM, "微信接口调用AccessToken:" + json);
//        return accessToken;
//    }
//
//    //获取用户ID列表并存入redis
//    @Override
//    public List getUserIdsList() {
//        List<String> userList = new LinkedList<>();
//        String accessToken = weChatRedisDao.getAccessToken();
//        String nextOpenId = "";
//        Integer total = 0, count = 0;
//        Map params = new LinkedHashMap();
//        params.put("access_token", accessToken);
//        //循环调用微信接口返回用户列表，微信每次返回最大数量为1万条
//        while (total > count + 1 || total == 0) {//微信公众号实际返回total比count大1
//            if (StringUtils.isNotBlank(nextOpenId)) {
//                params.put("next_openid", nextOpenId);
//            }
//            String url = MapToPramsUtil.mapToPrams(baseUrl + "user/get?", params, "&");
//            String json = WebUtil.urlLink(url, "get", "");
//            JSONObject jsonObject = JSONObject.fromObject(json);
//            System.out.println(json);
//            if (jsonObject.get("errcode") != null) {
//                logger.info(LogCategory.SYSTEM, "微信获取用户ID列表报错:" + json);
//                throw new BusinessException("微信获取用户ID列表报错,错误码" + jsonObject.getString("errcode"));
//            }
//            //这里不进行非空判断，任意为空为无效数据直接让它报错
//            total = Integer.valueOf(jsonObject.getString("total"));
//            count += Integer.valueOf(jsonObject.getString("count"));
//            nextOpenId = jsonObject.getString("next_openid");
//            String data = jsonObject.getString("data");
//            String openids = JSONObject.fromObject(data).getString("openid");
//            JSONArray array = JSONArray.fromObject(openids);
//            userList.addAll(JSONArray.toList(array));
//        }
//        weChatRedisDao.addUserOpenIds(userList);
//        logger.info(LogCategory.SYSTEM, "微信接口获取用户列表总数为:" + count);
//        return userList;
//    }
//
//    //获取单人用户信息
//
//    @Override
//    public String getUserInfo(String openId, String accessToken) {
//        //调用微信接口根据openid返回用户的完整信息
//        Map params = new LinkedHashMap();
//        params.put("access_token", accessToken);
//        params.put("openid", openId);
//        params.put("lang", "zh_CN");
//        String url = MapToPramsUtil.mapToPrams(baseUrl + "user/info?", params, "&");
//        String json = WebUtil.urlLink(url, "get", "");
//        logger.info(LogCategory.SYSTEM, "微信获取用户信息返回信息:" + json);
//        return json;
//    }
//
////    public void testGetAll() {
////        List<String> ids = weChatRedisDao.getUserOpenIds();
////        long startTime = System.currentTimeMillis();
////        for (String id : ids) {
////            getUserInfo(id);
////        }
////        long endTime = System.currentTimeMillis();
////        System.out.println("耗时：" + (endTime - startTime));
////    }
//
//    //批量获取用户信息，每次最多100条
//    @Override
//    public String getUserInfos(String openIdsJson) {
//        //调用微信接口根据openid返回用户的完整信息
//        String accessToken = weChatRedisDao.getAccessToken();
//        Map params = new LinkedHashMap();
//        params.put("access_token", accessToken);
//        params.put("lang", "zh_CN");
//        String url = MapToPramsUtil.mapToPrams(baseUrl + "user/info/batchget?", params, "&");
//        String json = WebUtil.urlLink(url, "post", openIdsJson);
//        JSONObject jsonObject = JSONObject.fromObject(json);
//        if (jsonObject.get("errcode") != null) {
//            logger.error(LogCategory.SYSTEM, "微信批量获取用户信息报错:" + json);
//        }
//        return json;
//    }
//
//    //从微信公众号更新用户信息到redis
//    @Override
//    public void saveUsersToRedis() {
//        getAccessToken();
//        //从redis从取出用户信息map
//        Map<String, String> redisUserInfos = weChatRedisDao.getUserInfos();
//        Set<String> redisUserInfoSet = redisUserInfos.keySet();
//        List<String> redisUserIdsList = new ArrayList<>(redisUserInfoSet);//先查询redis中关注用户ID列表
//        List<String> copyRedisUserIdsList = new ArrayList<>(redisUserIdsList);//复制一份redis中关注用户ID列表
//        List<String> nowUserIdsList = new ArrayList<>(getUserIdsList());//再查询当前关注用户ID列表
//        List<String> copyNowUserIdsList = new ArrayList<>(nowUserIdsList);//复制一份当前关注用户ID列表
//        Map userInfos = new LinkedHashMap();
//        //redis中已有用户id列表则先去除重复的ids，再去查询出不重复的ids
//        if (redisUserIdsList != null && redisUserIdsList.size() > 0 && redisUserInfos != null && redisUserInfos.size() > 0) {
//            for (int i = 0; i < nowUserIdsList.size(); i++) {
//                for (int j = 0; j < redisUserIdsList.size(); j++) {
//                    if (nowUserIdsList.get(i).equals(redisUserIdsList.get(j))) {
//                        copyNowUserIdsList.remove(nowUserIdsList.get(i));
//                        copyRedisUserIdsList.remove(redisUserIdsList.get(j));
//                    }
//                }
//            }
//        }
//        int count = 0;
//        int allCount = 0;
//        //获取新增加关注的用户信息
//        while (copyNowUserIdsList.size() - allCount > 0) {
//            List userList = new LinkedList();
//            int num = 0;
//            if (copyNowUserIdsList.size() - allCount > 100) {
//                num = 100;
//            } else {
//                num = copyNowUserIdsList.size() - allCount;
//            }
//            for (int i = allCount; i < num + allCount; i++) {
//                Map idMap = new LinkedHashMap();
//                idMap.put("openid", copyNowUserIdsList.get(i));
//                idMap.put("lang", "zh_CN");
//                userList.add(idMap);
//            }
//            Map params = new LinkedHashMap();
//            params.put("user_list", userList);
//            JSONObject params1 = JSONObject.fromObject(params);
//            String json = getUserInfos(params1.toString());
//            JSONObject toJson = JSONObject.fromObject(json);
//            if (toJson.get("user_info_list") != null) {
//                JSONArray userInfoList = JSONArray.fromObject(toJson.get("user_info_list"));
//                for (int i = 0; i < userInfoList.size(); i++) {
//                    JSONObject userInfo = JSONObject.fromObject(userInfoList.get(i));
//                    if (!(userInfo.get("nickname") instanceof JSONNull)) {
//                        String nickname = userInfo.getString("nickname");
//                        String openid = userInfo.getString("openid");
//                        userInfos.put(openid, nickname);
//                        count++;
//                    }
//                }
//            }
//            allCount += 100;
//        }
//        System.out.println("增加新关注用户" + count + "个");
//        logger.info(LogCategory.SYSTEM, "增加新关注用户" + count + "个");
//        Iterator<Map.Entry<String, String>> iterator = redisUserInfos.entrySet().iterator();
//        //如果为空则直接添加到redis，如果已经存在则先添加到已有map再添加到redis,为了去掉已经不关注的用户
//        if (redisUserInfos != null && redisUserInfos.size() > 0) {
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> map = iterator.next();
//                for (String idKey : copyRedisUserIdsList) {
//                    if (map.getKey().equals(idKey)) {
//                        iterator.remove();
//                    }
//                }
//            }
//        }
//        if (userInfos != null && userInfos.size() > 0) {
//            redisUserInfos.putAll(userInfos);
//        }
//        weChatRedisDao.addUserInfos(redisUserInfos);
//    }
//
//    //获取所有模板,也用来检测access_token是否过期，过期则更新access_token
//    @Override
//    public String getAllTemplate() {
//        Map params = new LinkedHashMap();
//        params.put("access_token", weChatRedisDao.getAccessToken());
//        String url = MapToPramsUtil.mapToPrams(baseUrl + "template/get_all_private_template?", params, "&");
//        String json = WebUtil.urlLink(url, "get", "");
//        JSONObject jsonObject = JSONObject.fromObject(json);
//        if (jsonObject.get("errcode") != null) {
//            logger.error(LogCategory.SYSTEM, "微信获取所有模板报错:" + json);
//            String errcode = jsonObject.getString("errcode");
//            if (errcode.equals("40001") || errcode.equals("40014")) {
//                getAccessToken();
//            }
//        }
//        return json;
//    }
//
//    //根据模板向个人推送消息
//    @Override
//    public void pushMessage(String userId, String userName, String messageCode, String turnToUrl, String title) {
//        Date date = new Date();
//        String formatDate = DateFormatUtils.format(date, "yyyy年MM月dd日");
//        Map params = new LinkedHashMap();
//        params.put("access_token", weChatRedisDao.getAccessToken());
//        Map postData = new LinkedHashMap();
//        postData.put("touser", userId);
//        postData.put("template_id", templateId);
//        postData.put("url", turnToUrl);
//        Map data = new LinkedHashMap();
//        Map first = new LinkedHashMap();
//        first.put("value", "尊敬的" + userName + "，" + title);
//        first.put("color", "#173177");
//        Map keynote1 = new LinkedHashMap();
//        keynote1.put("value", "5173资讯简报");
//        keynote1.put("color", "#173177");
//        Map keynote2 = new LinkedHashMap();
//        keynote2.put("value", messageCode);
//        keynote2.put("color", "#173177");
//        Map keynote3 = new LinkedHashMap();
//        keynote3.put("value", formatDate);
//        keynote3.put("color", "#173177");
//        Map remark = new LinkedHashMap();
//        remark.put("value", "点击前往>>>");
//        remark.put("color", "#0000FF");//#173177
//        data.put("first", first);
//        data.put("keyword1", keynote1);
//        data.put("keyword2", keynote2);
//        data.put("keyword3", keynote3);
//        data.put("remark", remark);
//        postData.put("data", data);
//        String url = MapToPramsUtil.mapToPrams(baseUrl + "message/template/send?", params, "&");
//        String json = WebUtil.urlLink(url, "post", JSONObject.fromObject(postData).toString());
//        JSONObject jsonObject = JSONObject.fromObject(json);//不需要返回信息，改成异步推送
//        if (jsonObject.get("errcode") != null) {
//            String errcode = jsonObject.getString("errcode");
//            //报异常时记录一下,无数据返回成功时则errcode=0
//            if (errcode.equals("40001") || errcode.equals("40014")) {
//                getAccessToken();
//                secPushMesaage(JSONObject.fromObject(postData).toString());
//            }
//        }
////        return json;
//    }
//
//    //token失效时再次向个人推送消息
//
//    /**
//     * @Author 周黎钢
//     * @date 2017/11/17 11:53
//     */
//    @Override
//    public void secPushMesaage(String data) {
//
//        Map params = new LinkedHashMap();
//        params.put("access_token", weChatRedisDao.getAccessToken());
//        String url = MapToPramsUtil.mapToPrams(baseUrl + "message/template/send?", params, "&");
//        String json = WebUtil.urlLink(url, "post", data);
////        System.out.println(userName);
//        JSONObject jsonObject = JSONObject.fromObject(json);//不需要返回信息，改成异步推送
//        if (jsonObject.get("errcode") != null) {
//            logger.error(LogCategory.SYSTEM, json);
//        }
////        return json;
//    }
//
//    /**
//     * @param page
//     * @return
//     */
//    @Override
//    public int pushMessageToAll(int page) {
//        List<String> message = weChatRedisDao.getMessage();
//        String messageCode = "";
//        String url = "";
//        String title = "";
//        Map<String, Object> queryMap = new HashMap<>();
//        queryMap.put("is_locked", true);
//        List<PushMessageEO> list = pushMessageEODao.selectByMap(queryMap);
//        if (list == null || list.size() == 0) {
//            throw new BusinessException("数据库获取推送消息为空");
//        }
//        PushMessageEO eo = list.get(0);
//        if (message != null && message.size() > 0) {
//            messageCode = message.get(0);
//            url = message.get(1);
//            title = message.get(2);
//        } else {
//            messageCode = eo.getMessageCode();
//            url = eo.getLinkUrl();
//            title = eo.getMessageTitle();
//        }
//        String[] str = messageCode.split(",");
//        if (str[0].equals("")) throw new BusinessException("获取刊号为空");
//        String parseMessageCode = str[0] + "年第" + str[1] + "期";
////        getAllTemplate();//检测accessToken是否过期
//        //调用前先更新用户信息
////        saveUsersToRedis();
//        //从redis中取出数据
//        Map<String, String> users = weChatRedisDao.getUserInfos();
//        long startTime = System.currentTimeMillis();
////        for (Map.Entry<String, String> entry : users.entrySet()) {
////            if (entry.getKey().equals("odzcM0iiHvBOXWmc8Gw3nzIV4-IQ")) {//用于测试群发
////            pushMessage(entry.getKey(), entry.getValue(), parseMessageCode, url, title);
////            System.out.println(++count);
////            }
////        }
//        Set<String> redisUserInfoSet = users.keySet();
//        List<String> redisUserIdsList = new ArrayList<>(redisUserInfoSet);
//        int endNum = (page + 1) * 100;
//        if (endNum > redisUserIdsList.size()) {
//            endNum = redisUserIdsList.size();
//        }
//        for (int i = page * 100; i < endNum; i++) {
////            getUserInfo(redisUserIdsList.get(i));
//            pushMessage(redisUserIdsList.get(i), "5173用户", parseMessageCode, url, title);
//            System.out.println(i);
//        }
//        long endTime = System.currentTimeMillis();
//        System.out.println("第" + page + "页耗时：" + (endTime - startTime));
//        if (redisUserIdsList.size() <= (page + 1) * 100) {
//            //推送成功把刊号期数+1
//            int code = Integer.valueOf(str[1]);
//            Calendar now = Calendar.getInstance();
//            if (!Integer.valueOf(str[0]).equals(now.get(Calendar.YEAR))) {
//                messageCode = now.get(Calendar.YEAR) + ",1";
//            } else {
//                messageCode = str[0] + "," + ++code;
//            }
//            eo.setMessageCode(messageCode);
//            eo.setSuccess(true);
//            eo.setSuccessTime(new Date());
//            pushMessageEODao.update(eo);
//            weChatRedisDao.delKey(WeChatKeyHelper.getRedisPushMessage());
//            weChatRedisDao.addMessage(messageCode, url, title);
//            return -1;
//        }
//        page++;
//        return page;
//    }
//
//
//    //获取所有素材
//    @Override
//    public String batchGetMaterials() {
//        Map params = new LinkedHashMap();
//        params.put("access_token", weChatRedisDao.getAccessToken());
//        String url = MapToPramsUtil.mapToPrams(baseUrl + "material/batchget_material?", params, "&");
//        Map data = new LinkedHashMap();
//        data.put("type", "news");
//        data.put("offset", 0);
//        data.put("count", 20);
//        String json = WebUtil.urlLink(url, "post", JSONObject.fromObject(data).toString());
//        JSONObject jsonObject = JSONObject.fromObject(json);
//        if (jsonObject.get("errcode") != null) {
//            logger.error(LogCategory.SYSTEM, "微信获取所有素材报错:" + json);
//        }
//        return json;
//    }
//
//    @Override
//    public void getMessagePool() throws InterruptedException {
//        List<String> list = getUserIdsList();
//        String accessToken = weChatRedisDao.getAccessToken();
//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
////        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1000);
////        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        long mtime = System.currentTimeMillis();
//        final List<String> allList = new LinkedList();
////        ThreadLocal<List> threadLocal = new ThreadLocal<>();
////        threadLocal.set(allList);
//        for (int i = 0; i < 100; i++) {
//            System.out.println(i);
//            Task task = new Task(i, list, accessToken, allList);
//            executor.submit(task);
//            System.out.println("【总任务数】" + executor.getTaskCount() + "线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
//                    executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount() +
//                    "【总任务数】" + executor.getTaskCount() + "【线程活跃数】" + executor.getActiveCount());
//        }
//        executor.shutdown();
//        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
//        System.out.println("线程池是否关闭：" + executor.isTerminated());
//        System.out.println("最终结果大小：" + allList.size());
//        System.out.println("lastTime:" + (System.currentTimeMillis() - mtime));
//    }
//
//    public void getMessagePool1() throws InterruptedException {
//        List<String> list = getUserIdsList();
//        String accessToken = weChatRedisDao.getAccessToken();
////        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//        //线程数量=（1+线程等待时间/cpu计算时间）*cpu可用数量
//        ThreadPoolExecutor executor1 = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
////        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        long mtime = System.currentTimeMillis();
//        List<String> allList = new LinkedList();
//        ThreadLocal<List> threadLocal = new ThreadLocal<>();
//        threadLocal.set(allList);
//        List<Task> tasks = new LinkedList<>();
//        for (int i = 0; i < 100; i++) {
//            System.out.println(i);
//            Task task = new Task(i, list, accessToken, threadLocal.get());
//            tasks.add(task);
//        }
//        executor1.shutdown();
//        executor1.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
////        List<Future<String>>futures=executor1.invokeAll(tasks);
//        System.out.println("线程池是否关闭：" + executor1.isTerminated());
//        System.out.println("最终结果大小：" + allList.size());
//        System.out.println("lastTime:" + (System.currentTimeMillis() - mtime));
//    }
//
//    class Task implements Callable<String> {
//        private List<String> list;
//        private final List<String> allList;
//        private final int num;
//        private String accessToken;
//
//        public Task(int num, List<String> list, String accessToken, List<String> allList) {
//            this.num = num;
//            this.list = list;
//            this.accessToken = accessToken;
//            this.allList = allList;
//        }
//
//        @Override
//        public String call() {
//            String json = getUserInfo(list.get(num), accessToken);
//            allList.add(json);
//            return "";
//        }
//    }
//
//    class Task1 implements Callable {
//        volatile List<String> list;
//        volatile private int num;
//        private String accessToken;
//        volatile List<String> allList;
//
//        public Task1(int num, List<String> list, String accessToken, List<String> allList) {
//            this.num = num;
//            this.list = list;
//            this.accessToken = accessToken;
//            this.allList = allList;
//        }
//
//        @Override
//        public Object call() throws Exception {
//            if (Thread.interrupted()) {
//                throw new RuntimeException("又饿又累没力气搬石头了");
//            }
//            String json = getUserInfo(list.get(num), accessToken);
//            allList.add(json);
//            return num;
//        }
//    }
//}
