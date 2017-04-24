package com.spirit.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgx on 2017/4/24.
 */
@Controller
@RequestMapping("/demo_api")
public class DemoService {

    @RequestMapping(value="/test", method= RequestMethod.GET)
    public List<String> test(
            @RequestParam(value="source", required = true) String source
    ){
        List<String> list = new ArrayList<String>();
        list.add("Hello world! It's from "+source);
        return list;
    }
}
