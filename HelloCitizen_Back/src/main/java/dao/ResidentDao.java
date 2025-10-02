package dao;

import entity.Resident;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ResidentDao {

    @PersistenceContext
    private EntityManager em;

    public List<Resident> findAll() {
        return em.createQuery("SELECT r FROM Resident r", Resident.class)
                .getResultList();
    }

    public Resident findById(Long id) {
        return em.find(Resident.class, id);
    }

    public Resident save(Resident resident) {
        if (resident.getId() == null) {
            em.persist(resident);
            return resident;
        } else {
            return em.merge(resident);
        }
    }

    public boolean delete(Long id) {
        Resident r = em.find(Resident.class, id);
        if (r != null) {
            em.remove(r);
            return true;
        }
        return false;
    }
}
