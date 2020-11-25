package Single;

//单例模式 之 懒汉式
//
//
//
public class LazyDemo {

    private static LazyDemo lazyDemo = null;

    private LazyDemo(){}

    public  static LazyDemo getLazyDemo(){
        if (lazyDemo == null){
            synchronized (LazyDemo.class){
                if (lazyDemo == null){
                    return new LazyDemo();
                }
            }

        }

        return lazyDemo;

    }
}
