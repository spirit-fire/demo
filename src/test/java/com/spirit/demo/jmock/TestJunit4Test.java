package com.spirit.demo.jmock;

import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by lgx on 2017/4/7.
 */
public class TestJunit4Test extends TestCase {
    private Mockery mockery = new JUnit4Mockery();

    private IMathFun util = null;

    private TestJunit4 test = null;

    @Before
    public void setUp() throws Exception{
        super.setUp();
        this.util = this.mockery.mock(IMathFun.class);
        this.test = new TestJunit4(this.util);
        this.mockery.checking(new Expectations(){
            {
                exactly(1).of(util).abs(-10);will(returnValue(10));
            }
        });
    }

    @After
    public void tearDown() throws Exception{
//        super.tearDown();
    }

    @Test(timeout = 1)
    public void test(){
        assertEquals(100, this.test.cal(-10));
    }

    @Ignore("Ignore this test for the moment, but method name 'testIgnore' won't be ignored")
    @Test
    public void tesIgnore(){
        System.out.println("Hello world, this method should be ignored!");
        assertEquals(100, this.test.cal(-10));
    }
}
