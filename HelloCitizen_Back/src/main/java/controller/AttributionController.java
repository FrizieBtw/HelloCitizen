package controller;

import entity.Attribution;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.AttributionService;

@RestController
@RequestMapping("/api/attribution")
class AttributionController {

    private final AttributionService attributionService;

    AttributionController(AttributionService attributionService) {
        this.attributionService = attributionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attribution> getResidentById(@PathVariable Long id) {
        Attribution attribution = attributionService.findById(id);
        if (attribution != null) {
            return ResponseEntity.ok(attribution);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
