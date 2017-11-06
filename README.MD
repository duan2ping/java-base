
# 八大基本数据类型：

                            字节
               |--> byte      1
              |--> short     2
        整型：|--> int       4
              |--> long      8

                --> float    4
        浮点型：|--> double  8

        布尔型：|--> boolean 1

        字符：  |--> char    2

# 特殊的类型：void,空类型

         Void类是一个不可实例化的占位符类，它持有对表示Java关键字void的Class对象的引用
         Void类是final修饰，则不能实例化，即一开始就已经存在因此也可以认为是java基本数据类型（《Thinking in Java》）
         在基本数据类型的封装类中都有public static final Class<封装类型>  TYPE = (Class<封装类型>) Class.getPrimitiveClass("基本类型");
         例：public static final Class<Integer>  TYPE = (Class<Integer>) Class.getPrimitiveClass("int");
         Void中也有该属性，因此也能将void类型归为基本数据类型（Primitive：原始的）

# 数据类型取值范围(不同硬件和不同语言有所不同)

    1M = 1024K, 1K(KB)=1024B(byte), 1B(字节) = 8bit;
    bit：比特(位)，每一位由 0,1组成。计算机的存储信息最小数据单位

    数据类型取值范围推到：
    例：int的取值范围
        int-->4个字节-->4X8=32位
        有符号范围：-2^31 --- 2~31-1
        int存储为： 00000000 00000000 00000000 00000000
        最大的值为：01111111 11111111 11111111 11111111  [最高位为符号位]
        最大值转换为十进制：
            ①：Sn = 1*2^30 + 1*2~29 + ... +1*2^1 + 1*2^0;
            由数列得An=BnCn，其中{Bn}为等差数列，{Cn}为等比数列
            乘公比错位相减即：②：2*① --> 2Sn = 1*2^31 + 1*2~30 + ... +1*2^2 + 1*2^1;
            由②-①得 Sn = 2^31 - 1
            即int的最大值为 2^31 - 1 同理 最小值为 -2^31
        ----> 其他数据类型范围为 -2^n -- 2^n - 1（n为位数）


# 类：

    ①：public 权限的类只能有一个（也可以一个都没有，但最多只有1个）
    ②：这个.java文件的文件名必须是public类的类名
    ③：若这个文件中没有public的类，则文件名随便是一个类的名字即可
        用javac 编译这个.java文件的时候，它会给每一个类生成一个xxx.class文件

# 访问修饰符

    ①：public     --> 公共的
                   --> 作用范围：本类，其他包下其他类

    ②：protected  --> 受保护的
                   --> 作用范围：本类，同包下的所有子类[不同包中的子类不能访问]

    ③：private    --> 私有的
                   --> 作用范围：本类

    ④：default    --> 默认的
                   --> 作用范围：本类，同包下的所有类
                   
# equals和==
  
  （1）对于==，如果作用于基本数据类型的变量，则直接比较其存储的"值"是否相等
       如果作用于引用类型的变量（String），则比较的是所指向的对象的地址（即是否指向同一个对象）。
  （2）equals方法是基类Object中的方法，因此对于所有的继承于Object的类都会有该方法
       在Object类中，equals方法(底层实现就是==)是用来比较两个对象的引用是否相等，即是否指向同一个对象。
  （3）对于equals方法，注意：equals方法不能作用于基本数据类型的使用。如果没有
       对equals方法进行重写，使用Object的equals(则比较的是对象的地址)而String类
       对equals方法进行了重写，用来比较指向的字符串对象所存储的字符串(字面量)是否相等
       其他的一些类诸如Double，Date，Integer等，都对equals方法进行了重写用来比较指向
       的对象所存储的内容是否相等。

# String深入分析
    
    说明：String不是基本类型，它是一个特殊的引用类型(值不会改变)，是一个字符串常量。
    底层实现：char数组
    特点：一旦被创建就不会改变（String类是final修饰，且值char数组也是final），对它
         的各种操作(replace,subString...)都是创建了一个新的字符串返回
    注意：所谓的不可变是字符串的字面量不可变(字符串的值),但引用可以改变
          例：String a = "abc";
              a = "bcd";
          其中字符串a对象的字面量"abc"是一旦创建便是不可改变的。
          而a对象的引用是可以指向任何合法的对象(引用)
    创建方式
       方式：
            ①"" ,单独使用""引号创建的字符串都是常量,编译期就已经确定存储到String Pool(字符串池)中
            ②new String(""),使用new String()创建的对象会存储到heap(堆)中,是运行期新创建的；
       区别：
            String element1 = "aaa";String element2 = new String("aaa"); 
         方式① 
           实现：JVM首先会去字符串池中查找是否存在"aaa"这个对象，如果不存在，则在字符串池中创建"aaa"这个
                 对象然后将池中"aaa"这个对象的引用地址返回给element1引用，这样element1会指向池中"aaa"这个
                 字符串对象，如果存在，则不创建任何对象，直接将池中"aaa"这个对象的地址返回，赋给element1
           注意：使用①方式，不一定会产生一个新对象。
         方式② 
           实现：JVM首先在字符串池中查找有没有"aaa"这个字符串对象，如果有，则不在池中再去创建"aaa"这个对象
                 直接在堆中创建一个"aaa"字符串对象，然后将堆中的这个"aaa"对象的地址返回赋给引用element2
                 如果没有，则首先在字符串池中创建一个"aaa"字符串对象，然后再在堆中创建一个"aaa"字符串对象，
                 然后将堆中这个"aaa"字符串对象的地址返回赋给element2引用
           注意：使用②方式至少产生一个对象(堆中[程序运行时]和字符串池中[类加载时])
       综上所述可知：
                String a = "a"; String b = "a"; String c = new String("a);
                a==b//true and a==c//false
                
    String的"+"操作
        String c = "c"; 
        //1、String result1 = "a"+"b"+"c"; 
        //2、String result2 = "a" + "b" + c; 
        ①：编译后，将字符串"a","b","c"拼接起来(编译优化)
            // result1 = "abc";
        ②：编译后，将"a","b"拼接起来创建一个StringBuilder在使用append(c)将c拼接将结果toString()
           // result2 = new StringBuilder("ab").append(c).toString();
        说明： String中使用 + 字符串连接符进行字符串连接时，连接操作最开始时如果都是字符串常量
               编译后将尽可能多的直接将字符串常量连接起来，形成新的字符串常量参与后面的拼接
               直到与对象拼接时以前面拼接的字符串常量为参数创建一个StringBuilder对象并使用
               append()对后面的进行拼接，最后将结果toString()
        综上所述可知：
             String a = "a";String b = "b"; String c = "c";
             String element = "abc";
             element=="a"+"b"+"c"//true  and element==a+b+c//false
        缺陷：“+”拼接效率低下
               String s = "";
               for(int i = 0; i < n; i++) {
                   s += "a";
               }
               每进行一次"+"拼接则产生一个StringBuilder，因此会消耗n-1次对象创建和销毁时间
               如果使用StringBuilder.append()拼接则节省n-1次对象创建销毁时间
               所以，当使用大量拼接时使用StringBuilder(如果考虑线程安全则使用StringBuffer)
               如果是String a = "a"+"b"; 这种常量拼接则优于StringBuilder
               
    String、StringBuffer、StringBuilder      
          可变与不可变：String是不可变字符串对象，StringBuilder和StringBuffer是可变字符串对象
          线程安全：String线程安全(常量，不可变)。StringBuffer和StringBuilder的实现都是一样的
                    但是StringBuffer的方法大多都使用了synchronized因此StringBuild(不安全)StringBuffer(安全)
          执行效率：StringBuilder > StringBuffer > String
    
    引用和对象区别
        User user;
        这个语句声明一个User类型的引用变量user[我们常常称之为句柄]，而对象一般通过new产生

# static修饰符

    说明：在thinking in java中提到
         static方法就是没有this的方法。在static方法内部不能调用非静态方法，反过来是可以的。
         而且可以在没有创建任何对象的前提下，仅仅通过类本身来调用static方法。
         这实际上正是static方法的主要用途。
         
    理解：
    1、static方法、属性
         说明：static方法也称静态方法类方法,static修饰的方法和成员属于类所有，
               所有对象共享。
         注意：static修饰的方法不能有其他(属性，方法)非静态的使用。
               但是非静态的方法可以使用静态的属性和方法。static成员是线程非安全的(test/MyTest类里有测试)
               static方法是线程安全的(方法内没有static属性)
         作用：静态方法一般用于工具类，方便程序中的调用(无需创建对象)。
    2、static代码块
            static{}
      　说明：static关键字还有一个比较关键的作用就是用来形成静态代码块以优化程序性能。
              static块可以置于类中的任何地方，类中可以有多个static块。在类初次被加载的时候，
              会按照static块的顺序来执行每个static块，并且只会执行一次。
        作用：优化程序，加载配置
        注意：在类初次被加载时调用(一般一个类只会被加载一次，则只会被调用一次)
    3、代码块(匿名代码块)
            {}
        说明：构造块在创建对象时会在之前被调用，每次创建对象时都会被调用，并且优先于类构造函数执行。 
              构造块中定义的变量是局部变量
        作用：可作对象初始化的准备工作
        注意：每次创建对象时都会被调用(会多次调用)
    注意：static{}是在类加载时调用，一般类只加载一次，则只会执行一次
         {}是在类初始化时调用，类可以初始化多次(只要创建对象)，则会执行多次          
    执行顺序：     static{} > {} > 构造
    如果有父类则：父类static{}> 子类static{} >父类{} > 父类构造 > 子类{} > 子类构造
    静态变量在静态块之间初始化(静态变量是在)
    总结：static方法不能使用非static属性和方法(static变量和static方法在类实例化之前先存在)
          反之可以，详细过程看类的加载过程。
          
# 类的生命周期

    【加载】 -------> 【验证】 -------> 【准备】 -----> 【解析】 -----> 
    【初始化】 ------> 【使用】 -------> 【卸载】
    
    说明：类从被加载到JVM中开始，到卸载为止
          整个生命周期包括：加载、验证、准备、解析、初始化、使用和卸载七个阶段。
          其中加载----到初始化称[类的加载过程]
          其中验证----到解析三个阶段称也称[链接阶段]
    加载：[查找class文件并转成Class对象]
          类将class文件字节码内容加载到内存中，并将这些静态数据转换成方法区中的运行时数据结构
          在堆中生成一个代表这个类的java.lang.Class对象，作为方法区类数据的访问入口。
    验证：[检查载入Class文件数据的正确性]
          验证类数据信息是否符合JVM规范，是否是一个有效的字节码文件
          验证内容涵盖了类数据信息的格式验证、语义分析、操作验证等。
    准备：[给类的静态变量分配存储空间]
         为类中的所有静态变量分配内存空间，并为其设置一个初始值 
          static int a = 3; 则在准备阶段时 a = 0;只是分配空间占位而不是赋值(赋值在初始化阶段)
        （类型的占位值而不是原值，由于还没有产生对象，实例变量不在此操作范围内）
          被final修饰的静态变量，会直接赋予原值
          static final int b = 2; 则在准备阶段时 b = 2;(因为是常量是不变的直接分配)
    解析：[将符号引用转成直接引用]
          虚拟机常量池内的符号引用替换为直接引用的过程。
         （比如String s ="aaa",转化为 s的地址指向“aaa”的地址）
    初始化：[对类的静态变量，静态代码块执行初始化操作]
           将一个类中所有被static关键字标识的代码统一执行一遍，如果执行的是静态变量
           那么就会使用用户指定的值覆盖之前在准备阶段设置的初始值；如果执行的是static代码块
           那么在初始化阶段，JVM就会执行static代码块中定义的所有操作。
    使用：对类的操作使用
    卸载：该类所有的实例都已经被回收，也就是java堆中不存在该类的任何实例。
          加载该类的ClassLoader已经被回收。
          该类对应的java.lang.Class对象没有任何地方被引用
          就会被虚拟机垃圾回收
    总结：[加载]阶段将class文件加载到jvm内存中并将静态数据转换成方法区中的运行时数据结构并
               堆中生成一个Class对象。
          [准备]准备内存空间和数据，这个时候还没有类的实例，唯一属于类的东西就是静态成员了
                所以在准备阶段会给类的静态成员变量分配内存
          [解析]把类的二进制数据中的符号引用转换成直接引用。
                符号引用(类中变量名、方法名这些符号)
                直接引用(内存地址，指针) 
          [初始化]对静态变量的赋值，(因为类相关的只有静态成员,没有实例)
                  ①使用声明时的初始值 ②先执行static属性赋值再执行static{}
    注意：①类的加载过程，是对类一系列相关的处理而不是实例。先有类再有实例
          ②类的加载只是讲类加载到jvm中，而[初始化]需要根据编码中的操作
             一下几种会触发类初始化（new、getStatic、putStatic或invokeStatic）
             1）使用new关键字实例化对象
             2）读取一个类的静态字段（被final修饰、已在编译期把结果放在常量池的静态字段除外）
             3）设置一个类的静态字段（被final修饰、已在编译期把结果放在常量池的静态字段除外）
             4）调用一个类的静态方法
          ③一般来说一个class只会被加载一次(但会被初始化多次)，之后就会从jvm的class实例的缓存中获取
         
#类变量加载(static{})和成员变量加载({})
    1、类变量初始化(会将变量都按源码顺序移到static块中)
      static修饰的类变量:    在准备阶段分配内存，初始化阶段赋值，
      static String a = "a";          null        "a"
      编译成class会将类赋值操作添加到static{}中
      static String a = "a";
      变成：
          static String a; // 准备阶段分配内存
          static{
              a = "a";     // 初始化阶段执行static块即初始化类变量
          }
          
    2、成员变量初始化(会将所有成员变量赋值和匿名块操作按顺序移到构造中)
        成员变量：         在创建时分配和存并初始化
        String b = "b";     "b"
        编译成class会将赋值操作和{}添加到构造中
        String b = "b";
        {
            b = "c";
        }
        public construct(){
            b = "d";
        }
        变成：
            String b;
            public construct(){
                b = "b";
                b = "c";
                b = "d";
            }
# 集合

                           ①Collection                                 接口
                                |
        ________________________|________________________
         |                      |                       |
       ②List                 Queue                   ③Set             接口                ④Map
         |                                              |                                     |
  _____|_____________________                    _____|______                    __________|____________
   |            |              |                   |           |                    |         |           |
(1)ArrayList (2)LinkedList (3)Vector            (1)TreeSet (2)HashSet   常用子类  (1)HashMap (2)Hashtable (3)TreeMap


①：Collection是最基本的集合接口(集合相关测试参考collections包下)
    集合工具类：Collections
    数组工具类：Arrays
②List接口
    特点：有序可重复
    说明：涉及到扩容的，会有一个加载因子用来决定是否需要扩容(元素个数>容量长度*加载因子系数)
          0< 加载因子 <1，默认为 0.75
    遍历方式：
        <1> for
            for(...){}
        <2> for（增强for）
            for(元素类型 itme : 集合){ }
        <3> 转成数组
            元素类型[] item = 集合.toArray();
            for(){} // 使用增强for也可使用普通for
        <4> 迭代器[可看源码分析]
            注意：在迭代器遍历中，不能对集合进行add|remove(modCount，记录集合修改次数，add|remove都会引起变化)
                  否则会出现java.util.ConcurrentModificationException异常
                  原因是，防止多线程下的迭代不安全操作，[Fail-Fast(快速失败)机制]
                  除非通过迭代器自身remove或add对列表结构进行修改，否则在其他
                  线程中以任何形式对列表进行修改，迭代器马上会抛出异常，快速失败
            Iterator<String> ite=list.iterator();
             while(ite.hasNext())
             {
                 元素类型 item = ite.next();
             }
    (1) ArrayList
        结构：ArrayList底层实现为数组，所以是顺序存储结构。
              因为底层为数组，所以必须有固定长度，可设置初始长度（默认为10）
              ArrayList list = new ArrayList(初始长度);
              当集合超过(初始长度*加载因子)时，会自动扩容。扩容为：原始容量*3/2+1
        特点：查询快（顺序存储结构）
        安全性：未同步，所以存在线程安全问题。（高并发下避免使用）
        遍历方式：<1><2><3><4>
    (2) LinkedList
         结构：LinkedList底层为链表存储结构
         特点：增删快，提供了许多对首尾元素的操作
         安全性：未同步，所以存在线程安全问题。（高并发下避免使用）
         遍历方式：<1><2><3><4>
    (3) Vector
        说明：是ArrayList线程安全的代替集合
        结构：数组结构，默认初始长度为10，默认扩容为：原始容量*2
        可指定初始长度和扩容比例：Vector v = new Vector(初始长度,扩容比例);
        安全性：使用了同步，不存在线程安全问题。（高并发下使用）
        缺点：因为使用了同步，所以效率不如ArrayList
        遍历方式：<1><2><3><4>

③：Set接口
      特点：无序不重复
      说明：不重复是指存储数据不重复(真实数据不重复)，而不是不能添加重复数据(add可以添加重复包括null)。
            当添加一个已存在的元素时则会替换之前的元素(所以数据是不重复的，但添加重复是没错的)
            添加引用类型(对象)时，验证是否重复(判断重复的条件就是HashCode和equals，前提是无序集合)，会先试用hashCode(),如果相同再用equals()。自定义对象需
            重写hashCode和equals方法实现，否则会同样会出现重复的
      结构：大多底层都是Map集合的封装
      (1) TreeSet
          结构：(底层是TreeMap)是一种排序二叉树。存入Set集合中的值，会按照值的大小进行相关的排序操作。
          底层算法是基于红黑树来实现的
          特点：有序的Set集合,可以按照任何顺序将元素插入该集合，当对该集合进行迭代时，
                各个值将自动以排序后的顺序出现(默认自然升序)，意味着TreeSet中的元素要实现Comparable接口，
                或者有一个自定义的比较器Comparator(为了给元素指定排序规则)。
          遍历方式：<2><3>(不推荐)<4>
          安全性：线程不安全
          注意：不能添加null(运行时会NullPointerException),因为需要使用Comparator的排序，当前元素.compareTo(目标元素)
          目标元素必须和当前元素类型一致。因此TreeSet添加的元素也必须一致(否则运行时会ClassCastException)
      (2) HashSet
          结构：(底层是HashMap)，底层由Hash算法实现
          遍历方式：<2><3>(不推荐)<4>
          安全性：线程不安全
          注意：可以添加不同类型(包括null)的重复值，因为底层是HashMap，值
                是HashMap的key，特性是，key不能重复，后者添加不进去(不报错)。
                
④：Map接口
    特点：Key-Value键值对
    遍历方式：
        <1> 使用key
            for(元素类型 key : 集合.keySet()){
                String value = 集合.get(key);
            }
        <2> 使用value
            for(元素类型 val : 集合.values()){
                String value = val;
            }
    (1) HashMap
        结构：底层由Entry(内部类)和数组，哈希表结合实现。
        说明：key不可重复，只能有一个null，value可以重复（其中说的不可重复跟Set一样，
               都可以重复，只是重复过后value会被后者覆盖[覆盖的是value，key使用的是前者不会覆盖
               因为底层会使用key求hashCode计算该键值对所对应的Entry，得到key-value]）
        遍历方式：<1><2>
        安全性：线程不安全
    (1) Hashtable
            说明：是HashMap的安全实现，使用了同步，线程安全
            结构：底层由Entry(内部类)和数组，哈希表结合实现。
            遍历方式：<1><2>
            安全性：线程安全
            注意：key，value 都不能为空(运行时异常)
    (1) TreeMap
            说明：带排序的key-value(TreeSet的底层就是TreeMap)
            结构：底层由Entry(内部类)和数组，二叉树结合实现。
            遍历方式：<1><2>
            安全性：线程不安全
            注意：value可以为null，key不可以(运行时错误)
                 
    说明：TreeMap和TreeSet，因为TreeSet底层是TreeMap所以都具有
          TreeMap添加key或TreeSet添加元素时自定义类必须实现Comparable或提供Comparator，
          否则运行时异常,当自定义类既提供了Comparable又提供了Comparator则优先使用Comparator
          因为Comparable是本类重写(本身的方法)，而Comparator是创建一个类(新建比较器类)
          所以会先检查是否提供自定义的比较器，否则才会将对象强转成Comparable
    注意：无序不重复保证不重复的手段先验证hashCode在验证equals，所以自定义类需要重写(不强制，默认Object的方法)
          有序不重复保证不重复的手段优先使用Comparator比较器，否则使用Comparable(强制，必须有Comparator|Comparable)
    扩展：增强for的底层就是iterator（使用javap -c *** 查看for和iterator的反编译文件。两个结果都是一样的）
          如果是基本类型则for使用普通遍历，如果实现了iterator则使用iterator
          根据ierator的特性便可得出增强for的特性。也可以实现Iterator接口自定义遍历操作。
    普通for和foreach
        速度：for > foreach  
        安全：for < foreach
        原因：foreach有对多线程的修改检查，而且源码就是对集合操作进行封装，从而多了很多验证。
    知识点：① 迭代器 ②集合 ③ 比较器 
   
# 排序

    String 比较方法 ： "XXX".compareTo("XXX");返回 = 前者ASCII与后者不同字符处的字符ASCII的差；
    例："abc".compareTo("b");返回的是首位，a和b的ASCII码差值
    特殊的--例："abc".compareTo("ab");可比较的部分为ab且都相同则比较长度。
    说明：比较器不具有排序功能，它只是定义排序的规则(对象间比较大小的方式)和顺序(返回值决定)
          底层依赖比较器得比较并根据其返回值将其按一定规则排序
    作用：用来给对象类型(集合、数组、对象)进行排序
    方式一：
     * Comparator比较器
     * ①：自定义一个比较器实现Comparator接口
     * ②：重写compare()方法
     public class collections.SimpleComparator implements Comparator<Integer>{
        /**
         * 用户自定义比较规则
         * @param o1   元素1
         * @param o2   元素2
         * @return     元素1和元素2的大小
         * 大于0： o1 > o2
         * 等于0:  o1 = o2
         * 小于0： o1 < o2
         */
        public int compare(Integer o1, Integer o2){
            if(o1 > o2){
                return 1;
            }else if(o1 < o2){
                return -1;
            }else{
                return 0;
            }
        }
     }
     方式二：
     * Comparable自然排序
     * ①：需要排序的类实现Comparator接口
     * ②：重写compare()方法
     // 需要排序的类
     public class collections.Student implements Comparable<collections.Student>{

         private String name;
         private Integer age;
         public collections.Student(String name,Integer age){
             this.name = name;
             this.age = age;
         }
         /**
          * 自定义排序规则
          * @param target   目标比较对象
          * @return         当前对象与目标对象的大小
          * 大于0： this > target
          * 等于0:  this = target
          * 小于0： this < target
          */
         public int compareTo(collections.Student target) {

             return this.age - target.age;
         }

      区别：Comparator 和 Comparable
            Comparable属于需要排序的类中的方法，即该类的排序      [内部比较器]
            Comparator属于比较器对象，用来给指定的两个元素进行比较[外部比较器]
     // 中文比较器，用来将中文排序
     Comparator chinese = Collator.getInstance(java.util.Locale.CHINA);
     注意：如果自定义类没有重写Comparable也没有用Comparator
          （在Set和Map中会出现运行时异常ClassCastException，使用sort()时编译不通过）
           两个都实现了，则优先使用Comparator
     总结：在Arrays.sort(数组，比较器)[如果是Comparable方式则省略比较器这个参数，因为该比较方式在类对象里],
           或者Collections.sort(集合，比较器)中，底层排序
           使用到比较器用于决定按什么排序，升序或降序。
           JDK sort()方法源码
            //如果后一位与前一位比较 < 0 则递减。反之递增
            if (c.compare(a[runHi++], a[lo]) < 0) { // Descending
                ...
             } else {                              // Ascending
                ...
             }
             得出：A：后一位   B：前一位
              * 如果A>B 比较器返回大于0 则升序
              * 如果A>B 比较器返回小于0 则降序


# 多线程

    

# AOP

    说明：AOP（Aspect-OrientedProgramming)--面向切面编程，是对OOP的补充和完善或者说是更高级的编程
          思路，面向多对象的公共业务统一操作
    作用：多用于一些统一多对象业务处理，权限认证、日志、事物、异常的处理
    底层原理：cglib代理或jdk动态代理和反射
    优点：分离系统中的各种关注点，将核心关注点和横切关注点分离开来
    核心概念：
            切面（Aspect)：官方的抽象定义为“一个关注点的模块化，这个关注点可能会横切多个对象”。
            连接点（Joinpoint） ：程序执行过程中的某一行为。
            通知（Advice） ：“切面”对于某个“连接点”所产生的动作。
            切入点（Pointcut） ：匹配连接点的断言，在AOP中通知和一个切入点表达式关联。
