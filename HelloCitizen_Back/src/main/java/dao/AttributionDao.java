package dao;

import entity.Attribution;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class AttributionDao {

    @PersistenceContext
    private EntityManager em;

    public List<Attribution> findAll() {
        return em.createQuery("SELECT r FROM Resident r", Attribution.class)
                .getResultList();
    }

    public Attribution findById(Long id) {
        return em.find(Attribution.class, id);
    }

    public Attribution save(Attribution attribution) {
        if (attribution.getId() == null) {
            em.persist(attribution);
            return attribution;
        } else {
            return em.merge(attribution);
        }
    }

    public boolean delete(Long id) {
        Attribution r = em.find(Attribution.class, id);
        if (r != null) {
            em.remove(r);
            return true;
        }
        return false;
    }
}
