package dao;

import entity.Gift;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class GiftDao {

    @PersistenceContext
    private EntityManager em;

    public List<Gift> findAll() {
        return em.createQuery("SELECT r FROM Gift r", Gift.class)
                .getResultList();
    }

    public Gift findById(Long id) {
        return em.find(Gift.class, id);
    }

    public Gift save(Gift gift) {
        if (gift.getId() == null) {
            em.persist(gift);
            return gift;
        } else {
            return em.merge(gift);
        }
    }

    public boolean delete(Long id) {
        Gift g = em.find(Gift.class, id);
        if (g != null) {
            em.remove(g);
            return true;
        }
        return false;
    }

    public List<Gift> findByAge(int age) {
        return em.createQuery(
                        "SELECT g FROM Gift g WHERE g.ageMin <= :age AND g.ageMax >= :age",
                        Gift.class
                )
                .setParameter("age", age)
                .getResultList();
    }

}