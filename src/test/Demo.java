package test;

/**
 * Created by wang on 2016/12/20.
 */
public class Demo {
    private String name;
    private Integer age;
    private String tel;

    public Demo(String name, Integer age, String tel) {
        this.age = age;
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
