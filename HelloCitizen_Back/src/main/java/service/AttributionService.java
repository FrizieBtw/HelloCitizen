package service;

import dao.AttributionDao;
import dao.ResidentDao;
import entity.Attribution;
import entity.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributionService {

    private final AttributionDao attributionDao;
    private final ResidentDao residentDao;

    @Autowired
    public AttributionService(AttributionDao attributionDao, ResidentDao residentDao) {
        this.attributionDao = attributionDao;
        this.residentDao = residentDao;
    }

    public Attribution findById(Long id) {
        return attributionDao.findById(id);
    }

    public Attribution findByResidentId(Long residentId) {
        Resident resident = residentDao.findById(residentId);
        if (resident == null) {
            return null;
        }
        return attributionDao.findByResidentId(residentId);
    }

    public Attribution save(Attribution attribution) {
        return attributionDao.save(attribution);
    }

    public Attribution addEmptyAttributionToResident(Long residentId) {
        Resident resident = residentDao.findById(residentId);
        if (resident == null || attributionDao.findByResidentId(residentId) == null) {
            return null;
        }
        Attribution attribution = new Attribution();
        attribution.setResident(resident);
        return attributionDao.save(attribution);
    }
}
