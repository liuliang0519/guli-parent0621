package JUC2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


//读写锁  模拟缓存读写
public class Demo2 {
    private volatile Map<String,Object> m =  new HashMap();
    //创建读写锁
    private ReadWriteLock RWL = new ReentrantReadWriteLock();
    private Lock lock = new ReentrantLock();

    //存数据
    public void put(String key,Object value) {
//          RWL.writeLock().lock();
//        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写");
            TimeUnit.MILLISECONDS.sleep(200);

            System.out.println(Thread.currentThread().getName()+"已经写完了数据");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
//            RWL.writeLock().unlock();
//           lock.unlock();
        }

    }


    //读数据
    public Object get(String key){
        Object o = null;
//           RWL.readLock().lock();
//           lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在读");
            TimeUnit.MILLISECONDS.sleep(200);
             o = m.get(key);
            System.out.println(Thread.currentThread().getName()+"已经读到了数据"+o);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
//            lock.unlock();
//            RWL.readLock().unlock();

        }
        return o;
    }


    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();

        //写
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                demo2.put(String.valueOf(finalI),String.valueOf(finalI +1));
            },"aa").start();
        }

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //读
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                demo2.get(String.valueOf(finalI));
            },"bb").start();
        }




    }
}
