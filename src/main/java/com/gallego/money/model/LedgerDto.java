package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LedgerDto {
    public Long creditId;
    public List<BigDecimal> entries = new ArrayList<>();
}
