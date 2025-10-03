package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attribution")
class AttributionController {

    private final AttributionService attributionService;

    AttributionController(AttributionService attributionService) {
        this.attributionService = attributionService;
    }
}
