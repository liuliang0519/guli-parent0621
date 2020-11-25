package JUC1;

public class JvmDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        //System.out.println(o1.getClass().getClassLoader());
        // 结果为null 因为 （BootStrap ClassLoader）启动类加载器是由c语言编写

        ClassLoader loader = Thread.currentThread()
                                    .getContextClassLoader();

        System.out.println(loader);// AppClassLoader 应用类加载器
        System.out.println(loader.getParent());// ExtClassLoader 扩展类加载器
        System.out.println(loader.getParent().getParent());//null  BootStrap ClassLoader 启动加载器


    }
}
