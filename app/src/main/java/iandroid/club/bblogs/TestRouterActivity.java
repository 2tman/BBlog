package iandroid.club.bblogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.iandroid.bbase_module.router.HomeRouteUtils;
import com.iandroid.bbase_module.router.MineRouteUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import org.joor.Reflect.*;

import static org.joor.Reflect.on;

public class TestRouterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_router);

        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build(HomeRouteUtils.Home_Activity_Main)
                        .withString("url", "http://www.baidu.com")
                        .withBoolean("isReal", true)
                        .withInt("age", 20)
                        .navigation();
            }
        });
        findViewById(R.id.btn_mine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", "tom");
                ARouter.getInstance()
                        .build(MineRouteUtils.Mine_Activity_Main)
                        .withBundle("params", bundle)
                        .navigation();
            }
        });
    }

    /**
     * 反射测试
     * java.lang.Class ---- 类的创建
     * java.lang.reflect.Constructor  -----  反射类中构造方法
     * java.lang.reflect.Field  ----- 反射属性
     * java.lang.reflect.Method ----- 反射方法
     * java.lang.reflect.Modifier ----- 访问修饰符的信息
     * <p>
     * 实现反射，实际上是得到Class对象，使用java.lang.Class这个类。
     * 这是Java发射机制的起源，当一个类被加载后，Java虚拟机会自动产生一个Class对象
     */
    private void doReflect() throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取Class对象的方法

        //1.
        try {
            Class clz1 = Class.forName("iandroid.club.bblogs.TestRouterActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2.
        Class clz2 = TestRouterActivity.class;

        //3.getClass获取
        Class clz3 = new TestRouterActivity().getClass();

        /**
         * 三种方法的区别：
         * 1.会让ClassLoader装载类，并进行类的初始化
         * 2.返回类对象运行时真正所指的对象/所属类型的Class对象
         * 3.ClassLoader装载入内存，不对类进行类的初始化操作
         * 区分重点在于，是否进行初始化 和 是否在实例中获取
         */

        /**
         * 1.forName中的参数需要填入全路径类名,
         * 但是只能创建无参数对象
         */
        try {
            Class clz = Class.forName("");
            Object o = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 2.有参数创建对象
         * 返回一个Constructor对象，反映了此Class对象所表示的类指定的公共构造方法
         */

        Constructor<?> csr = clz2.getConstructor(String.class, int.class);
        Object o = csr.newInstance("gabriel", 20);


        /**
         * 反射类中的属性需要使用Field对象
         */
        Field field = clz2.getDeclaredField("name");

        //使用setAccessible取消封装，特别是可以取消私有字段访问限制
        field.setAccessible(true);

        // 设置o 对象
        field.set(o, "hello");

        /**
         * Field类描述的是属性对象，其中可以获取到很多属性的信息，包括名字/属性类型/属性的注解
         *
         * 在安全管理器中会使用checkPermission方法来检查权限，
         * 而setAccessible(true)并不是将方法的权限改为public,而是取消java的权限控制检查，
         * 所以即使是public方法，qiaccessible属性默认也是false
         */

        /*
        获取修饰符
         */
        String priv = Modifier.toString(field.getModifiers());

        /*
        获取类中的方法
        getDeclaredMethod 获取的是类自身声明的所有方法，包含public/protected和private方法
         */
        Method m = clz2.getDeclaredMethod("setName", String.class);

        /*
        通用反射调用方法
        Method中的invoke方法用于检查 AccessibleObject 的override属性是否为true
        AccessibleObject是Method/Field/Constructor的父类，override属性默认为false,
        可以调用setAccessible方法改变，如果设置为true，则表示可以忽略访问权限的限制，直接调用。
         */
        m.invoke(clz2, "hello");

        /**
         * 异常处理
         * 1.获取不到Class
         * 当Class.forName()中路径获取不到对应的Class时，会抛出异常。
         * 2.获取不到Field
         * (1).确实不存在这个Field
         * (2).修饰符导致的权限问题
         * 以上两种情况会抛出NoSuchFieldException异常。
         * getField只能获取对象中public修饰符的属性，并且能获取父类Class的public属性。
         *
         * getDeclaredField能获取对象中各种修饰符的属性。但无法获取父类的任何属性。
         *
         * 如何才能获取父类的属性呢？class对象会提供getSuperclass方法来获取父类对象，然后
         */

        /**
         * java 的反射机制提供了动态代理模式实现
         */

        RealSubject realSubject = new RealSubject();
        Subject proxySubject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(),
                new Class[]{Subject.class},
                new ProxyHandler(realSubject));
        proxySubject.doSomething();

        //动态代理的作用在于 不修改源码的情况下，可以增强一些方法，在方法的执行前后做些想做的事情

    }

    public interface Subject {
        public void doSomething();
    }

    public class RealSubject implements Subject {
        @Override
        public void doSomething() {

        }
    }

    public class ProxyHandler implements InvocationHandler {
        private Object realSubject;

        public Object getRealSubject() {
            return realSubject;
        }

        public ProxyHandler(Object realSubject) {
            this.realSubject = realSubject;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) {
            //在转调具体目标对象之前，可以执行一些功能处理
            //调用具体目标对象方法
            Object result = null;
            try {
                result = method.invoke(realSubject, objects);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public void testjOOR() {
        Test test = on("iandroid.club.bblogs.Test")//类似Class.forName()
                .create("Test")//调用类中的构造方法
                .call("doTest")//调用类中的方法
                .call("doTest2")//再次调用类中的方法
                .get();//获取包装好的对象

        /**
         * 并且支持动态代理
         */
        RealSubject test1 = on("iandroid.club.bblogs.Test")
                .create("Test")
                .as(RealSubject.class);
        test1.doSomething();
    }
}
