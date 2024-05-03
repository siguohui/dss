object.getClass()和Object.class的区别

两者的区别如下：
类名.class叫做“类字面量”，因class是关键字, 所以类名.class编译时确定。而getclass()是某个具体的方法来调用，是运行时根据实际实例确定，getClass()是动态而且是final的。
例如：
String.class 是能对类名的引用取得在内存中该类型class对象的引用，而new String().getClass() 是通过实例对象取得在内存中该实际类型class对象的引用。


public static <T> T request2Bean(HttpServletRequest request,Class<T> clazz){}

https://www.cnblogs.com/zhaoyanhaoBlog/p/9362267.html

单独的T 代表一个类型（表现形式是一个类名而已） ，
Class<T>代表这个类型所对应的类（又可以称做类实例、类类型、字节码文件）， 
Class<？>表示类型不确定的类

Class<T>表示T类型的字节码文件，意思是：
Class<T> 相当于Class<T> c=T.class，
T  t  new T() ;
或者Class<T> c= t.getClass();
通过以上可以获取类名为c.getName();


Class<T> ct=T.class，T  t  new T() ; 与Class c=T.class，T  t  new T() ;
ct泛型指的是ct只能是T的字节码，而c可以是任何类的字节码。所以用ct用法更好

E - Element (在集合中使用，因为集合中存放的是元素)
T - Type（Java 类）
K - Key（键）
V - Value（值）
N - Number（数值类型）
? -  表示不确定的java类型
举例说明：
Set<T> 表示 集合里 是   T类的实例
List<E> 表示  集合里 是  E类的实例
List<?> 表示 集合里的对象类型不确定，未指定 
List 同 List<?> 是一样的。
泛型的作用： 1、用泛型：
Java代码  收藏代码
List<T> list=new ArrayList<T>();  
T t=list.get(0);  2、不用泛型：
Java代码  收藏代码
List  list=new ArrayList();  
T t=(T)list.get(0); 


？ 表示不确定的java类型。
T  表示java类型

Class<T>在实例化的时候，T要替换成具体类
Class<?>它是个通配泛型，?可以代表任何类型
<? extends T>受限统配，表示T的一个未知子类。
<? super T>下限统配，表示T的一个未知父类。
Class<T> 多见于泛型类的定义和声明。
Class<?>是Class<? extends Object>的简写。
Object是所有类的根类，是具体的一个类，使用的时候可能是需要类型强制转换的，但是用T ？等这些的话，在实际用之前类型就已经确定了，不需要强制转换。
那么
MyClass<? extends A> a=new MyClass<B>();
问好表示a所知的对象不知道是什么具体类型，extends A意味着这B肯定（必须）是A的子类或者就是A本身。

<？ extends Collection> 这里？代表一个未知的类型，这个未知的类型实际上是Collection的一个子类，Collection是这个通配符的上限.
