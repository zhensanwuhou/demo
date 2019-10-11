package com.example.proxy;

//静态代理
public class ProxyDemoStatic {

    public static void main(String[] args) {
        Singer singer = new Singer();
        ProxySinger proxy = new ProxySinger(singer);
        proxy.sing();
    }

}
interface ISinger{
    void sing();
}

class Singer implements ISinger{
    @Override
    public void sing() {
        System.out.println("啦啦啦啦");
    }
}

class ProxySinger implements ISinger{

    private ISinger target;

    public ProxySinger(ISinger singer){
        this.target = singer;
    }

    @Override
    public void sing() {
        System.out.println("start");
        System.out.println("啦啦啦啦");
        System.out.println("end");
    }
}

