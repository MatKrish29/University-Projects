package sample;

public class Over60Member extends DefaultMember {
    private int age;

    //Setter
    public void setAge(int age) {

        this.age = age;
    }

    //Getter
    public int getAge() {

        return age;
    }

    //Displaying the details in the console
    public void getDetails() {
        getDetailsDefault();
        System.out.println("Age                   : " + age);
    }
}
