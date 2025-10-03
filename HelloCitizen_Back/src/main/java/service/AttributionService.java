package service;

import dao.AttributionDao;
import entity.Attribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributionService {

    private final AttributionDao attributionDao;

    @Autowired
    public AttributionService(AttributionDao attributionDao) {
        this.attributionDao = attributionDao;
    }

    public Attribution findById(Long id) {
        return attributionDao.findById(id);
    }
}
