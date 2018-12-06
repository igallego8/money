package com.gallego.money.boundery;

import com.gallego.money.entity.Context;
import com.gallego.money.entity.Product;
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
        return ResponseEntity.ok(Context.gateway.getProductsBy(creditId).iterator());
    }

    @GetMapping
    public ResponseEntity getProducts(){
        Iterator<Product> it = Context.gateway.fetchProducts().iterator();
        Map<Long, List<ProductDto>> map = new HashMap<>();
        List<ProductDto> products;
        while (it.hasNext()){

            Product p = it.next();
            if (!map.containsKey(p.getCreditId())){
                products = new ArrayList<>();
                map.put(p.getCreditId(), products);
            }else{
                products = map.get(p.getCreditId());
            }

            ProductDto dto = new ProductDto();
            dto.debt = p.getDebt();
            dto.shares = p.getShares() - p.getSharesPaid();
            dto.creditId = p.getCreditId();
            dto.description = p.getDescription();
            dto.interest = p.getInterest();
            products.add(dto);
        }

        return ResponseEntity.ok(map.values());
    }


    @GetMapping("/{creditId}/charge")
    public ResponseEntity getNextCharge(@PathVariable Long creditId){
        return ResponseEntity.ok(Context.gateway.getProductsBy(creditId).getNextTotalCharge());
    }

    @GetMapping("/{creditId}/ledger")
    public ResponseEntity getLedger(@PathVariable Long creditId){
        return ResponseEntity.ok(Context.gateway.getLedger(creditId));
    }


}
