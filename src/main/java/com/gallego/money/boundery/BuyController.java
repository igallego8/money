package com.gallego.money.boundery;


import com.gallego.money.checkout.CheckoutService;
import com.gallego.money.checkout.DefaultCheckoutProcess;
import com.gallego.money.entity.Context;
import com.gallego.money.entity.Credit;
import com.gallego.money.entity.Products;
import com.gallego.money.integration.MockGateway;
import com.gallego.money.model.BuyCreditRequest;
import com.gallego.money.model.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:7772")
@Controller
@RequestMapping("/buy")
public class BuyController {

    CheckoutService checkoutService = new CheckoutService(new DefaultCheckoutProcess());

    @PostConstruct
    public void init(){
        Context.gateway = new MockGateway();
        Context.gateway.persist(new Credit(BigDecimal.valueOf(30000),BigDecimal.ZERO));
    }

    @GetMapping("/creditCards")
    public ResponseEntity getCreditCard(){
        return ResponseEntity.ok(Context.gateway.fetchCredits());
    }


    @PostMapping("/cash")
    public ResponseEntity buy(@RequestBody List<ProductDto> items){
        List<com.gallego.money.entity.Product> products = new ArrayList<>();
        items.forEach(i-> products.add(new com.gallego.money.entity.Product(i.amount)));
        checkoutService.payWithCash(new Products(products), 0L);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/credit")
    public ResponseEntity buyByCredit(@RequestBody BuyCreditRequest request){
        checkoutService.payWithCredit(request);
        return ResponseEntity.ok().build();
    }
}
