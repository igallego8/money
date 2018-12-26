package com.gallego.money.boundery;


import com.gallego.money.hex.model.credit.CheckoutCredit;
import com.gallego.money.hex.model.credit.vo.CheckoutCreditRequest;
import com.gallego.money.util.Context;
import com.gallego.money.hex.model.entity.Credit;
import com.gallego.money.integration.MockGateway;
import com.gallego.money.model.BuyCreditRequest;
import com.gallego.money.util.TimeContext;
import com.gallego.money.util.date.DefaultTimeHandler;
import money.DailyTaskService;
import money.DepositTask;
import money.DepositTaskParam;
import money.SavingAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;


@CrossOrigin(origins = "http://localhost:7772")
@Controller
@RequestMapping("/buy")
public class BuyController {


    CheckoutCredit checkoutCredit = new CheckoutCredit();

    @PostConstruct
    public void init(){
        Context.gateway = new MockGateway();
        Credit credit = new Credit(BigDecimal.valueOf(30000), BigDecimal.ZERO, 0.0f, 1);
        Context.gateway.persist(credit);
        TimeContext.timeHandler = new DefaultTimeHandler(LocalDate.now());

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAmount(BigDecimal.ZERO);
        Context.gateway.persist(savingAccount);

        DepositTask task = new DepositTask();
        task.setTaskParams(Arrays.asList(new DepositTaskParam(savingAccount.getId(), BigDecimal.valueOf(100L))));
        DailyTaskService periodicTaskService =  new DailyTaskService(Arrays.asList(task));
        TimeContext.timeHandler.subscribe(periodicTaskService);
    }

    @GetMapping("/creditCards")
    public ResponseEntity getCreditCard(){
        return ResponseEntity.ok(Context.gateway.fetchCredits());
    }


    @PostMapping("/credit")
    public ResponseEntity buyByCredit(@RequestBody BuyCreditRequest request){
        CheckoutCreditRequest r = new CheckoutCreditRequest(request.amount, request.description, request.creditId, request.shares);
        checkoutCredit.execute(r);
      //  checkoutService.payWithCredit(request);
        return ResponseEntity.ok().build();
    }
}
