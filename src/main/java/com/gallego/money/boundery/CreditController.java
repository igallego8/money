package com.gallego.money.boundery;

import com.gallego.money.hex.model.credit.PurchaseCredit;
import com.gallego.money.hex.model.credit.vo.PurchaseCreditRequest;
import com.gallego.money.hex.model.credit.ReferCredit;
import com.gallego.money.hex.model.credit.vo.ReferCreditRequest;
import com.gallego.money.util.Context;
import com.gallego.money.hex.model.entity.Credit;
import com.gallego.money.hex.model.entity.Product;
import com.gallego.money.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@CrossOrigin(origins = "http://localhost:7772")
@Controller
@RequestMapping("/credits")
public class CreditController {

    @PostMapping
    public ResponseEntity create(@RequestBody CreditRequest request){
        Credit credit = new Credit(request.amount, BigDecimal.ZERO, request.interest,request.cutoffDay);
        Context.gateway.persist(credit);
        return ResponseEntity.ok(credit);

    }

    @GetMapping
    public  ResponseEntity fetch(){
        List<Credit> credits= Context.gateway.fetchCredits();
        List<CreditDto> creditsDto = new ArrayList<>();
        NextPaymentChargeQuery nextPaymentChargeQuery = new NextPaymentChargeQuery();

        Map<Long, BigDecimal> interestHandlerMap = new HashMap<>();
        credits.forEach(c-> interestHandlerMap.put(c.getId(),nextPaymentChargeQuery.query(c.getId())) );
        credits.forEach(c -> {
            CreditDto dto = new CreditDto();
            dto.debt = c.getDebt();
            dto.nextPayment = interestHandlerMap.get(c.getId());
            dto.id = c.getId();
            dto.interest = c.getInterest();
            dto.cutoffDay = c.getCutoffDay();
            creditsDto.add(dto);
        });

        return ResponseEntity.ok(creditsDto);
    }

    @PostMapping("/{creditId}/refer")
    public ResponseEntity referCredit(@PathVariable ("creditId") Long creditId,@RequestBody CreditReferRequest request){
        new ReferCredit().execute(new ReferCreditRequest(creditId,request.shares,request.interest));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{creditId}/purchase")
    public ResponseEntity purchaseCredit(@PathVariable ("creditId") Long creditId,@RequestBody CreditPurchaseRequest request){
        new PurchaseCredit().execute(new PurchaseCreditRequest(creditId,request.amount, request.cutoffDay,request.interest,request.shares));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{creditId}/products")
    public ResponseEntity creditProducts(@PathVariable ("creditId") Long creditId){
        CreditProductSearchService creditProductSearchService = new CreditProductSearchService();
        List<Product> it = creditProductSearchService.search(creditId);
        List<ProductDto> dtoProducts = new ArrayList<>();
        for (Product p : it){
            ProductDto dto = new ProductDto();
            dto.debt = p.getDebt();
            dto.shares = p.getShares() - p.getSharesPaid();
            dto.creditId = p.getCreditId();
            dto.description = p.getDescription();
            dto.interest = p.getInterest();
            dtoProducts.add(dto);
        }
        return ResponseEntity.ok(dtoProducts);
    }
}
