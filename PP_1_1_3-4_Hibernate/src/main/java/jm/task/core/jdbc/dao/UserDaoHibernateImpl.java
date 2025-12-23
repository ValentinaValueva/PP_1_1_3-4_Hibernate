package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.hibernate.Transaction;

import java.util.List;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), lastName VARCHAR(255), age TINYINT)").executeUpdate();
            tx.commit();
            System.out.println("Таблица создана");
            session.close();
        } catch (HibernateException e) {
            LOGGER.error("Ошибка создания таблицы: " + e.getMessage());
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            tx.commit();
            System.out.println("Таблица удалена");
            session.close();
        } catch (HibernateException e) {
            LOGGER.error("Ошибка удаления таблицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tx.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            LOGGER.error("Ошибка при добавлении User " + e.getMessage());
        }
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            tx.commit();
            System.out.println("User c id " + id + " удален");
        } catch (Exception e) {
            LOGGER.error("Ошибка при удалении User " + e.getMessage());
        }
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        List<User> users = null;
        try {
            Transaction tx = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).getResultList();
            tx.commit();
            users.forEach(System.out::println);
        } catch (Exception e) {
            LOGGER.error("Ошибка при удалении User " + e.getMessage());
        }
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getInstance().getSessionFactory().getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            tx.commit();
            System.out.println("Все записи из таблицы users удалены.");
        } catch (Exception e) {
            LOGGER.error("Ошибка очистки таблицы " + e.getMessage());
        }
        session.close();

    }
}
