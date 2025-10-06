package controller;

import entity.Attribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AttributionService;
import service.ResidentService;

@RestController
@RequestMapping("/api/attributions")
class AttributionController {

    private final AttributionService attributionService;

    @Autowired
    AttributionController(AttributionService attributionService, ResidentService residentService) {
        this.attributionService = attributionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attribution> getResidentById(@PathVariable Long id) {
        Attribution attribution = attributionService.findById(id);
        return attribution == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(attribution);
    }

    @PostMapping("/resident/{id}")
    public ResponseEntity<Attribution> getAttributionByResident(@PathVariable Long residentId) {
        Attribution created = attributionService.addEmptyAttributionToResident(residentId);
        return created == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(created);
    }
}
