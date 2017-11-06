package generic;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型，jdk1.5添加的【编译时期】的安全检验技术，为了避免在程序运行时类型转换错误
 * 也可也说是一种高度抽象的设计模式，使得程序设计简洁易复用。而不是片面理解的泛型
 * 就是指定具体的数据类型
 *    一、泛型设计就是将类型进行抽象，提炼出一个模板，这个模板可以接受任何类型的实例化
 *    二、设计时先抽象出一个类型，具体的类型由具体使用时指定，这样就增加了编程的灵活性。
 * Created by duan2ping on 2017/8/19.
 */
public class Generic<T>{


    //泛型符号可以用任意符号,常用
    // E（元素element简写），T（类 型type），K（键key），V（值value）

    /**
     *  ①泛型的类型参数只能是引用类型，不能是基本数据类型。
     *  ②不能将确切的泛型类型用作instanceof比较操作。
     */
    @Test
    public void testType() {
        // ①
        //List<int> list = new ArrayList<int>();
        List<Integer> intege = new ArrayList<Integer>();
        // ②
        //System.out.println(intege instanceof List<Integer>);
    }

    /**
     *  验证泛型是作用于编译时期
     *  说明运行时中类对象未携带泛型信息
     *  编译成功后JVM会对泛型的擦除机制，在运行时JVM是不知道泛型信息
     */
    @Test
    public void testValidate(){
        List<Integer> integer = new ArrayList<Integer>();
        List<String>  string = new ArrayList<String>();
        // 两个类的对象相等，说明编译后未携带泛型
        System.out.println(integer.getClass().equals(string.getClass()));
        // 再次验证泛型是编译时间有效
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(123);
        Class c = a.getClass();
        try{
            Method method = c.getMethod("add",Object.class);
            // 虽然a集合是Integer类型，但是在编译后（运行中）泛型已被擦除
            // 所以可以添加任何类型
            method.invoke(a,"哈哈");
            System.out.println(a);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     *  在编写泛型代码后，java虚拟中会把这些泛型参数类型都擦除，用相应的确定类型来代替，
     *  代替的这一动作叫做类型擦除，而用于替代的类型称为原始类型，在类型擦除过程中，
     *  一般使用第一个限定的类型来替换，若无限定则使用Object
     */
    public void testClear(){
        // 定义
        class Test<T>{
            private T t;
            public void show(T t){}
        }

        // 编译后，用Object替换
        /*
        class Testd{
            private Object t;
            public void show(Object t) { }
        }
        */
        // 定义
        /*
        class Testd<? extends Comparable>{
            private T t;
            public void show(T t){ }
        }
        */
        // 编译后 用限定类型替换
        /*
        class Testd{
            private Comparable t;
            public void show(Comparable t){ }
        }
         */
    }


    /**
     *  泛型通配符 ？
     *  因为使用？不知道是什么类型，所以是只读的
     */
    public <T> void testMatch(){
        // 希望一个集合能接受任何类型集合
        List<Integer> integer = new ArrayList<Integer>();
        List<String> string = new ArrayList<String>();
        List<T> a ;
        // ERROR：a集合只能装T类型的
        // a=integer;
        // ？可以是任何类型的
        List<?> b;
        b = integer;
        b = string;
        //ERROR：b集合使用？，不知道是什么类型，所以不能添加（只读）
        //b.add("1");
    }

    private static String a;
    // 不能使用泛型类型的静态成员
    // 原因：static是属于类成员，所有对象共享属性，
    // 如果Generic<String>,generic.Generic<Integer>则会使b的类型改变
    //private static T b;

    /**
     *  泛型方法
     * @param m   泛型参数
     * @param <M> 指定该方法的泛型，声明一个泛型，可以是任何字符，它只是
     * @return    返回泛型
     */
    public <M> M testGenericMethod(M m){
        /*
         *定义泛型方法时，必须在返回值前边加一个<T>，
         * 来声明这是一个泛型方法，持有一个泛型T，然后才可以使用泛型T
         */
        return m;
    }


    /**
     *  ① 不能创建泛型类数组
     *  ② 不能实例化泛型类型
     */
    public <T> void testError(T t){
        // ①
        //Testd<String>[] a = new Testd<String>[1];
        // ②
        //T t = new T();

    }
}