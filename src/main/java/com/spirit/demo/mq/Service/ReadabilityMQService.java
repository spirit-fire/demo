package com.spirit.demo.mq.Service;

import com.spirit.demo.mq.Bootstrap;
import com.spirit.demo.readability.dao.ReadabilityDealWithMcqDao;

/**
 * Created by lgx on 2017/5/14.
 */
public class ReadabilityMQService implements Bootstrap {

    /** readability dao */
    private ReadabilityDealWithMcqDao readabilityDealWithMcqDao;

    /**
     * mq start
     */
    @Override
    public void deal() {
        this.readabilityDealWithMcqDao.deal();
    }

    public void setReadabilityDealWithMcqDao(ReadabilityDealWithMcqDao readabilityDealWithMcqDao){this.readabilityDealWithMcqDao = readabilityDealWithMcqDao;}
}
