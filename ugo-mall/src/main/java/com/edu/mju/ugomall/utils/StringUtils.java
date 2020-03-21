package com.edu.mju.ugomall.utils;

/**
 * @author 67072
 */
public class StringUtils {
    public boolean isEmpty(String obj){
        boolean flag = false;
        if(obj == null || ("").equals(obj)){
            flag = true;
        }
        return flag;
    }
}
