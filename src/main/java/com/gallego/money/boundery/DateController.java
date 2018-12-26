package com.gallego.money.boundery;


import com.gallego.money.util.TimeContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@CrossOrigin(origins = "http://localhost:7772")
@Controller
@RequestMapping("/date")
public class DateController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");

    @PostConstruct
    public  void  init(){

    }

    @PostMapping
    public ResponseEntity changeDate(@RequestBody DateChangeRequest request){
        LocalDate localDate = LocalDate.parse(request.date,formatter);
        TimeContext.timeHandler.setDate(localDate);
        return ResponseEntity.ok().build();

    }
}
