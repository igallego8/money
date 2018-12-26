package com.gallego.money.boundery;


import com.gallego.money.hex.model.credit.PayCredit;
import com.gallego.money.hex.model.credit.vo.PayCreditRequest;
import com.gallego.money.model.PayRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@CrossOrigin(origins = "http://localhost:7772")
@Controller
@RequestMapping("/pay")
public class PayController {

    PayCredit payCredit;

    @PostConstruct
    public  void  init(){
        payCredit = new PayCredit();
    }

    @PostMapping
    public ResponseEntity pay(@RequestBody PayRequest request){
        PayCreditRequest r = new PayCreditRequest(request.creditId, request.amount);
        payCredit.execute(r);
        return  ResponseEntity.ok().build();
    }
}
