package ZhouYang;

public class ClassLoader {
    public ClassLoader(){
        System.out.println(333);
    }

    {
        System.out.println(111);
    }

    static {
        System.out.println(222);
    }
    public static void main(String[] args) {
        System.out.println(444);
        new A();
        System.out.println("=======");
        new A();
        System.out.println("=======");
        new ClassLoader();
    }
}


class A{
    A(){
        System.out.println(777);
    }

    {
        System.out.println(666);
    }

    static {
        System.out.println(555);
    }

}
