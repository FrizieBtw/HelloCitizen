package controller;

import entity.Resident;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courier")
class CourierServiceController {

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody Resident resident) {
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Email sended to : %s", resident.getEmail()));
    }

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody String email) {
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Email sended to : %s", email));
    }
}
