package service;

import dao.AttributionDao;
import dao.ResidentDao;
import entity.Attribution;
import entity.AttributionStatus;
import entity.Gift;
import entity.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AttributionService {

    private final AttributionDao attributionDao;
    private final ResidentDao residentDao;
    private final GiftService giftService;

    @Autowired
    public AttributionService(AttributionDao attributionDao, ResidentDao residentDao, GiftService giftService) {
        this.attributionDao = attributionDao;
        this.residentDao = residentDao;
        this.giftService = giftService;
    }

    public List<Attribution> findAll() {
        return attributionDao.findAll();
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
        
        resident.setNotificationDate(LocalDate.now());
        residentDao.save(resident);

        Attribution attribution = new Attribution();
        attribution.setResident(resident);
        attribution.setAttributionStatus(AttributionStatus.PROPOSED);
        attribution.setPropositionDate(LocalDate.now());
        attribution = save(attribution);
        return String.format(
                "<p>Bonjour %s %s,</p>" +
                        "<p>Félicitations pour votre première année d’habitation dans notre commune !</p>" +
                        "<p>Vous êtes éligible à un cadeau. Cliquez ici pour faire votre choix : " +
                        "<a href=\"http://localhost:3000/views/habitantChoix?resident=%s&attribution=%s\">Choisir mon cadeau</a></p>" +
                        "<p>Une fois choisi, vous pourrez indiquer votre adresse de livraison.</p>" +
                        "<p>Cordialement,<br>La Mairie d'Ussel</p>", attribution.getResident().getFirstName(), attribution.getResident().getLastName(), resident.getId(), attribution.getId().toString());
    }

    public Attribution updateAttribution(Map<String, Object> payload) throws IllegalArgumentException {
        try {
            Long residentId = Long.valueOf(payload.get("residentId").toString());
            Long giftId = Long.valueOf(payload.get("giftId").toString());
            String email = payload.get("email").toString();
            String deliveryAddress = payload.get("deliveryAddress").toString();

            Attribution attribution = findByResidentId(residentId);
            if (attribution == null) {
                throw new IllegalArgumentException("Aucune attribution trouvée pour ce résident.");
            }

            Gift gift = giftService.findById(giftId);
            if (gift == null) {
                throw new IllegalArgumentException("Cadeau introuvable.");
            }

            attribution.setGift(gift);
            attribution.setShippingAddress(deliveryAddress);
            attribution.setAttributionStatus(AttributionStatus.CHOOSEN);
            attribution.setChoiceDate(LocalDate.now());
            attribution.setTotalPrice(gift.getPrice());

            return save(attribution);

        } catch (Exception e) {
            throw new IllegalArgumentException("Erreur lors de la mise à jour de l'attribution : " + e.getMessage(), e);
        }
    }
}
