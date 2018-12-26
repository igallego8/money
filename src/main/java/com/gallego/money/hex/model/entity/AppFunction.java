package com.gallego.money.hex.model.entity;

public class AppFunction extends Entity {

    private String name;

    public AppFunction (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSame(Entity entity) {
        if(entity instanceof  AppFunction){
      return  name.equals(((AppFunction) entity).getName());
        }else{
            return false;
        }
    }
}
