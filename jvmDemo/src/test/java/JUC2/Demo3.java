package JUC2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

//阻塞队列
public class Demo3 {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(4);//指定队列长度3

//        int num = 0;
//        while (num < 20){
//            num++;
//            System.out.println(blockingQueue.add(num+1+""));
//
//        }
//
//        int num2 = 0;
//        while (num2 < 21){
//            num2++;
//            System.out.println(blockingQueue.poll()+"b");
//            System.out.println(blockingQueue.remove()+"a");
//
//        }

        int num1 = 0;
        while (num1 < 20){
            num1++;
            System.out.println(blockingQueue.offer(num1+1+"",3L, TimeUnit.SECONDS));//put 队列满了  造成阻塞


        }




    }
}
