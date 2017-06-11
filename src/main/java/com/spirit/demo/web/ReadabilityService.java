package com.spirit.demo.web;

import com.spirit.demo.readability.dao.ReadabilityDao;
import com.spirit.demo.readability.dao.impl.ReadabilityDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgx on 2017/5/7.
 */
@Controller
@RequestMapping("/geekbook")
public class ReadabilityService {

    @RequestMapping(value="/readability", method= RequestMethod.GET)
    public List<String> getContext(
            @RequestParam(value="source", required = true) String source,
            @RequestParam(value="url", required = true) String url
    ) throws Exception{
        List<String> list = new ArrayList<String>();
        ReadabilityDao readabilityDao = new ReadabilityDaoImpl();
        String content = readabilityDao.getContextFromUrAndSavePicsToQiniu(url);
//        String content = readabilityDao.getContextFromUrl(url);
        list.add(content);
        return list;
    }

    public static void main(String args[]){
        ReadabilityService service = new ReadabilityService();
        String url = "http://www.cnblogs.com/SKILL0825/p/5971539.html";
        List<String> list = null;
        System.out.println("[s] begin: "+url);
        try{
            list = service.getContext("2148", url);
            System.out.println(list);
        }catch (Exception e){

        }
    }
}
