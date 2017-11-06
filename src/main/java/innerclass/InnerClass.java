package innerclass;

import shiro.Login;

/**
 * 内部类：顾名思义，类中的类，多用于事件监听
 * 有以下四种方式 [除了静态内部类，其他的类都会生成一个：外部类名$内部类名.class]
 * ①：成员内部类
 * ②：局部内部类
 * ③：匿名内部类
 * ④：静态内部类
 * 内部类可以解决java实现多继承问题
 * 其实使用内部类最大的优点就在于它能够非常好的解决多重继承的问题，但是如果我们不需要解决多重继承问题
 * 那么我们自然可以使用其他的编码方式，但是使用内部类还能够为我们带来如下特性（摘自《Think in java》）：
 * 1、内部类可以用多个实例，每个实例都有自己的状态信息，并且与其他外围对象的信息相互独立。
 * 2、在单个外围类中，可以让多个内部类以不同的方式实现同一个接口，或者继承同一个类。
 * 3、创建内部类对象的时刻并不依赖于外围类对象的创建。
 * 4、内部类并没有令人迷惑的“is-a”关系，他就是一个独立的实体。
 * 5、内部类提供了更好的封装，除了该外围类，其他类都不能访问
 * Created by duan2ping on 2017/9/4.
 */
public class InnerClass extends Login{
    /**
     * ①：成员内部类
     * 范围：类中方法外
     * 特性：可以无限制的访问外围类的所有成员属性和方法(包含private)
     * 第一：成员内部类中不能存在任何static的变量和方法
     * 第二：成员内部类是依附于外围类的，所以只有先创建了外围类才能够创建内部类
     *
     */
     class Inner {
            public void sya(){
                //System.out.println(i);
            }
        }

    public void testLocal(){

        /**
         * ②：局部内部类
         * 范围：方法内或作用域内(语句块中)
         * 特性：可以无限制的访问外围类的所有成员属性和方法(包含private)
         * * 第一：成员局部类中不能存在任何static的变量和方法
         */
        class Local{}
    }


    public AnonymousClass anonymous(final int num){
        /**
         * ③：匿名内部类
         * 范围：一个作用域中
         * 特性
         * 1、匿名内部类是没有访问修饰符的。
         * 2、new 匿名内部类，这个类首先是要存在的。如果我们将那个InnerClass接口注释掉，就会出现编译出错。
         * 3、注意getInnerClass()方法的形参，第一个形参是用final修饰的，而第二个却没有。
         *    同时我们也发现第二个形参在匿名内部类中没有使用过，所以当所在方法的形参需要被匿名内部类使用，那么这个形参就必须为final。
         * 4、匿名内部类是没有构造方法的。因为它连名字都没有何来构造方法。
         */
        return new AnonymousClass(){
            int number = num + 3;
            public int getNumber(){
                return number;
            }
        };        /* 注意：分号不能省 */
    }

    /**
     * 四：静态内部类
     * 范围：类中，方法外
     * 特性：
     *  1、它的创建是不需要依赖于外围类的。
     *  2、它不能使用任何外围类的非static成员变量和方法。
     */
    static class StaticClass{}

    public static void main(String args[]){
        // 创建内部类对象
        InnerClass innerClass = new InnerClass();
        InnerClass.Inner inner = innerClass.new Inner();
    }
}
interface AnonymousClass {
    int getNumber();
}


