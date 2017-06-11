package com.spirit.demo.common;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lgx on 2017/5/14.
 */
public final class Patterns {

    public static final Pattern PAGE_IMG_PATTERNS = ciPattern("<img[^>]*src=\"(.+?)\"[^>]*>");

    private Patterns(){
        // do not init this class!
    }

    static boolean match(Pattern pattern, String string) {
        return pattern.matcher(string).matches();
    }

    static boolean exists(Pattern pattern, String string) {
        return pattern.matcher(string).find();
    }

    private static Pattern ciPattern(String patternString) {
        return Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
    }

    /**
     * get all matches
     * @param pattern
     * @param string
     * @return
     */
    public static ArrayList<String> matchGroup(Pattern pattern, String string){
        ArrayList<String> list = new ArrayList<String>();
        Matcher m = pattern.matcher(string);
        while(m.find()){
            if(!m.group(1).equals("")){
                list.add(m.group(1));
            }
        }
        return list;
    }
}
