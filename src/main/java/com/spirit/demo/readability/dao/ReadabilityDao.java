package com.spirit.demo.readability.dao;

import com.spirit.demo.readability.model.ReadabilityCallBackModel;

/**
 * Created by lgx on 2017/5/7.
 */
public interface ReadabilityDao {

    public String getContextFromUrl(String url) throws Exception;

    public String getContextFromUrAndSavePicsToQiniu(String url) throws Exception;

    public ReadabilityCallBackModel getCallBackModerlFromUrAndSavePicsToQiniu(String url) throws Exception;
}
