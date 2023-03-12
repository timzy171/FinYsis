package hibernate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "goods")
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private double cost;

    @Column(name = "type")
    private String type;


    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;


    public Good() {
    }

    public Good(String name, double cost, String type, User user) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", type='" + type + '\'' +
                ", user=" + user +
                '}';
    }
}
