package JUC1;
//编写例子  3个售票员 一起出售30张票
/*
* 实现思路
* 模板： 1 创建资源类 定义属性和方法
*       2 创建多个线程  共同操作资源方法
*
* 资源：票
* 操作：卖
* 3个线程（售票员） 共同 调 卖票 的方法
* */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 1 创建资源类
class Ticket{
    //定义票数
    private int num = 30;

    //卖票的方法    synchronized关键字加锁
//    public synchronized  void sale(){
////        if (this.num > 0){
////            this.num--;
////            //获取当前线程的名称
////            System.out.println(Thread.currentThread().getName() + "卖出了票，总票数还剩"+num);
////        }
////
////
////    }

    //创建 可重入锁对象
    Lock l = new ReentrantLock();

    //lock锁的方式
    public void sale2(){

        //上锁
        l.lock();
        try {
                if (num > 0){
                   // this.num--;
                    //获取当前线程的名称
                    System.out.println(Thread.currentThread().getName() +(num--)+"卖出了票q，总票数还剩"+num);
                }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //必须放到finally中
            l.unlock(); //解锁
        }
    }
}

public class JUCDemo {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        //创建3个线程
        new Thread(new Runnable() {

            public void run() {
                for (int i = 0; i <= 40 ; i++) {
                    ticket.sale2();//卖票
                }
            }
        },"aa").start();

        new Thread(new Runnable() {

            public void run() {
                for (int i = 0; i <= 40 ; i++) {
                    ticket.sale2();//卖票
                }
            }
        },"bb").start();
//
        new Thread(new Runnable() {

            public void run() {
                for (int i = 0; i <= 40 ; i++) {
                    ticket.sale2();//卖票
                }
            }
        },"cc").start();


    }
}
