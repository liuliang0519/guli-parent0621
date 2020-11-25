package JUC3;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 死锁
 * 什么是死锁？
 * A和B俩个线程
 * A线程 拿着a锁 ，想去抢b锁
 * B线程 拿着b锁 ，想去抢a锁
 */
public class DeadLock {



    public static void main(String[] args) {
        Lock lock1 =new ReentrantLock();
        Lock lock2 =new ReentrantLock();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {

                lock1.lock();
                try{
                    System.out.println(Thread.currentThread().getName()+"线程 持有了a锁");
                    Thread.sleep(1000);

                    System.out.println(Thread.currentThread().getName()+"线程 还想抢b锁");
                    lock2.lock();
                    System.out.println("抢到了b锁");
                    lock2.unlock();
                }finally {
                    lock1.unlock();
                }

            }
        },"A").start();


        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {

                lock2.lock();
                try{
                    System.out.println(Thread.currentThread().getName()+"线程 持有了b锁");
                    Thread.sleep(1000);

                    System.out.println(Thread.currentThread().getName()+"线程 还想抢a锁");
                    lock1.lock();
                    System.out.println("抢到了a锁");
                    lock1.unlock();
                }finally {
                    lock2.unlock();
                }

            }
        },"B").start();

    }




}
