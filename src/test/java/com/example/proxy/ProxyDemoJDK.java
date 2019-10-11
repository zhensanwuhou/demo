package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//静态代理
public class ProxyDemoJDK {

    public static void main(String[] args) {
        Singer2 target = new Singer2();
        ISinger2 singer2 = (ISinger2)Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler(){
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("start");
                        Object returnValue = method.invoke(target, args);
                        System.out.println("end");
                        return returnValue;
                    }
                }
        );
        singer2.sing();
    }

}

interface ISinger2{
    void sing();
}

class Singer2 implements ISinger2{

    @Override
    public void sing() {
        System.out.println("啦啦啦啦2");
    }
}
