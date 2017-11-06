package utils;

import java.text.Collator;
import java.util.*;

/**
 * 排序
 * Created by duan2ping on 2017/9/7.
 */
public class Sort {

    /**
     * 中文拼音排序
     * @param depts   集合
     * @return        排序后
     */
    public static List<String> chineseSort(List<String> depts){
        // 中文比较器
        Comparator comChinese = Collator.getInstance(Locale.CHINA);
        Collections.sort(depts,comChinese);
        return depts;
    }

    /**
     * 选择排序
     * @param arr    排序前
     * @return       排序后
     */
    public static Integer[] selectSort(Integer[] arr){
        for(int i = 0 ; i < arr.length -1; i++){
            for(int j = i+1 ; j<arr.length ; j++){
                // arr[j] > arr[i] ：将大的往前移 -- 降序
                // arr[j] < arr[i] ：将下的往前移 -- 升序
                if(arr[j] > arr[i]){
                    arr[j] = arr[i] + arr[j];
                    arr[i] = arr[j] - arr[i];
                    arr[j] = arr[j] - arr[i];
                }
            }
        }
        return arr;
    }

    /**
     * 冒泡排序
     * @param arr   未排序前
     * @return　　　排序后
     */
    public static Integer[] changeSort(Integer[] arr){
        for( int i = 0 ; i < arr.length - 1 ; i ++){
            for( int j = 0 ; j < arr.length - 1 -i; j++){
                // arr[j+1] > arr[i] ：将大的往前移 -- 降序
                // arr[j+1] < arr[i] ：将下的往前移 -- 升序
                if(arr[j+1] < arr[j]){
                    // 交换数（不新增第三变量），方法很多，可以自行设计运算。
                    // ① 运算
                    arr[j+1] = arr[j] + arr[j+1];
                    arr[j] = arr[j+1] - arr[j];
                    arr[j+1] = arr[j+1] - arr[j];
                }
            }
        }
        return arr;
    }
    protected static String a="";
    public static void main(String args[]){
        testChineseSort();

        testSelectSort();

        testChangeSort();
    }

    // 测试中文排序
    public static void testChineseSort(){
        System.out.println("----------测试中文排序start-----------");
        List<String> depts = new ArrayList<String>();
        depts.add("妇产科");
        depts.add("儿科");
        depts.add("神经科");
        depts.add("残疾科");
        System.out.println("排序前："+depts);
        chineseSort(depts);
        System.out.println("排序后："+depts);
        System.out.println("----------测试中文排序end------------");
    }

    // 测试选择排序
    public static void testSelectSort(){
        Integer[] arr = {3,2,5,3,6,3,22,32,35,4,5};
        System.out.println("----------测试选择排序start-----------");
        System.out.println("排序前："+Arrays.toString(arr));
        selectSort(arr);
        System.out.println("排序后："+Arrays.toString(arr));
        System.out.println("----------测试选择排序end-----------");
    }

    // 测试冒泡排序
    public static void testChangeSort(){
        Integer[] arr = {3,2,5,3,6,3,22,32,35,4,5};
        System.out.println("----------测试冒泡排序start-----------");
        System.out.println("排序前："+Arrays.toString(arr));
        changeSort(arr);
        System.out.println("排序后："+Arrays.toString(arr));
        System.out.println("----------测试冒泡排序end-----------");
    }

}
