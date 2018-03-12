package com.hxq.huaxiaoqclient.utils;

/**
 * Created by yuanzhenyun on 2017/7/13.
 */

public class StringUtils {

    /**
     * 一个为空时不相等，两个都为空时相等，都不为空时再比较
     *
     * @param first
     * @param last
     * @return
     */
    public static boolean equals(String first, String last) {

        if (first == null) {
            if (last == null) {
                return true;
            } else {
                return false;
            }
        }

        return first.equals(last);

    }

    /**
     * 为空时或者为大小写的null或长度为1视为空，其它情况不为空
     */
    public static boolean isEmpty(String txt) {


        if (txt == null) {
            return true;
        }

        if (txt.length() == 0) {
            return true;
        }

        if (txt.toLowerCase().equals("null")) {
            return true;
        }

        return false;
    }
}
