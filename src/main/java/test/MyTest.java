package test;

import org.junit.Test;

/**
 * 测试
 * Created by duan2ping on 2017/9/21.
 */
public class MyTest implements Runnable {

    private static int static_i;//静态变量

    /**
     * 测试静态变量线程安全问题
     */
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new MyTest(), "线程" + i).start();
        }
    }

    // 多线程任务
    public void run() {
        static_i = 4;
        System.out.println("[" + Thread.currentThread().getName()
                + "]获取static_i 的值:" + static_i);
        static_i = 10;
        System.out.println("[" + Thread.currentThread().getName()
                + "]获取static_i*2的值:" + static_i * 2);
    }

    /**
     * 测试static{},{},构造执行顺序
     */
    @Test
    public void testStaticOrder() {
        new User();
        new User();
    }


    /**
     * 测试什么时候触发初始化阶段(会执行static{})
     * 1）使用new关键字实例化对象
     * 2）读取一个类的静态字段（被fina修饰除外)
     * 3）设置一个类的静态字段（被fina修饰除外)
     * 4）调用一个类的静态方法
     */
    @Test
    public void testString() {

        System.out.println("-----------检测触发初始化阶段----------");
        System.out.println("①：读取一个类的静态字段");
        String a = User.a;
        System.out.println("①：设置一个类的静态字段");
        User.a = "a";
        System.out.println("①：调用一个类的静态方法");
        User.say();
        System.out.println("①：使用new关键字实例化对象");
        new User();
    }

    @Test
    public void test() {
        // Integer x20 = \x21;
        StringBuffer sb = new StringBuffer(60);
        System.out.println(convert("\\u60a8\\u597d"));
    }

    public String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    public static float a;


}
class User extends Person{
    static String a = "aa";
    public User(){
        System.out.println("user 构造");
    }
    static{
        System.out.println("user 静态代码块1");
    }
    {
        System.out.println("user 代码块{1}");
    }
    {
        System.out.println("user 代码块{2}");
    }
    static{
        System.out.println("uesr 静态代码块2");
    }
    public static void say(){}
}
class Person{
    public Person(){
        System.out.println("person　构造");
    }
    static{
        System.out.println("person 静态代码块1");
    }
    {
        System.out.println("person 代码块1");
    }
    {
        System.out.println("person 代码块2");
    }
    static{
        System.out.println("person 静态代码块2");
    }
}
