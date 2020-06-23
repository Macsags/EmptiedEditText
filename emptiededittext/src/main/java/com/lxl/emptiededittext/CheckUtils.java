package com.lxl.emptiededittext;

import java.util.regex.Pattern;

/**
 * 校验类
 */

public class CheckUtils {


    private static final String PATTERN_IDCARD = "([0-9]{17}([0-9]|X|x))|([0-9]{15})";

    /**
     * 判断身份证位数或格式的正确性
     *
     * @param idNumber
     * @return
     */

    public static boolean checkIdNumber(String idNumber) {
        return Pattern.matches(PATTERN_IDCARD, idNumber);

    }


}