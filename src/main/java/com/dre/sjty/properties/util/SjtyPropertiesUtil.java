package com.dre.sjty.properties.util;

import java.util.ResourceBundle;

public class SjtyPropertiesUtil {

    private static final ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("application");
    }
    /**
     * 获取最大线程数
     * @return
     */
    public static int getMaxThreadCount() {
        return Integer.parseInt(resourceBundle.getString("maxThreadCount"));
    }

    public static void main(String[] args) {
        System.out.println(getMaxThreadCount());
    }
}
