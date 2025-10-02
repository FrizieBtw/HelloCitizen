package controller;

import entity.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ResidentService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/residents")
public class ResidentController {

    private final ResidentService residentService;

    @Autowired
    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }
    // GET
    @GetMapping
    public List<Resident> getAllResidents() {
        return residentService.findAll();
    }

    // GET{id}
    @GetMapping("/{id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable Long id) {
        Resident resident = residentService.findById(id);
        if (resident != null) {
            return ResponseEntity.ok(resident);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<Resident> createResident(@RequestBody Resident resident) {
        System.out.println(resident);
        Resident created = residentService.create(resident);
        return ResponseEntity.ok(created);
    }

    // PUT{id}
    @PutMapping("/{id}")
    public ResponseEntity<Resident> updateResident(@PathVariable Long id, @RequestBody Resident residentDetails) {
        Resident updated = residentService.update(id ,residentDetails);
        if(updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteResident(@PathVariable Long id) {
        if(residentService.delete(id)) {
            return ResponseEntity.ok("Resident deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }

}