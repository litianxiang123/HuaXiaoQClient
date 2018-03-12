package com.hxq.huaxiaoqclient.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import org.senydevpkg.utils.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountUtils {

    /**
     * 金额为分的格式
     */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    /**
     * 将分为单位的转换为元并返回金额格式的字符串 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(Long amount) throws Exception {
        if (!amount.toString().matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }

        int flag = 0;
        String amString = amount.toString();
        if (amString.charAt(0) == '-') {
            flag = 1;
            amString = amString.substring(1);
        }
        StringBuffer result = new StringBuffer();
        if (amString.length() == 1) {
            result.append("0.0").append(amString);
        } else if (amString.length() == 2) {
            result.append("0.").append(amString);
        } else {
            String intString = amString.substring(0, amString.length() - 2);
            for (int i = 1; i <= intString.length(); i++) {
                if ((i - 1) % 3 == 0 && i != 1) {
                    result.append(",");
                }
                result.append(intString.substring(intString.length() - i, intString.length() - i + 1));
            }
            result.reverse().append(".").append(amString.substring(amString.length() - 2));
        }
        if (flag == 1) {
            return "-" + result.toString();
        } else {
            return result.toString();
        }
    }

    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(String amount) throws Exception {
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static String changeY2F(Long amount) {
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();
    }

    /**
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
     *
     * @param amount
     * @return
     */
    public static String changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }

    public static void main(String[] args) {
        try {
            System.out.println("结果：" + changeF2Y("-000a00"));
        } catch (Exception e) {
            System.out.println("----------->>>" + e.getMessage());
//          return e.getErrorCode();  
        }
//      System.out.println("结果："+changeY2F("1.00000000001E10"));  

        System.out.println(AmountUtils.changeY2F("1000000000000000"));
        System.out.println(Long.parseLong(AmountUtils.changeY2F("1000000000000000")));
        System.out.println(Integer.parseInt(AmountUtils.changeY2F("10000000")));
        System.out.println(Integer.MIN_VALUE);
        long a = 0;
        System.out.println(a);

    }

    /**
     * 金钱乘法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.add(b2).toString();
    }

    /**
     * 金钱乘法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String multiply(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.multiply(b2).toString();
    }

    /**
     * 乘法
     *
     * @param v1    乘数
     * @param v2    被乘数
     * @param scale 小数点保留位数
     * @return
     */
    public static String multiplyWithScale(String v1, String v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal result = b1.multiply(b2);
        result = result.setScale(scale);
        return result.toString();
    }

    /**
     * 金钱除法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String divide(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.divide(b2).toString();
    }

    /**
     * 如果除不断，产生无限循环小数，那么就会抛出异常，解决方法就是对小数点后的位数做限制
     *
     * @param v1
     * @param v2 小数模式 ，DOWN，表示直接不做四舍五入，参考{@link RoundingMode}
     * @return
     */
    public static String divideWithRoundingDown(String v1, String v2) {
        return divideWithRoundingMode(v1, v2, RoundingMode.DOWN);
    }

    /**
     * 选择小数部分处理方式
     */
    public static String divideWithRoundingMode(String v1, String v2,
                                                RoundingMode roundingMode) {
        return divideWithRoundingModeAndScale(v1, v2, RoundingMode.DOWN, 0);
    }

    /**
     * 选择小数点后部分处理方式，以及保留几位小数
     *
     * @param v1           除数
     * @param v2           被除数
     * @param roundingMode 小数处理模式
     * @param scale        保留几位
     * @return
     */
    public static String divideWithRoundingModeAndScale(String v1, String v2,
                                                        RoundingMode roundingMode, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, roundingMode).toString();
    }

    /**
     * 金钱减法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String subtract(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.subtract(b2).toString();
    }

    //设置需要保存的小数点的位置
    public static void setPoint(final EditText editText, final int point) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > point) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + point + 1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //传递毫秒
    public static String rangeDay(long time) {
        long nowTime = System.currentTimeMillis();
        long range = (time - nowTime) / 1000;
        if (range > 0) {
            if (range / (24 * 3600) > 1) {
                return range / (24 * 3600) + 1 + "天";
            } else if (range / 3600 > 1) {
                return range / 3600 + 1 + "小时";
            } else if (range / 60 > 1) {
                return range / 60 + 1 + "分";
            } else {

                return null;

            }
            // return (int) (range / (24 * 3600 * 1000)) + 1;
        } else {
            return null;
        }
    }

    /**
     * 后去业务时间
     *
     * @param circleTime 单位秒
     * @return
     */
    public static String getCurrentTimeStr(long circleTime) {
        long temp = circleTime;
        long millis = System.currentTimeMillis();
        circleTime = millis
                - (circleTime * 1000);
        if (circleTime > 0) {
            long showTime = circleTime / (3600 * 24 * 1000);
            long showTimeHour = circleTime / (3600 * 1000);
            long showTimeMins = circleTime / (60 * 1000);
            if (showTime == 0) {
                if (showTimeHour != 0) {
                    return circleTime / (3600 * 1000) + "小时前";
                } else if (showTimeMins != 0) {
                    return showTimeMins + "分钟前";

                } else {
                    return circleTime / 1000 + "秒前";
                }
            } else if (showTime == 1) {
                return "昨天";
            } else if (showTime == 2) {
                return "前天";
            } else {
                return showTime +"天前";
//                return DateUtils.formatDateAndTime(temp * 1000);
            }
        }
        return "刚刚";
    }

    /**
     * 后去业务时间
     *
     * @param circleTime 单位毫秒
     * @return
     */
    public static String getCurrentTimeStr1(long circleTime) {
        long temp = circleTime;
        long millis = System.currentTimeMillis();
        circleTime = millis
                - (circleTime);
        if (circleTime > 0) {
            long showTime = circleTime / (3600 * 24 * 1000);
            long showTimeHour = circleTime / (3600 * 1000);
            long showTimeMins = circleTime / (60 * 1000);
            if (showTime == 0) {
                if (showTimeHour != 0) {
                    return circleTime / (3600 * 1000) + "小时前";
                } else if (showTimeMins != 0) {
                    return showTimeMins + "分钟前";

                } else {
                    return circleTime / 1000 + "秒前";
                }
            } else if (showTime == 1) {
                return "昨天";
            } else if (showTime == 2) {
                return "前天";
            } else {
                return DateUtils.formatDateAndTime(temp);
            }
        }
        return "刚刚";
    }
}  