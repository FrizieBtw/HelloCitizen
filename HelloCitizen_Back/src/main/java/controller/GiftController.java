package controller;

import entity.Gift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.GiftService;

import java.util.List;

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
        return giftService.getAllGifts();
    }

    @PostMapping("/{create}")
    public Gift createGift(Gift gift)
    {
        giftService.Create(gift);
        return gift;
    }
}
