package Single;

//单例模式 之 饿汉式
public class HungerDemo {

    private static HungerDemo hungerDemo = new HungerDemo();

    //构造器私有化
    private HungerDemo(){

    };

    //通过类名调 静态方法 得到对象
    public static HungerDemo getHungerDemo(){
        return hungerDemo;
    }

}
