package com.spirit.demo.readability.service;

import com.spirit.demo.mq.Bootstrap;
import com.spirit.demo.readability.dao.ReadabilityDealWithMcqDao;

/**
 * Created by lgx on 2017/5/21.
 */
public class ReadabilityService implements Bootstrap {

    private ReadabilityDealWithMcqDao readabilityDealWithMcqDao;

    /**
     * mq start
     */
    @Override
    public void deal() {
        this.readabilityDealWithMcqDao.deal();
    }

    public void setReadabilityDealWithMcqDao(ReadabilityDealWithMcqDao readabilityDealWithMcqDao) {
        this.readabilityDealWithMcqDao = readabilityDealWithMcqDao;
    }
}
