package com.gallego.money.boundery;

import com.gallego.money.hex.model.entity.Credit;
import com.gallego.money.util.Context;
import com.gallego.money.hex.model.entity.Product;
import com.gallego.money.model.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;


@CrossOrigin(origins = "http://localhost:7772")
@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/{creditId}")
    public ResponseEntity getProducts(@PathVariable Long creditId){
        return ResponseEntity.ok(Context.gateway.findCreditBy(creditId).getProducts());
    }

    @GetMapping
    public ResponseEntity getProducts(){
        List<Credit> credits = Context.gateway.fetchCredits();
        Map<Long, List<ProductDto>> map = new HashMap<>();

        credits.forEach(c->  {
            if (!map.containsKey(c.getId())){
                map.put(c.getId(), new ArrayList<>());
            }
            for (Product p : c.getProducts()){
                ProductDto dto = new ProductDto();
                dto.debt = p.getDebt();
                dto.shares = p.getShares() - p.getSharesPaid();
                dto.creditId = p.getCreditId();
                dto.description = p.getDescription();
                dto.interest = p.getInterest();
                map.get(c.getId()).add(dto);
            }
        });
        return ResponseEntity.ok(map.values());
    }


    @GetMapping("/{creditId}/charge")
    public ResponseEntity getNextCharge(@PathVariable Long creditId){
        return ResponseEntity.ok(new NextPaymentChargeQuery().query(creditId));
    }

    @GetMapping("/{creditId}/ledger")
    public ResponseEntity getLedger(@PathVariable Long creditId){
        return ResponseEntity.ok(Context.gateway.getLedger(creditId));
    }


}
