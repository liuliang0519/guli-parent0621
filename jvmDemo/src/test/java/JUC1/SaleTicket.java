package JUC1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//创建票资源类
class Ticket1 {
    //定义票数
    private int number = 30;

    // synchronized 卖票的方法
//    public synchronized void sale() {
//        if(number>0) {
//            //获取当前线程名称  Thread.currentThread().getName()
//            System.out.println(Thread.currentThread().getName()+"卖出："+(number--)+"剩下"+number);
//        }
//    }
    //lock实现 卖票的方法
    public void sale() {
        //创建可重入锁
        Lock lock = new ReentrantLock();
        //上锁
        lock.lock();
        //解锁
        try {
            if(number>0) {

                //获取当前线程名称  Thread.currentThread().getName()
               --number;
                System.out.println(Thread.currentThread().getName()+"卖出了票，还剩下"+number+"张");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

// 3个售票员 卖出 30张票
public class SaleTicket {

    public static void main(String[] args) {
        Ticket1 ticket = new Ticket1();
        //使用lam表达式创建线程
        //new Thread(()->{ for (int i = 1; i <=40; i++) ticket.sale(); },"WW");

        //创建三个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=40; i++) {
                    ticket.sale();
                }
            }
        },"AA").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=40; i++) {
                    ticket.sale();
                }
            }
        },"BB").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=40; i++) {
                    ticket.sale();
                }
            }
        },"CC").start();
    }
}
