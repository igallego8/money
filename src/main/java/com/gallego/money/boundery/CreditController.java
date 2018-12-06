package com.gallego.money.boundery;

import com.gallego.money.PurchaseCreditService;
import com.gallego.money.checkout.ReferService;
import com.gallego.money.entity.Context;
import com.gallego.money.entity.Credit;
import com.gallego.money.entity.Product;
import com.gallego.money.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
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
        credits.forEach(c -> {
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
        ReferService referService = new ReferService();
        request.creditId = creditId;
        referService.defer(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{creditId}/purchase")
    public ResponseEntity purchaseCredit(@PathVariable ("creditId") Long creditId,@RequestBody CreditPurchaseRequest request){
        PurchaseCreditService purchaseCreditService = new PurchaseCreditService();
        purchaseCreditService.purchase(creditId,request.amount, request.shares,request.interest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{creditId}/products")
    public ResponseEntity creditProducts(@PathVariable ("creditId") Long creditId){
        CreditProductSearchService creditProductSearchService = new CreditProductSearchService();
        Iterator<Product> it = creditProductSearchService.search(creditId).iterator();
        List<ProductDto> products = new ArrayList<>();
        while (it.hasNext()){
            Product p = it.next();
            ProductDto dto = new ProductDto();
            dto.debt = p.getDebt();
            dto.shares = p.getShares() - p.getSharesPaid();
            dto.creditId = p.getCreditId();
            dto.description = p.getDescription();
            dto.interest = p.getInterest();
            products.add(dto);
        }
        return ResponseEntity.ok(products);
    }
}
