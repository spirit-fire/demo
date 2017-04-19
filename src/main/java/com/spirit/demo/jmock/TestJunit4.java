package com.spirit.demo.jmock;

/**
 * Created by lgx on 2017/4/7.
 */
public class TestJunit4 {

    private IMathFun util = null;

    public TestJunit4(){

    }

    public TestJunit4(IMathFun util){
        this.util = util;
    }

    public int cal(int num){
        return 10 * this.util.abs(num);
    }
}
