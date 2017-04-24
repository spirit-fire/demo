package com.spirit.demo.jmock;

/**
 * Created by lgx on 2017/4/7.
 */
public class IMathFunImpl implements IMathFun{
    /** */
    public int abs(int value) {
        return Math.abs(value);
    }

    /** {@link #calc(int, int) } Method */
    public int calc(int v1, int v2) {
        return v1+v2;
    }

}
