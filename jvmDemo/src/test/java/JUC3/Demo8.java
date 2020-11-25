package JUC3;


import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

// 使用spring 创建线程池
public class Demo8 {
    public static void main(String[] args) {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(2);  //核心线程数
        threadPool.setMaxPoolSize(5);   //最大线程数
        threadPool.setKeepAliveSeconds(6);  //空闲线程存活时间
        threadPool.setQueueCapacity(5);   //任务队列长度
        threadPool.initialize();  //线程池创建 初始化
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //???? 配置无效？？


        try {

            for (int i = 1; i <= 11; i++) {

                int finalI = i;
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName()+"号 线程启动， 为： "+ finalI +" 号客人提供了服务！");
                    }
                });

            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            threadPool.shutdown();
        }



    }
}
