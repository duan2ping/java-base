package test;

import org.junit.Test;

/**
 * Created by Administrator on 2017/9/22.
 */
public class StringTest {

    /**
     * 测试equals和==
     * ==：比较的是引用的内存地址
     * equals：比较的是字符串的字面量
     */
    @Test
    public void testEqual(){
        String a = "a";
        String b = new String("a");
        System.out.println("a==b |"+(a==b));
        System.out.println("a.equals(b) | "+a.equals(b));
    }

    /**
     * 测试String、StringBuffer、StringBuilder效率
     * StringBulder>StringBuffer>String
     */
    @Test
    public void testEfficiency(){
        int count = 20000;
       // ---------测试String效率-------------");
        long startString = System.currentTimeMillis();
        String str = "a";
        for(int i = 0 ; i < count ; i++){
            str+=i;
        }
        long endString = System.currentTimeMillis();

       // --------测试StringBuffer效率---------");

        long startStringBuffer = System.currentTimeMillis();
        StringBuffer buf = new StringBuffer("a");
        for(int i = 0 ; i < count ; i++){
            buf.append(i);
        }
        long endStringBuffer = System.currentTimeMillis();

        // --------测试StringBuilder效率---------");
        long startStringBuilder = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("a");
        for(int i = 0 ; i < count ; i++){
            sb.append(i);
        }
        long endStringBuilder = System.currentTimeMillis();

        long stringResult = endString - startString;
        long stringBufferResult = endStringBuffer - startStringBuffer;
        long stringBuilderResult = endStringBuilder - startStringBuilder;
        System.out.println("===========test result=================");
        System.out.println("String\tStringBuffer\tStringBuilder");
        System.out.println(stringResult+"ms\t\t\t"+stringBufferResult+"ms\t\t\t"+stringBuilderResult+"ms");
    }
    public static void main(String args[]) throws InterruptedException {

        System.out.println("线程开始....");
        Runnable r = new Runnable() {
            public void run() {
                for(int i = 0 ; i < 100 ; i++){
                    System.out.println("~~~~~"+i+"~~~~~");
                }
            }
        };

        Thread a = new Thread(r,"a");
        a.start();
        a.join();
        Runnable w = new Runnable() {
            public void run() {
                for(int i = 0 ; i < 100 ; i++){
                    System.out.println("["+i+"]");
                }
            }
        };

        Thread e = new Thread(w,"b");
        e.start();
        for(int i = 0 ; i < 100 ; i++){
            System.out.println("【"+i+"】");
        }

       // System.out.println("线程结束。。。。。");
        new StringTest().test(100);
    }


    public void test(long a){
        System.out.println("a:"+a);
    }
}
