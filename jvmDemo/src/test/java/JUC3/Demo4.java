package JUC3;


//实现Runable接口 创建线程 匿名内部类
public class Demo4 {

    //目标资源
    private Integer count = 0;

    public synchronized void add(){
        count++;
        System.out.println(Thread.currentThread().getName()+"正在加运算，结果为 ："+count);
    }

    public synchronized void remove(){
        count--;
        System.out.println(Thread.currentThread().getName()+"正在减运算，结果为 ："+count);
    }

    public static void main(String[] args) {

        Demo4 demo4 = new Demo4();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 9; i++) {
                    demo4.add();
                }

            }
        },"A线程").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 9; i++) {
                    demo4.remove();
                }
            }
        },"B线程").start();



    }

}
