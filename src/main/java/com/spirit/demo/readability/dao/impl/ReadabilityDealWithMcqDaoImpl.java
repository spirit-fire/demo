package com.spirit.demo.readability.dao.impl;

import com.spirit.demo.readability.dao.ReadabilityDao;
import com.spirit.demo.readability.dao.ReadabilityDealWithMcqDao;
import com.spirit.demo.readability.model.ReadabilityCallBackModel;
import com.spirit.engine.common.HttpRequest;
import com.spirit.engine.common.ToolUtils;
import com.spirit.engine.storage.mc.GkMemcacheClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lgx on 2017/5/14.
 */
public class ReadabilityDealWithMcqDaoImpl implements ReadabilityDealWithMcqDao {

    /** log4j */
    private static final Logger log = Logger.getLogger(ReadabilityDealWithMcqDaoImpl.class.getName());

    /** gk memcache client */
    private GkMemcacheClient gkMemcacheClient;

    /** readability dao */
    private ReadabilityDao readabilityDao;

    /** mcq key */
    private static final String GEEKBOOK_USER_COLLECT_ARTICLES_MCQ = "user_collect_articles";

    /** callback url */
    private static final String GEEKBOOK_CALLBACK_INTERFACE = "http://i.gk.800138.net/article/update";

    /**
     * threadpool deal with url which comes from mcq
     * 1、readability get article text
     * 2、upload pictures to qiniu
     * 3、call front interface
     */
    private static ThreadPoolExecutor geekbook_Readabiliety_threadpool = null;
    static {
        geekbook_Readabiliety_threadpool = new ThreadPoolExecutor(1,5,10, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(50));
    }

    /**
     * default constructor
     */
    public ReadabilityDealWithMcqDaoImpl(){

    }

    /**
     * default init
     */
    private void init(){
        if(null==this.readabilityDao){
            this.readabilityDao = new ReadabilityDaoImpl();
        }
    }

    /**
     * main deal
     */
    @Override
    public void deal() {
        this.init();
        final ReadabilityDao readabilityDao = this.readabilityDao;
        String info = "";
        String url = "";
        String uid = "";
        String aid = "";
        JSONObject root = null;
        log.info("[s] readability start running...");
        boolean flag = true;
        while(flag){
            try{
                info = (String)this.gkMemcacheClient.get(GEEKBOOK_USER_COLLECT_ARTICLES_MCQ);
//                info = "{\"uid\":\"1006299791130764\",\"aid\":\"20025682207836071\",\"url\":\"http://blog.csdn.net/yangbutao/article/details/12971037\"}";
                if(ToolUtils.isStringEmpty(info) || info.equals("null")){
                    Thread.sleep(50);
                    continue;
                }
                log.info("[i] get info from mcq: "+info);
                root = new JSONObject(info);
                url = root.has("url") ? root.getString("url") : "";
                uid = root.has("uid") ? root.getString("uid") : "";
                aid = root.has("aid") ? root.getString("aid") : "";
                if(ToolUtils.isStringEmpty(url) ||
                        ToolUtils.isStringEmpty(uid) ||
                        ToolUtils.isStringEmpty(aid)){
                    continue;
                }
                final String urlTmp = url;
                final String aidTmp = aid;
                final String uidTmp = uid;
                geekbook_Readabiliety_threadpool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ReadabilityCallBackModel callBackModel = readabilityDao.getCallBackModerlFromUrAndSavePicsToQiniu(urlTmp);
                            callBackModel.setAid(aidTmp);
                            callBackModel.setUid(uidTmp);
                            callBackModel.setCtime(System.currentTimeMillis()/1000);
                            String response = HttpRequest.doPost(GEEKBOOK_CALLBACK_INTERFACE, callBackModel.toString());
                            log.info(String.format("[i] callback response: %s, params: %s", response, callBackModel.toString()));
                        } catch (Exception e) {
                            log.warn(String.format("[e] INNER exception occurred! url: %s, err msg:%s", urlTmp, e.getMessage()));
                            return;
                        }
                    }
                });
            }catch(Exception e){
                log.warn(String.format("[e] exception occurred! url: %s, err msg:%s", url, e.getMessage()));
            }
        }
    }

    public static void main(String[] args){
        ReadabilityDealWithMcqDaoImpl dao = new ReadabilityDealWithMcqDaoImpl();
        ReadabilityDao readabilityDao = new ReadabilityDaoImpl();
        dao.setReadabilityDao(readabilityDao);
        dao.deal();
    }

    /**
     * set gkMemcacheClient
     * @param gkMemcacheClient
     */
    public void setGkMemcacheClient(GkMemcacheClient gkMemcacheClient){
        this.gkMemcacheClient = gkMemcacheClient;
    }

    /**
     * set readabilityDao
     * @param readabilityDao
     */
    public void setReadabilityDao(ReadabilityDao readabilityDao){this.readabilityDao = readabilityDao;}
}
