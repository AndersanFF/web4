package DAO;

import ResourcesForServlets.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

public class UserFromBD {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addUser(Users user) {
        em.persist(user);
    }
    @Transactional
    public Users checkUser(String username) {
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.username = :username", Users.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Transactional
    public void updateUser(Users user) {
        Users  detachedUser  = em.find(Users.class, 1L);
        em.detach(detachedUser );
    }
}
