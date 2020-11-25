package JUC3;


import java.util.concurrent.*;

//
public class ThreadPoolDemo2 {
    public static void main(String[] args) {

        //自定义线程池
        ExecutorService threadpool = new ThreadPoolExecutor(
                2, //初始化线程数 coreSize
                5,  //最大线程数
                3,  //回收线程时间
                TimeUnit.SECONDS,  //时间单位
                new ArrayBlockingQueue<Runnable>(3), //设置队列长度
                Executors.defaultThreadFactory(),  //线程工厂（默认固定）
                new ThreadPoolExecutor.CallerRunsPolicy()//拒绝策略   4种
        );

        for (int i = 0; i < 50; i++) {
            threadpool.execute(()->{

                System.out.println(Thread.currentThread().getName()+"正在办理");

            });
        }

    }





}
