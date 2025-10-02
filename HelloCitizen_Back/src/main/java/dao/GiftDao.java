package dao;

import entity.Gift;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

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

    public void save(Gift gift) {
        if (gift.getId() == null) {
            em.persist(gift);
        } else {
            em.merge(gift);
        }
    }

    public void delete(Long id) {
        Gift r = em.find(Gift.class, id);
        if (r != null) {
            em.remove(r);
        }
    }
}