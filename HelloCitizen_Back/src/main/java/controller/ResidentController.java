package controller;

import dao.ResidentDao;
import entity.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    @Autowired
    private ResidentDao residentDao;

    // GET
    @GetMapping
    public List<Resident> getAllResidents() {
        return residentDao.findAll();
    }

    // GET{id}
    @GetMapping("/{id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable Long id) {
        Resident resident = residentDao.findById(id);
        if (resident != null) {
            return ResponseEntity.ok(resident);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST
    public ResponseEntity<Resident> createResident(@RequestBody Resident resident) {
        Resident created = residentDao.save(resident);
        return ResponseEntity.ok(created);
    }

    // PUT{id}
    @PutMapping("/{id}")
    public ResponseEntity<Resident> updateResident(@PathVariable Long id, @RequestBody Resident residentDetails) {
        Resident resident = residentDao.findById(id);
        if (resident == null) {
            return ResponseEntity.notFound().build();
        }
        resident.setFirstName(residentDetails.getFirstName());
        resident.setLastName(residentDetails.getLastName());
        resident.setBirthday(residentDetails.getBirthday());
        resident.setEmail(residentDetails.getEmail());
        resident.setNumber(residentDetails.getNumber());
        resident.setAddress(residentDetails.getAddress());
        resident.setArrivalDate(residentDetails.getArrivalDate());
        resident.setNotificationDate(residentDetails.getNotificationDate());

        Resident updated = residentDao.save(resident);
        return ResponseEntity.ok(updated);
    }

    // DELETE{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        residentDao.delete(id);
        return ResponseEntity.noContent().build();
    }

}