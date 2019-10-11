package com.example.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyDemoCglib {
    public static void main(String[] args) {
        Singer3 target = new Singer3();
        Singer3 singer3 = (Singer3) new ProxyFactory(target).getProxyInstance();
        singer3.sing();
    }
}

class Singer3 {
    public void sing(){
        System.out.println("啦啦啦啦333");
    }
}


/**
 * Cglib子类代理工厂
 */
class ProxyFactory implements MethodInterceptor{

    //维护目标对象
    private Object target;

    public ProxyFactory(Object obj){
        this.target = obj;
    }

    // 给目标对象创建一个代理对象
    public Object getProxyInstance(){
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(target.getClass());
        //3.设置回调函数
        en.setCallback(this);
        //4.创建子类(代理对象)
        return en.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("start");
        //执行目标对象的方法
        Object returnValue = method.invoke(target, args);
        System.out.println("end");
        return returnValue;
    }
}


