package collections;

import org.junit.Test;

import java.util.*;

/**
 * 集合相关特性测试
 * Created by duan2ping on 2017/9/19.
 */
public class TestCollection {

    /**
     * 测试集合线程安全问题
     * ArrayList：线程不安全
     * Vector:    线程安全（底层的方法都使用了synchronized）
     */
    public static void main(final String args[]) {
        // 将可将实现类改成想要测试的对象，eg:ArrayList
        final List arrayList = new ArrayList();
        // 初始化集合
        for (int i = 0; i < 20; i++) {
            arrayList.add(i);
        }
        System.out.println("集合元素："+arrayList.toString());
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                    for (int i = 0; i < arrayList.size(); i++) {
                        try {
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("删除元素：[" + arrayList.remove(i) + "]");
                    }
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < arrayList.size(); i++) {
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("获得元素：[" + arrayList.get(i) + "]");
                }
            }
        },"t2");
        t1.start();
        t2.start();

    }


    /**
     * 测试集合添加元素验证重复依据
     * 无序不重复保证不重复的手段先验证hashCode在验证equals，所以自定义类需要重写(不强制，默认Object的方法)
     * 有序不重复保证不重复的手段优先使用Comparator比较器，否则使用Comparable(强制，必须有Comparator|Comparable)
     */
    @Test
    public void testAdd(){
        /**
         * 不重复集合：自定义对象，需要自己重写hashCode和equals方法
         */
        System.out.println("============无序Set==================");
        Set set = new HashSet();
        set.add(new User("admin","00453"));
        set.add(new User("admin","00453"));
        System.out.println("未重写hashCode和equals");
       show(set);
        Set newset = new HashSet();
        newset.add(new NewUser("newadmin","00453"));
        newset.add(new NewUser("newadmin","00453"));
        System.out.println("重写hashCode和equals");
        System.out.println("============有序Set==================");
        // 既有Comparable和Comparator优先使用比较器
        // 根据方法返回的值判断元素是否存在， 0：存在
        Set<NewUser> tree = new TreeSet<NewUser>(new Comparator<NewUser>() {
            public int compare(NewUser o1, NewUser o2) {
                return o1.id.equals(o2.id) ? 0 : 1;
            }
        });
        tree.add(new NewUser("newadmin","00453"));
        tree.add(new NewUser("newadmin","00453"));
        show(tree);
    }


    /**
     * 测试集合排序
     * 排序方式：①实现Comparable接口重写compareTo方法 ②自定义比较器实现Comparator接口重写compare方法
     * 基本类型的包装类都实现了Comparable类，所以都有默认的排序
     * 自定义类必须实现Comparable接口或者提供Comparator，否则编译不通过
     * sort默认升序
     */
    @Test
    public void testSort(){
        // 基本类型默认排序
        System.out.print("[基本类型默认排序(sort默认升序)]");
        List<Integer> ilist = new ArrayList<Integer>();
        ilist.add(2);ilist.add(1);ilist.add(6);ilist.add(3);
        Collections.sort(ilist);
        show(ilist);
        System.out.print("[基本类型排序(提供Comparator实现降序)]");
        Collections.sort(ilist, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2)>0?-1:1;
            }
        });
        show(ilist);
        System.out.print("[自定义比较器，实现自定义类排序]");
        List<User> ulist = new ArrayList<User>();
        ulist.add(new User("admin","00456"));
        ulist.add(new User("role","00453"));
        ulist.add(new User("user","00451"));
        Collections.sort(ulist, new Comparator<User>() {
            public int compare(User o1, User o2) {
                return o1.id.compareTo(o2.id)>0?-1:1;
            }
        });
        show(ulist);
    }


    /**
     * 测试遍历效率
     * for > iterator > foreach
     */
    @Test
    public void testShowSpeed(){
        System.out.println("-------测试集合遍历效率--------");
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0 ; i <100000 ; i++){
            list.add(i);
        }
        // iterator
        long startIter = System.currentTimeMillis();
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            iter.next();
        }
        long endIter = System.currentTimeMillis();
        System.out.println("iterator耗时："+(endIter-startIter)+"ms");

        // for
        long startFor = System.currentTimeMillis();
        for(int i = 0 ; i < list.size() ; i ++){
            //
        }
        long endFor = System.currentTimeMillis();
        System.out.println("for耗时："+(endFor-startFor)+"ms");

        // foreach
        long startEach = System.currentTimeMillis();
        for(Integer i : list){
            // System.out.println(i);
        }
        long endEach = System.currentTimeMillis();
        System.out.println("foreach耗时："+(endEach-startEach)+"ms");
    }


    // 遍历的方法
    public static <T extends Collection> void show(T t){
        System.out.println();
        for(Object obj : t){
            System.out.print(obj+",");
        }
        System.out.println();
    }

}

// 未重写hashCode和equals
class User{
    public String name;
    public String id;
    public User(String name,String id){this.name = name;this.id = id;}

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

// 重写hashCode和equals
class NewUser implements Comparable<NewUser>{
    public String name;
    public String id;
    public NewUser(String name,String id){this.name = name;this.id = id;}

    @Override
    public String toString() {
        return "NewUser{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewUser newUser = (NewUser) o;

        if (!name.equals(newUser.name)) return false;
        return id.equals(newUser.id);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    public int compareTo(NewUser o) {
        return 1;
    }
}
