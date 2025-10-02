package controller;

import entity.Gift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.GiftService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/gifts")
public class GiftController {

    private final GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @GetMapping
    public List<Gift> getAllGifts() {
        return giftService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gift> getGiftById(@PathVariable Long id) {
        Gift gift = giftService.findById(id);
        if (gift != null) {
            return ResponseEntity.ok(gift);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Gift> createGift(@RequestBody Gift gift) {
        Gift created = giftService.create(gift);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gift> updateGift(@PathVariable Long id, @RequestBody Gift giftDetails) {
        Gift updated = giftService.update(id, giftDetails);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGift(@PathVariable Long id) {
        if (giftService.delete(id)) {
            return ResponseEntity.ok("Gift deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}