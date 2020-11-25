package JUC3;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


//实现Runable接口 创建新线程
public class Demo3 implements Runnable{


    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName()+(i+1));
        }

    }


    public static void main(String[] args) {

        Thread thread = new Thread(new Demo3(),"aa");
        thread.start();
    }
}
