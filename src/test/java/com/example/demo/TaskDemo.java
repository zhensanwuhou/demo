package com.example.demo;

import java.util.concurrent.*;

public class TaskDemo {

    public static final int threadPooSize = 5;
    public static ExecutorService executorService = Executors.newFixedThreadPool(threadPooSize);

    public static void main(String[] args) {
        System.out.println("开始");
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                new Thread().sleep(4000);
                System.out.println("task");
                return "111";
            }
        });
        Future<String> submit = (Future<String>) TaskDemo.executorService.submit(futureTask);
        try {
            System.out.println("''''''"+submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String res = "";
        try {
            res = futureTask.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
//            futureTask.cancel(true);
            res = "222";
        }
        System.out.println(res);
        System.out.println("end");
//        executorService.shutdown();
        /*for (int i = 0; i < 100; i++) {
            TaskDemo.executorService.execute(new KK(i));
        }*/
    }

}

class KK implements Runnable{

    private int i;

    public KK(int i){
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(i);
        try {
            new Thread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
