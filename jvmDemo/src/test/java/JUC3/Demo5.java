package JUC3;


//继承Thread 类创建线程
public class Demo5 extends Thread {

    private Integer  num = 0;
//    private Integer num = 0;

    @Override
    public void run(){
        for (int i = 0; i < 30 ; i++) {
            System.out.println(num++);
        }
    }



    public static void main(String[] args) {

        Demo5 demo5 = new Demo5();

        Thread thread = new Thread(demo5);

        thread.start();


    }
}
