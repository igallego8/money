package com.gallego.money.boundery;


import com.gallego.money.model.PayRequest;
import com.gallego.money.payment.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@CrossOrigin(origins = "http://localhost:7772")
@Controller
@RequestMapping("/pay")
public class PayController {

    PaymentService paymentService;

    @PostConstruct
    public  void  init(){
        paymentService = new PaymentService();
    }

    @PostMapping
    public ResponseEntity pay(@RequestBody PayRequest request){
        paymentService.pay(request.creditId, request.amount);
        return  ResponseEntity.ok().build();
    }
}
