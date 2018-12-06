package com.gallego.money.entity;

public class Entity {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSame(Entity entity) {
       return id != null && id.equals(entity.getId());
    }
}
