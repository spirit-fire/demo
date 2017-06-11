package com.spirit.demo.readability.dao.impl;

import com.basistech.readability.HttpPageReader;
import com.basistech.readability.PageReader;
import com.basistech.readability.Readability;
import com.basistech.readability.TikaCharsetDetector;
import com.spirit.demo.common.Patterns;
import com.spirit.demo.readability.dao.ReadabilityDao;
import com.spirit.demo.readability.model.ReadabilityCallBackModel;
import com.spirit.engine.common.ToolUtils;
import com.spirit.engine.qiniu.Constant.QiniuDealTypeConstants;
import com.spirit.engine.qiniu.service.QiniuUploadService;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by lgx on 2017/5/7.
 */
public class ReadabilityDaoImpl implements ReadabilityDao {

    /** readabiliry */
    private Readability readability = null;

    /** http page reader */
    private PageReader pageReader = null;



    /** init readability */
    private void init(){
        pageReader = new HttpPageReader();
        readability = new Readability();
        pageReader.setCharsetDetector(new TikaCharsetDetector());
        readability.setPageReader(pageReader);
        readability.setReadAllPages(false);
    }

    @Override
    public String getContextFromUrl(String url) throws Exception{
        if(null==readability || null==pageReader){
            readability = null;
            pageReader = null;
            this.init();
        }
        readability.processDocument(url);
        String content = readability.getArticleText();
        return content;
    }

    @Override
    public String getContextFromUrAndSavePicsToQiniu(String url) throws Exception {
        if(null==readability || null==pageReader){
            readability = null;
            pageReader = null;
            this.init();
        }
        readability.processDocument(url);
        String content = readability.getArticleText();
        ArrayList<String> list = Patterns.matchGroup(Patterns.PAGE_IMG_PATTERNS, content);
        for(String info:list){
//            String pid = MD5Utils.md5(info);
            String response = "";
            response = QiniuUploadService.upload(info, QiniuDealTypeConstants.QINIU_URL_TYPE);
//            content.replace(info, pid);
            content = content.replace(info, response);
        }
        return content;
    }

    @Override
    public ReadabilityCallBackModel getCallBackModerlFromUrAndSavePicsToQiniu(String url) throws Exception {
        ReadabilityCallBackModel callBackModel = new ReadabilityCallBackModel();
        if(null==readability || null==pageReader){
            readability = null;
            pageReader = null;
            this.init();
        }
        readability.processDocument(url);
        String content = readability.getArticleText();
        ArrayList<String> list = Patterns.matchGroup(Patterns.PAGE_IMG_PATTERNS, content);
        ArrayList<String> pidList = new ArrayList<String>();
        for(String info:list){
//            String pid = MD5Utils.md5(info);
            String response = "";
            response = QiniuUploadService.upload(info, QiniuDealTypeConstants.QINIU_URL_TYPE);
//            content.replace(info, pid);
            if(ToolUtils.isStringEmpty(response)){
                continue;
            }
            content = content.replace(info, response);
            pidList.add(response);
        }
        callBackModel.setText(URLEncoder.encode(content, "UTF-8"));
        callBackModel.setPids(ToolUtils.implode(pidList,","));
        return callBackModel;
    }
}
