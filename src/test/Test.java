package test;

/**
 * Created by wang on 2016/12/7.
 */
public class Test {
    public static final ThreadLocal<String> holder = new ThreadLocal<String>();
    public static void main(String[] args) throws Exception {
        holder.set("1");
        holder.set("2");
        System.out.println(holder.get());
        System.out.println(holder.get());
    }
}

