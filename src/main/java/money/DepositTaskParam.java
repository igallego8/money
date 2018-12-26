package money;

import java.math.BigDecimal;

public class DepositTaskParam {

    private  BigDecimal amount;
    private Long savingAccountId;


    public DepositTaskParam(Long savingAccountId, BigDecimal amount) {
        this.savingAccountId = savingAccountId;
        this.amount = amount;
    }

    public Long getSavingAccountId() {
        return savingAccountId;
    }


    public BigDecimal getDepositAmount() {
        return amount;
    }
}
