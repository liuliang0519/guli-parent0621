package JUC3;

import lombok.SneakyThrows;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo9 implements Runnable{


    public static void main(String[] args) {
        Demo9 demo9 = new Demo9();

        new Thread(demo9,"A").start();

        new Thread(demo9,"B").start();


    }



//    @SneakyThrows
//    @Override
//    public synchronized void run() {
//        System.out.println(Thread.currentThread().getName()+"线程抢到了");
//        Thread.sleep(3000);
//        System.out.println(Thread.currentThread().getName()+"线程释放了锁走了");
//    }

    Lock lock = new ReentrantLock();

    @SneakyThrows
    @Override
    public void run() {

      if (lock.tryLock(2L,TimeUnit.SECONDS)) {
        try {
          // manipulate protected state
            System.out.println(Thread.currentThread().getName()+"线程抢到了");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"线程释放了锁走了");
        } finally {
        lock.unlock();
        }
      } else {
        // perform alternative actions
          System.out.println(Thread.currentThread().getName()+"不等了，没抢到");
      }

    }
}
