package money;

import com.gallego.money.hex.model.entity.Entity;

import java.math.BigDecimal;

public class SavingAccount extends Entity {

    private BigDecimal amount;

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void addAmount(BigDecimal depositAmount) {
        amount = amount.add(depositAmount);
    }
}
