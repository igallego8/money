package com.gallego.money.integration;

import com.gallego.money.entity.*;

import java.util.ArrayList;
import java.util.Iterator;
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


    public MockGateway (){
        System.out.println(" Creating new MockGateway");
    }

    @Override
    public Ledger getLedger(Long id) {
        return ledgersPersisted.stream().filter(l -> l.getAssetId().equals(id)).findFirst().orElseThrow(()->new RegistryNotFoundException("Ledger not found for credit " + id));
    }

    @Override
    public Credit getCreditCardBy(Long id) {
        System.out.println(" Credit Cards list size :: " + credits.size());
        credits.forEach(c -> System.out.println("Credit Card :: "+ c.getId()));
        return credits.stream().filter(c-> c.getId().equals(id)).findFirst().orElseThrow(()->new RegistryNotFoundException("Credit not found"));
    }

    @Override
    public void persist(Products products) {
        Iterator<Product> it = products.iterator();
        while(it.hasNext()){
            Product p = it.next();
            generateId(p);
            productsPersisted.add(p);
        }
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
        credits.add(credit);
    }

    @Override
    public void delete(Products products) {
        Iterator<Product> it = products.iterator();
        it.forEachRemaining(p-> productsPersisted.remove(p));
    }

    @Override
    public Products getProductsBy(Long creditId) {
      return  new Products(productsPersisted.stream().filter(p -> p.getCreditId().equals(creditId)).collect(Collectors.toList()));
    }

    @Override
    public List<Credit> fetchCredits() {
        return credits;
    }

    @Override
    public Products fetchProducts() {
        return  new Products(productsPersisted.stream().collect(Collectors.toList()));
    }

    @Override
    public void persist(User user) {
        generateId(user);
        usersPersisted.add(user);
    }

    @Override
    public User findUserBy(String userName) {
        return usersPersisted.stream().filter(u -> userName.equals(u.getName())).findFirst().orElseThrow(() -> {
            throw new RegistryNotFoundException("User not found");
        });
    }

    @Override
    public User findUserBy(Long userId) {
        return usersPersisted.stream().filter(u -> u.getId().equals(userId)).findFirst().orElseThrow(()->new RegistryNotFoundException("User not found"));
    }

    @Override
    public AppFunction findAppFunctionBy(String name) {
        return functions.stream().filter(f-> name.equals(f.getName())).findFirst().orElseThrow(()-> new RegistryNotFoundException("Function not found"));
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
    public void update(Products products) {
        Iterator<Product> it = products.iterator();
        while(it.hasNext()){
            Product product = it.next();
            if(product.hasDebt()){
                Product productToReplace = productsPersisted.stream().filter(p -> p.getCreditId().equals(product.getCreditId())).findFirst().orElseThrow(()-> new RegistryNotFoundException("Function not found"));
                productsPersisted.remove(productToReplace);
                productsPersisted.add(product);
            }else{
                productsPersisted.remove(product);
            }

        }
    }

    @Override
    public List<Ledger> fetchLedger() {
        return ledgersPersisted;
    }

    private void generateId(Entity entity) {
       if(entity.getId() == null){
           entity.setId(UUID.randomUUID().getLeastSignificantBits());
        }
    }
}
