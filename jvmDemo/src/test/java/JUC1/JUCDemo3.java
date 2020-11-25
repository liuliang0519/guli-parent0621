package JUC1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//使用lock进行实现
class Opnumb2{

    private int num = 0;
    //创建 可冲入锁
    private Lock l = new ReentrantLock();

    private Condition condition = l.newCondition();




    public  void addOne() throws InterruptedException {
        System.out.println("================");
        l.lock();
        try{
            //判断
            while (num != 0){
                condition.await();
            }
            //干活
            num++;
            System.out.println("加一之后，num= "+num);
            //通知
            condition.signalAll();

        }finally {
            l.unlock();
        }

    }

    public  void removeOne() throws InterruptedException {
        System.out.println("================");
        l.lock();
        try{
            //判断
            while (num != 1){
                condition.await();
            }
            //干活
            num--;
            System.out.println("减一之后，num= "+num);
            //通知
            condition.signalAll();
        }finally {
            l.unlock();
        }
    }

}

public class JUCDemo3 {
    public static void main(String[] args) {

        Opnumb2 op = new Opnumb2();

        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i <10 ; i++) {
                    try {
                        op.addOne();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        },"AA").start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i <10 ; i++) {
                    try {
                        op.removeOne();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        },"BB").start();



//        new Thread(()->{
//            for (int i = 0; i <10 ; i++) {
//                try {
//                    opnumb.addOne();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        },"AA");
//
//        new Thread(()->{
//            for (int i = 0; i <10 ; i++) {
//                try {
//                    opnumb.removeOne();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        },"BB");


    }
}
