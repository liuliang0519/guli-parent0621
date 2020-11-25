package ZhouYang;

public class ThreadDemo {

   public static void main(String[] args) {

       Object o1 = new Object();
       Object o2 = new Object();

       new Thread(()->{
          synchronized (o1){
              System.out.println(Thread.currentThread().getName()+"线程持有了o1 ，想持有o2");
              try {
                  Thread.sleep(1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              synchronized (o2){
                  System.out.println("a线程持有了o2");
              }

          }

       },"a").start();

       new Thread(()->{
           synchronized (o2){
               System.out.println(Thread.currentThread().getName()+"线程持有了o2 ，想持有o1");
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               synchronized (o1){
                   System.out.println("b线程持有了o1");
               }

           }

       },"b").start();

   }
}
