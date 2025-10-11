package service;

import dao.ResidentDao;
import entity.Gift;
import entity.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ResidentService {

    private final ResidentDao residentDao;
    private final GiftService giftService;

    @Autowired
    public ResidentService(ResidentDao residentDao, GiftService giftService) {
        this.residentDao = residentDao;
        this.giftService = giftService;
    }

    public List<Resident> findAll() {
        return residentDao.findAll();
    }

    public Resident findById(Long id) {
        return residentDao.findById(id);
    }

    public Resident create(Resident residentDetails) {
        residentDetails.setId(null);
        return residentDao.save(residentDetails);
    }

    public Resident update(Long id, Resident residentDetails) {
        Resident resident = residentDao.findById(id);
        if (resident == null) return null;

        if (residentDetails.getFirstName() != null)
            resident.setFirstName(residentDetails.getFirstName());
        if (residentDetails.getLastName() != null)
            resident.setLastName(residentDetails.getLastName());
        if (residentDetails.getBirthday() != null)
            resident.setBirthday(residentDetails.getBirthday());
        if (residentDetails.getEmail() != null)
            resident.setEmail(residentDetails.getEmail());
        if (residentDetails.getNumber() != null)
            resident.setNumber(residentDetails.getNumber());
        if (residentDetails.getAddress() != null)
            resident.setAddress(residentDetails.getAddress());
        if (residentDetails.getArrivalDate() != null)
            resident.setArrivalDate(residentDetails.getArrivalDate());
        if (residentDetails.getNotificationDate() != null)
            resident.setNotificationDate(residentDetails.getNotificationDate());

        return residentDao.save(resident);
    }

    public boolean delete(Long id) {
        return residentDao.delete(id);
    }

    public List<Gift> getGiftsByResidentId(Long id) {
        Resident resident = residentDao.findById(id);
        Date date = Date.valueOf(resident.getBirthday());
        return giftService.findByDate(date);
    }
}
