package JUC3;


import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

//自定义线程池的方式 创建线程
public class Demo7 {
    public static void main(String[] args) {


        ExecutorService threadPool = new ThreadPoolExecutor(
                //  七大参数 重要！！！
                2,  //核心线程数
                5,  //最大线程数
                6,  //线程回收时间
                TimeUnit.SECONDS,  //单位 秒
                new ArrayBlockingQueue<Runnable>(5), //设置阻塞队列的长度大小
                Executors.defaultThreadFactory(),  //默认的线程工厂
                new ThreadPoolExecutor.CallerRunsPolicy() //拒绝策略 有4种  1 AbortPolicy()  默认策略 达到阈值会抛出 RejectedExecutionException 异常
                                                                     //       2 CallerRunsPolicy()  达到阈值 会让main线程参与执行（调用者运行策略，不会抛弃任务）
                                                                     //       3 DiscardOldestPolicy() 达到阈值  优先处理新的请求（而不是队列里面的）一共处理max+blockMax次数
                                                                     //       4 DiscardPolicy()  达到阈值 默默不处理新的请求（）
        );
        try {
            for (int i = 1; i <=30; i++) {

                int finalI = i;
//                threadPool.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println(Thread.currentThread().getName()+"线程 创建啦  "+ finalI);
//                    }
//                });


//                threadPool.execute(()->{
//                    System.out.println(Thread.currentThread().getName()+"线程 创建啦  给  "+ finalI+"  号客人提供了服务！");
//                });

                  threadPool.submit(new Runnable() {
                      @Override
                      public void run() {
                          System.out.println(Thread.currentThread().getName()+"线程 创建啦  给  "+ finalI+"  号客人提供了服务！");
                      }
                  });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
