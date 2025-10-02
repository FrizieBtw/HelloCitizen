package service;
import dao.GiftDao;
import entity.Gift;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftService
{
    private final GiftDao giftDao;

    @Autowired
    public GiftService(GiftDao giftDao) {
        this.giftDao = giftDao;
    }

    public List<Gift> findAll() {
        return giftDao.findAll();
    }

    public Gift findById(Long id)
    {
        return giftDao.findById(id);
    }

    public Gift create(Gift gift)
    {
        return giftDao.save(gift);
    }

    public boolean delete(Long id)
    {
        return giftDao.delete(id);
    }

    public Gift update(Long id, Gift giftDetails) {
        Gift gift = giftDao.findById(id);
        if (gift == null) return null;

        if (giftDetails.getAgeMin() != null)
            gift.setAgeMin(giftDetails.getAgeMin());
        if (giftDetails.getAgeMax() != null)
            gift.setAgeMax(giftDetails.getAgeMax());
        if (giftDetails.getPrice() != null)
            gift.setPrice(giftDetails.getPrice());
        if (giftDetails.getCodeBarres() != null)
            gift.setCodeBarres(giftDetails.getCodeBarres());

        return giftDao.save(gift);
    }
}
