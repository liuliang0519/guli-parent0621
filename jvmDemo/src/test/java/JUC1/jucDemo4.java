package JUC1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource{
    //定义标志位  1代表aa 2代表bb 3代表cc
    private int flag = 1;

    //创建可重入锁
    Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    //aa使用的方法
    public void print5(){
        lock.lock();
        try {
            // 判断
            while (flag != 1){
                c1.await();
            }
            // 干活
            for (int i = 1; i <= 5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"打印了==  "+i);
            }
            // 通知
            flag = 2; //改成2 让bb执行
            c2.signalAll();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    //bb使用的方法
    public void print10(){
        lock.lock();
        try {
            // 判断
            while (flag != 2){
                c2.await();
            }
            // 干活
            for (int i = 1; i <= 10 ; i++) {
                System.out.println(Thread.currentThread().getName()+"打印了==  "+i);
            }
            // 通知
            flag =3; //改成3让cc执行
            c3.signalAll();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    //cc使用的方法
    public void print15(){
        lock.lock();
        try {
            // 判断
            while (flag != 3){
                c3.await();
            }
            // 干活
            for (int i = 1; i <= 15 ; i++) {
                System.out.println(Thread.currentThread().getName()+"打印了==  "+i);
            }
            // 通知
            flag = 1; //改成1 让aa执行
            c1.signalAll();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


}

public class jucDemo4 {
    public static void main(String[] args) {
        ShareResource sr = new ShareResource();


            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        sr.print5();
                    }
                }
            },"aa").start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        sr.print10();
                    }
                }
            },"bb").start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        sr.print15();
                    }
                }
            },"cc").start();


        }

}
