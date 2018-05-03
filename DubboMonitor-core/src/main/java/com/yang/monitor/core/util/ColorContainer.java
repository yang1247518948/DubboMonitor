package com.yang.monitor.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 生成颜色的容器
 * @author: yangping
 */
public class ColorContainer
{
    private static int index = 1;
    private static final int COUNT = 18;
    private static Map<Integer, String> colors = new HashMap<Integer, String>();
    private static Map<String, String> apps = new HashMap<String, String>();

    static
    {
        InitialContext(COUNT);
    }

    /** 
     * 初始化颜色容器(前端的图片为'iconxx',xx为00增长的数字)
     * @param count
     */
    public static void InitialContext(int count)
    {
        for (int i = 1; i < count; i++)
        {
            if (i <= 9)
            {
                colors.put(i, "icon0" + i);
            }
            else
            {
                colors.put(i, "icon"+i);
            }
        }
    }

    /** 
     * 通过appName获取颜色
     * @param appName
     * @return 颜色
     */
    public static String getColors(String appName)
    {
        if (apps.containsKey(appName))
        {
            return apps.get(appName);
        }
        String color = colors.get(index++);
        if (index == COUNT)
        {
            index = 1;
        }
        apps.put(appName, color);
        return color;
    }
}
