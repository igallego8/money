package com.gallego.money.boundery;

import com.gallego.money.checkout.DeferService;
import com.gallego.money.entity.Context;
import com.gallego.money.entity.Credit;
import com.gallego.money.model.CreditDto;
import com.gallego.money.model.CreditReferRequest;
import com.gallego.money.model.CreditRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:7772")
@Controller
@RequestMapping("/credits")
public class CreditController {

    @PostMapping
    public ResponseEntity create(@RequestBody CreditRequest request){
        Credit credit = new Credit(request.amount, request.interest);
        Context.gateway.persist(credit);
        return ResponseEntity.ok(credit);

    }

    @GetMapping
    public  ResponseEntity fetch(){
        List<Credit> credits= Context.gateway.fetchCredits();
        List<CreditDto> creditsDto = new ArrayList<>();
        credits.stream().forEach(c -> {
            CreditDto dto = new CreditDto();
            dto.debt = c.getDebt();
            dto.nextPayment = Context.gateway.getProductsBy(c.getId()).getNextTotalCharge();
            dto.id = c.getId();
            dto.interest = c.getInterest();
            creditsDto.add(dto);
        });

        return ResponseEntity.ok(creditsDto);
    }

    @PostMapping("/{creditId}/refer")
    public ResponseEntity referCredit(@PathVariable ("creditId") Long creditId,@RequestBody CreditReferRequest request){
        DeferService deferService = new DeferService();
        deferService.defer(creditId,request.interest,request.shares);
        return ResponseEntity.ok().build();
    }
}
