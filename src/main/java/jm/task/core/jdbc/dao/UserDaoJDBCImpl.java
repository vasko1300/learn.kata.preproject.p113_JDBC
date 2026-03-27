package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS USERS (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50),
                last_name VARCHAR(50),
                age INT)
                """;
        EntityManager em = Util.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery(sql).executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USERS";
        EntityManager em = Util.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery(sql).executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        EntityManager em = Util.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = new User(name, lastName, age);
            em.persist(user);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        EntityManager em = Util.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        String jpql = "SELECT u FROM User u";
        EntityManager em = Util.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(jpql, User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void cleanUsersTable() {
        String jpql = "DELETE FROM User";
        EntityManager em = Util.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery(jpql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
