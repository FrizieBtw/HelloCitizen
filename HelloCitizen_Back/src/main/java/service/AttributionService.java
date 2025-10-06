package service;

import dao.AttributionDao;
import dao.ResidentDao;
import entity.Attribution;
import entity.AttributionStatus;
import entity.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public String addProposedAttribution(Long residentId) {
        Resident resident = residentDao.findById(residentId);
        if (resident == null || findByResidentId(residentId) != null) {
            return null;
        }
        Attribution attribution = new Attribution();
        attribution.setResident(resident);
        attribution.setAttributionStatus(AttributionStatus.PROPOSED);
        attribution.setPropositionDate(LocalDate.now());
        attribution = save(attribution);
        return String.format(
            "<p>Bonjour %s %s,</p>" +
            "<p>Félicitations pour votre première année d’habitation dans notre commune !</p>" +
            "<p>Vous êtes éligible à un cadeau. Cliquez ici pour faire votre choix : " +
            "<a href=\"http://localhost:3000/views/habitantChoix?attribution=%s\">Choisir mon cadeau</a></p>" +
            "<p>Une fois choisi, vous pourrez indiquer votre adresse de livraison.</p>" +
            "<p>Cordialement,<br>La Mairie d'Ussel</p>", attribution.getResident().getFirstName(), attribution.getResident().getLastName(), attribution.getId().toString());
    }
}
