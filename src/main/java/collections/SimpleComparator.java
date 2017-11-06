package collections;

import java.util.Comparator;

/**
 * 用来定义比较对象、集合、数组等对象类型
 * 方式一：Comparator比较器
 * ①：自定义一个类实现Comparator接口
 * ②：重写compare()方法
 * Created by duan2ping on 2017/9/6.
 */
public class SimpleComparator implements Comparator<Integer> {

    /**
     * 用户自定义比较规则
     * @param o1   元素1
     * @param o2   元素2
     * @return     元素1和元素2的大小
     * 大于0： o1 > o2
     * 等于0:  o1 = o2
     * 小于0：o1 < o2
     */
    public int compare(Integer o1, Integer o2) {
     /*   if(o1 > o2){
            return -1;
        }else if(o1 < o2){
            return 1;
        }else{
            return 0;
        }*/
     return o1.compareTo(o2)>0 ? -1 : 1;
    }
}

/**
 * 实现方式二：Comparable比较器
 * ①：将需要排序的类实现Comparble接口
 * ②：重写compareTo方法
 */
class Student implements Comparable<Student>{

    private String name;
    private Integer age;
    public Student(String name,Integer age){
        this.name = name;
        this.age = age;
    }
    /**
     * 自定义排序规则
     * @param target   目标比较对象
     * @return         当前对象与目标对象的大小
     * 大于0： this > target
     * 等于0:  this = target
     * 小于0：this < target
     */
    public int compareTo(Student target) {

        return this.age - target.age;
    }

    /**
     * 总结：
     * 如果A>B 比较器返回大于0 则升序
     * 如果A>B 比较器返回小于0 则降序
     *
     */
    public static void main(String args[]){
        Student stu = new Student("pp",18);
        Integer result = stu.compareTo(new Student("dd",19));
        System.out.println(result<0?"dd比pp大":(result==0?"dd和pp一样大":"pp比dd大"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        return age != null ? age.equals(student.age) : student.age == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }
}
