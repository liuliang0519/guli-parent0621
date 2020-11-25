package JUC3;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//线程池
public class ThreadPoolDemo1 {
    public static void main(String[] args) {
        //创建线程池 //一池固定 3线程
        ExecutorService threadPool2 = Executors.newFixedThreadPool(3);
        //一池一线程
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //可扩容线程池  //根据cpu核心数
        ExecutorService threadPool = Executors.newCachedThreadPool();

        //上面3个线程池 都是通过 new ThreadPoolExecutor（）  通过构造器重载实现

        try {
            //从线程池 获取线程
            for (int i = 1; i <= 16; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
              threadPool.shutdown();
        }


    }
}

