package JUC3;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//实现Callable接口 实现创建线程
public class Demo6 implements Callable<Integer>{

    private int num = 0;

//    @Override
//    public String call() throws Exception {
//
//        for (int i = 0; i < 20; i++) {
//            System.out.println(num++);
//        }
//        return null;
//    }
    @Override
    public Integer call() throws Exception {

        return null;

    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> futureTask1 =
                new FutureTask<Integer>(()->{
                    System.out.println(Thread.currentThread().getName()+"  come in callable");
                    return 1024;
                });

        FutureTask<Integer> futureTask2 =
                new FutureTask<Integer>(()->{
                    System.out.println(Thread.currentThread().getName()+"  come in callable");
                    return 2048;
                });

        new Thread(futureTask1,"zhang3").start();
        new Thread(futureTask2,"li4").start();

        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
//        System.out.println(futureTask.get());

    }


}
