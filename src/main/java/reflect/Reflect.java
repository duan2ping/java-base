package reflect;

/**
 * 反射：在程序运行中对任意类，任意方法，任意属性进行操作
 * 也就是反射，使java具有动态性
 * 动态语言：在程序在运行时可以改变其结构
 * Created by duan on 2017/8/21.
 */
public class Reflect {

    /**
     *  三种方式加载Class对象
     *  Class对象:描述类的类,类的对象。
     *  每个类被加载进入内存之后,系统就会为该类生成一个对应的java.lang.Class对象
     *  通过该Class对象就可以访问到JVM中的这个类
     *  Class是类的对象，保存的是类相关的信息（属性，方法...）
     */
    public void getClassObject() throws ClassNotFoundException {
        //第一种方式：forName(类路径)
        Class class1 = Class.forName("Gather");

        //第二种方式：
        //java中每个类型都有class 属性.
        Class class2 = Gather.class;

        //第三种方式：
        //java语言中任何一个java对象都有getClass 方法
        Gather gather = new Gather();
        Class class3 = gather.getClass();
    }

    public static void main(String args[]){

    }

}

class User{
    public  String name;
    private Integer age;
    public User(){
    }
    public User(String name){
        this.name = name;
    }

    public void say(){
        System.out.println("我是："+name+"   年龄："+age);
    }
    public void click(User user){}
}
class Gather{}
