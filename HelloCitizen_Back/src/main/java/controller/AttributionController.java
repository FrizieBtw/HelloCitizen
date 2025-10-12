package controller;

import entity.Attribution;
import entity.Gift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AttributionService;
import service.ResidentService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/attributions")
class AttributionController {

    private final AttributionService attributionService;

    @Autowired
    AttributionController(AttributionService attributionService, ResidentService residentService) {
        this.attributionService = attributionService;
    }

    @GetMapping
    public List<Attribution> getAllAttributions() {
        return attributionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attribution> getResidentById(@PathVariable Long id) {
        Attribution attribution = attributionService.findById(id);
        return attribution == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(attribution);
    }

    @PostMapping("/resident/{id}")
    public ResponseEntity<String> addProposedAttribution(@PathVariable Long id) {
        String mail = attributionService.addProposedAttribution(id);
        return mail == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(mail);
    }

    @PutMapping
    public ResponseEntity<Attribution> updateAttribution(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(attributionService.updateAttribution(payload));
    }
}
