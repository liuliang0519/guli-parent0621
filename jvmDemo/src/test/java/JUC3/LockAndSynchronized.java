package JUC3;

import sun.misc.Cache;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock 和 synchronize 的区别
 * lock可用 trylock方法 过时不候 再次请求
 * synchronize 不见不散  一直再等 容易造成请求大量积压
 *  以下是案例代码
 */
public class LockAndSynchronized{

    public static void main(String[] args) {

        Object o = new Object();

        Lock lock = new ReentrantLock();

        new Thread(()->{
            try {
                if (lock.tryLock()){   //tryLock方法有返回值  返回true表示抢到了锁 false表示没抢到锁
                    System.out.println(Thread.currentThread().getName()+"线程  抢到了锁");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName()+"线程释放了锁！");
                }else {
                    System.out.println(Thread.currentThread().getName()+"线程没抢到锁！  离开了，一会再次访问");
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"A").start();

        new Thread(()->{
            try {
                if (lock.tryLock(2L, TimeUnit.SECONDS)){   //tryLock方法有返回值  返回true表示抢到了锁 false表示没抢到锁
                    System.out.println(Thread.currentThread().getName()+"线程  抢到了锁");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName()+"线程释放了锁！");
                }else {
                    System.out.println(Thread.currentThread().getName()+"线程没抢到锁！  离开了，一会再次访问");
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"B").start();



        /*
        new Thread(()->{
            synchronized (o){
                try {
                    System.out.println("B线程持有了锁");
                    Thread.sleep(2000);
                    System.out.println("B线程释放了锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            synchronized (o){
                try {
                    System.out.println("A线程持有了锁");
                    Thread.sleep(2000);
                    System.out.println("A线程释放了锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        */

        //以上代码  只要某个线程抢到了资源  另一个线程就会一直等待，不见不散。如果等待的线程过多就会高并发压崩服务器

        //换用lock的可重入锁






    }

}
