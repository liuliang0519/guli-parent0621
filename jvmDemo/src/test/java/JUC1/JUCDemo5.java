package JUC1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class demo{
    private int num = 1;//打印的数字
    private char word = 65;
    private int flag = 1;//标志位

    private Lock lock = new ReentrantLock();//创建可重入锁
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();

    //打印数字1-52
    public void printNum() throws InterruptedException {
        lock.lock();
        try {
            //判断
            while (flag==3){
                c1.await();
            }
            //操作
            System.out.print(num + " ");
            flag++;
            num++;
            //唤醒
            c2.signal();

        }finally {
            lock.unlock();
        }




    }

    //打印A-Z
    public void printAZ() throws InterruptedException {
        lock.lock();
        try {
            //判断
            while (flag!=3){
                c2.await();
            }
            //操作
            System.out.print(word + " ");
            flag=1;
            word++;
            //唤醒
            c1.signal();

        }finally {
            lock.unlock();
        }

    }

}
public class JUCDemo5 {
    public static void main(String[] args) {
        demo demo = new demo();
        new Thread(()->{
            for (int i = 0; i < 52; i++) {
                try {
                    demo.printNum();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"aa").start();


        new Thread(()->{
            for (int i = 0; i < 26; i++) {
                try {
                    demo.printAZ();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"bb").start();


    }
}
