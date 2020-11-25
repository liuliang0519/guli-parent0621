package JUC1;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class NoSafeDemo {
    public static void main(String[] args) throws InterruptedException {

        List<String> list = new ArrayList<>();
//        Vector<String> v = new Vector<>();
        List<String> list1 = new CopyOnWriteArrayList<>();


        for (int i = 0; i < 3000; i++) {
            new Thread(()->{
                list1.add(UUID.randomUUID().toString().substring(0,4));
                System.out.println(list1);
            },String.valueOf(i)).start();
//       java.util.ConcurrentModificationException  并发修改异常  使用CopyOnWriteArrayList解决问题（写时复制   /.1
//
//       j ）  写加锁读不加锁 相比vector全加锁效率更高

     }
    }
}
