package com.haoge.haogepicturebackend.utils;

import java.awt.*;

/**
 * 工具类：计算颜色相似度  
 */  
public class ColorSimilarUtils {  
  
    private ColorSimilarUtils() {  
        // 工具类不需要实例化  
    }  
  
    /**  
     * 计算两个颜色的相似度  
     *  
     * @param color1 第一个颜色  
     * @param color2 第二个颜色  
     * @return 相似度（0到1之间，1为完全相同）  
     */  
    public static double calculateSimilarity(Color color1, Color color2) {
        int r1 = color1.getRed();  
        int g1 = color1.getGreen();  
        int b1 = color1.getBlue();  
  
        int r2 = color2.getRed();  
        int g2 = color2.getGreen();  
        int b2 = color2.getBlue();  
  
        // 计算欧氏距离  
        double distance = Math.sqrt(Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2));  
  
        // 计算相似度  
        return 1 - distance / Math.sqrt(3 * Math.pow(255, 2));  
    }  
  
    /**  
     * 根据十六进制颜色代码计算相似度  
     *  
     * @param hexColor1 第一个颜色的十六进制代码（如 0xFF0000）  
     * @param hexColor2 第二个颜色的十六进制代码（如 0xFE0101）  
     * @return 相似度（0到1之间，1为完全相同）  
     */  
    public static double calculateSimilarity(String hexColor1, String hexColor2) {  
        Color color1 = Color.decode(hexColor1);  
        Color color2 = Color.decode(hexColor2);  
        return calculateSimilarity(color1, color2);  
    }


    public static String getStandardColor(String hexColor) {
        //将0x080f0  转换为 0x0800f0
        if(hexColor.length() == 7) {
            hexColor = hexColor.substring(0, 4) + "0" + hexColor.substring(4);
        }
        return hexColor;
    }
  
    // 示例代码  
    public static void main(String[] args) {
        System.out.println("getStandardColor(\"#080f0\") = " + getStandardColor("0x080f0"));
    }  
}

