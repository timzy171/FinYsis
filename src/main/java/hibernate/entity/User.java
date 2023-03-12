package hibernate.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name = "name")
    private String name;


    public User() {

    }

    public User(int id, String name, String surname, String email, String password) {
        this.user_id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + user_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "password")
    private String password;

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }

    @OneToMany(cascade = CascadeType.ALL,
    mappedBy = "user",fetch = FetchType.LAZY)
    private List<Good> goodList;

    public void addGoodToUser(Good good){
        if(goodList == null){
            goodList = new ArrayList<>();
        }
        goodList.add(good);
        good.setUser(this);
    }


    public List<Good> getGoodList() {
        if(goodList == null){
            goodList = new ArrayList<>();
        }
        return goodList;
    }

    public void setGoodList(List<Good> goodList) {
        this.goodList = goodList;
    }
}
