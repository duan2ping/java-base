package utils;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 一些简单的操作和算法
 * Created by duan on 2017/8/29.
 */
public class Utils {


    /**
     * 验证字符串是否是数字【正则】
     * @param str  输入的字符串
     */
    public static boolean isNumeric(String str){
        // 创建表达式
        Pattern pattern = Pattern.compile("[0-9]*");
        // 获得匹配器
        Matcher isNum = pattern.matcher(str);
        // 获得结果
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 验证字符串是否是数字【Character.isDigit()】
     * @param str  输入的字符串
     */
    public static boolean isNumeric2(String str){
        for(int i = 0 ; i < str.length() ; i++){
            // 当前字符
            char current = str.charAt(i);
            // 验证是否是数字
            if(!Character.isDigit(current)){
                return false;
            }
        }
        return true;
    }

    /**
     * 生成指定位数的随机数【String.format(pattern,随机数)
     * pattern:%06d-->将随机数格式化成六位整数，不足六位则补0
     * @param length 随机数长度
     */
    public static String random(String length){
        // 错误信息
        String error = "";
        // 输入验证
        if(length != null && !"".equals(length)) {
            if(isNumeric(length)){
                if(!length.equals("0")) {
                    // 生成随机数规则
                    String init = "%0" + length + "d";
                    // 获得最大随机数
                    String max = "";
                    for (int i = 0; i < Integer.parseInt(length); i++) {
                        max += "9";
                    }
                    return String.format(init, (new Random().nextInt(Integer.parseInt(max))));
                }else{
                    error = "请输入合法的数字@（>0）";
                }
            }else{
                error = "请输入合法的数字@（>0）";
            }
        }else{
            error = "请输入随机数的长度@";
        }
        return error;
    }

    /**
     * 生成随机数
     * @param m   起始范围
     * @param n   终止范围
     * @return    随机数 [m,n) 如果需要[m,n]则加一即可
     */
    public static String random2(Integer m , Integer n){
        StringBuffer result = new StringBuffer();
        if(m == null || n == null){
            result.append("请输入正确的起始值和终止值！");
        }else{
            if(m>n){
                result.append("请输入正确的起始值和终止值！(m<n)");
            }else {
               result.append((int)(Math.random() * (n-m)) + m);
            }
        }
        return result.toString();
    }

    /**
     * 提取每个汉字的首字母
     * @param str     汉字
     * @return String 拼音
     */
    public static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 提取每个汉字的全拼
     * @param str      汉字
     * @return String  拼音
     */
    public static String getPinYin(String str) {
        String result = "";
        for(int i = 0 ; i < str.length() ; i++) {
            char ch = str.charAt(i);
            // 提取汉字全拼，返回所有发音全拼(最后一个数字是音调)，第一个为常用的
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch);
            //System.out.println("["+str.charAt(i)+"]："+ Arrays.toString(pinyinArray));
            result += pinyinArray[0].substring(0, pinyinArray[0].length() - 1);
        }
        return result;
    }


    public static void main(String args[]){
      /*  Random ran = new Random();
        int result = (int)(Math.random()*(20-15))+16;
        for(int i = 0 ; i < 10 ; i ++) {
            System.out.println(random2(-7,-1));
        }*/
       System.out.println(getPinYinHeadChar("梁艳萍"));
       System.out.println(getPinYin("恢复的说法"));
       Sort sort = new Sort();
    }

}
