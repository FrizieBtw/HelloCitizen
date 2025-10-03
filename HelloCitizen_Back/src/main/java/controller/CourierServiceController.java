package controller;

import entity.Resident;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courier")
class CourierServiceController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendEmail(@RequestBody Resident resident) {
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Email sent to : %s", resident.getEmail()));
    }

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sendEmail(@RequestBody String email) {
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Email sent to : %s", email));
    }
}
