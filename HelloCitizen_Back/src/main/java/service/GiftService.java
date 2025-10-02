package service;
import dao.GiftDao;
import entity.Gift;

import java.util.List;

public class GiftService
{
    private final GiftDao giftDao = new GiftDao();

    public List<Gift> getAllGifts() {
        return giftDao.findAll();
    }

    public Gift findById(Long id)
    {
        return giftDao.findById(id);
    }

    public void Create(Gift gift)
    {
        giftDao.save(gift);
    }

    public void Delete(Gift gift)
    {
        giftDao.delete(gift.getId());
    }
}
