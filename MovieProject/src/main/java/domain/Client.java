package domain;

import javax.persistence.Entity;

@Entity
public class Client extends BaseEntity<Integer> {
    private String name;
    private int age;

    public Client(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Client(int id, String name, int age) {
        this.name = name;
        this.age = age;
        this.setId(id);
    }

    public Client() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
