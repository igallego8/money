package com.gallego.money.boundery;

import com.gallego.money.util.Context;
import com.gallego.money.hex.model.entity.Ledger;
import com.gallego.money.model.LedgerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;


@CrossOrigin(origins = "http://localhost:7772")
@Controller
@RequestMapping("/ledgers")
public class LedgerController {


    @GetMapping
    public ResponseEntity getLedgers(){
        List<Ledger> ledgers = Context.gateway.fetchLedger();
        Map<Long, LedgerDto> map = new HashMap<>();
        LedgerDto dto;
        for (Ledger ledger:ledgers){
            if (!map.containsKey(ledger.getAssetId())){
                dto = new LedgerDto();
                dto.creditId = ledger.getAssetId();
                map.put(ledger.getAssetId(), dto);
            }else{
                dto = map.get(ledger.getAssetId());
            }
            dto.entries.addAll( ledger.getEntries());
        }
        return ResponseEntity.ok(map.values());
    }
}
