package JUC2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

public class Demo1 {
//    public static void main(String[] args) throws InterruptedException {
//        CountDownLatch countDownLatch = new CountDownLatch(6);
//
//        for (int i = 1; i <= 6; i++) {
//            new Thread(()->{
//
//                System.out.println(Thread.currentThread().getName()+"号同学离开了");
//                //值 -1  减到0 唤醒
//                countDownLatch.countDown();
//            },String.valueOf(i)).start();
//        }
//        countDownLatch.await();
//        System.out.println("班长走了");
//
//    }

    static final int NUM = 7;
    public static void main(String[] args) {




        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM, () -> {
            System.out.println("集齐7个，召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {
            new Thread(()->{
                System.out.println("收集了一颗"+Thread.currentThread().getName()+"星龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }

}
