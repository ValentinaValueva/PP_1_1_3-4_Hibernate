package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import org.hibernate.Transaction;

import java.util.List;



public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), lastName VARCHAR(255), age TINYINT)").executeUpdate();
        tx.commit();
        System.out.println("Таблица создана");
    session.close();

    }
    @Override
    public void dropUsersTable() {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
        tx.commit();
        System.out.println("Таблица удалена");
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        tx.commit();
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        tx.commit();
        System.out.println("User c id " + id + " удален");
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<User> users = session.createQuery("FROM User").getResultList();
        tx.commit();
        users.forEach(System.out::println);
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        tx.commit();
        System.out.println("Таблица удалена");
        session.close();

    }
}
