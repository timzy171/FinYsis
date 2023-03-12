package hibernate;

import com.example.finysis.HelloController;
import org.hibernate.Session;
import org.hibernate.jpa.internal.HintsCollector;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.close();
        HibernateUtil.close();


    }
}
