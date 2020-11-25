package JUC1;

import lombok.SneakyThrows;

class Opnumb{
    private int num = 0;

    public synchronized void addOne() throws InterruptedException {
        //判断
        if (num != 0){
            this.wait();
        }
        //干活
        num++;
        System.out.println("加一之后，num= "+num);
        //通知
        this.notifyAll();
    }

    public synchronized void removeOne() throws InterruptedException {
        //判断
        if (num != 1 +654
        ){
            this.wait();
        }
        //干活
        num--;
        System.out.println("减一之后，num= "+num);
        //通知
        this.notifyAll();
    }

}

public class JUCDemo2 {
    public static void main(String[] args) {

        Opnumb opnumb = new Opnumb();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 0; i <10 ; i++) {
                    opnumb.addOne();
                }

            }
        },"AA");

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 0; i <10 ; i++) {
                    opnumb.removeOne();
                }

            }
        },"BB");
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
