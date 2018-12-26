package com.gallego.money.integration;

import com.gallego.money.hex.model.entity.*;
import com.gallego.money.exception.RegistryNotFoundException;
import money.SavingAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MockGateway implements Gateway {

    List<Credit> credits = new ArrayList<>();
    List<Product> productsPersisted = new ArrayList<>();
    List<Transac> transactsPersisted = new ArrayList<>();
    List<User> usersPersisted = new ArrayList<>();
    List<Ledger> ledgersPersisted = new ArrayList<>();
    List<AppFunction> functions = new ArrayList<>();
    List<License> licenses = new ArrayList<>();
    private List<SavingAccount> savingAccounts= Collections.synchronizedList(new ArrayList<SavingAccount>());


    public MockGateway (){
        System.out.println(" Creating new MockGateway");
    }

    @Override
    public Ledger getLedger(Long id) {
        return ledgersPersisted.stream().filter(l -> l.getAssetId().equals(id)).findFirst().orElseThrow(()->new RegistryNotFoundException("Ledger not found for credit " + id));
    }

    @Override
    public Credit findCreditBy(Long id) {
        System.out.println(" Credit Cards list size :: " + credits.size());
        credits.forEach(c -> System.out.println("Credit Card :: "+ c.getId()));
        Credit credit = credits.stream().filter(c-> c.getId().equals(id)).findFirst().orElseThrow(()->new RegistryNotFoundException("Credit not found"));
        return credit;
    }

    @Override
    public void persist(Product product) {
        generateId(product);
        productsPersisted.add(product);

    }

    @Override
    public void persist(Transac transac) {
        transactsPersisted.add(transac);
    }

    @Override
    public void persist(Ledger ledger) {
        ledgersPersisted.add(ledger);
    }

    @Override
    public void persist(Credit credit) {
        generateId(credit);
        credits.add(credit);
        credit.getProducts().forEach(this::persist);
    }

    @Override
    public void delete(Product product) {
        productsPersisted.remove(product);
    }

    @Override
    public List<Product> getProductsBy(Long creditId) {
      return  productsPersisted.stream().filter(p -> p.getCreditId().equals(creditId)).collect(Collectors.toList());
    }

    @Override
    public List<Credit> fetchCredits() {
        return credits;
    }

    @Override
    public List<Product> fetchProducts() {
        return  productsPersisted;
    }

    @Override
    public void persist(User user) {
        generateId(user);
        usersPersisted.add(user);
    }

    @Override
    public User findUserBy(String userName) {
        return usersPersisted.stream().filter(u -> userName.equals(u.getName())).findFirst().get();//.orElseThrow(() -> {
          //  throw new RegistryNotFoundException("User not found");
        //});
        }

    @Override
    public User findUserBy(Long userId) {
        return usersPersisted.stream().filter(u -> u.getId().equals(userId)).findFirst().get();//.orElseThrow(() -> new RegistryNotFoundException("User not found"));
    }

    @Override
    public AppFunction findAppFunctionBy(String name) {
        return functions.stream().filter(f -> name.equals(f.getName())).findFirst().get();//.orElseThrow(()-> new RegistryNotFoundException("Function not found"));
    }

    @Override
    public void persist(AppFunction function) {
        functions.add(function);
    }

    @Override
    public List<License> findLicensesByUserAndFunction(User user, AppFunction function) {
        List<License> result = new ArrayList<>();
        licenses.stream().filter(l ->
            l.getUser().isSame(user) && l.getFunction().isSame(function)
        ).forEach(result::add);

        return result;
    }

    @Override
    public void persist(License license) {
        licenses.add(license);
    }

    @Override
    public void update(Product product) {
       if ( productsPersisted.stream().anyMatch(p-> p.getId().equals(product.getId()))) {
            if (product.hasDebt()) {
                Product productToReplace = productsPersisted.stream().filter(p -> p.getCreditId().equals(product.getCreditId())).findFirst().get();//orElseThrow(()-> new RegistryNotFoundException("Function not found"));
                productsPersisted.remove(productToReplace);
                productsPersisted.add(product);
            } else {
                productsPersisted.remove(product);
            }
        }else{
           this.persist(product);
        }

    }

    @Override
    public List<Ledger> fetchLedger() {
        return ledgersPersisted;
    }

    @Override
    public void persist(SavingAccount savingAccount) {
        generateId(savingAccount);
        savingAccounts.add(savingAccount);
    }

    @Override
    public SavingAccount getSavingAccount(Long savingAccountId) {
       return savingAccounts.stream().filter(s-> s.getId().equals(savingAccountId)).findFirst().get();
    }

    @Override
    public void update(SavingAccount savingAccount) {
       savingAccounts.stream().filter(a-> a.getId().equals(savingAccount.getId())).forEach(a->a.setAmount(savingAccount.getAmount()));
    }

    @Override
    public void update(Credit credit) {
        credits.stream().filter(c-> c.getId().equals(credit.getId())).forEach(c->{
            c.setDebt(credit.getDebt());
            c.setCutoffDay(credit.getCutoffDay());
            c.setInterest(credit.getInterest());
            credit.getProducts().forEach(this::update);
            });

    }

    private void generateId(Entity entity) {
       if(entity.getId() == null){
           entity.setId(UUID.randomUUID().getMostSignificantBits()/1000);
        }
    }
}
