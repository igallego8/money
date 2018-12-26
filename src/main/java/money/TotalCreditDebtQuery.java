package money;

import com.gallego.money.util.Context;

import java.math.BigDecimal;

public class TotalCreditDebtQuery {

    public BigDecimal query(Long creditId) {
        return Context.gateway.findCreditBy(creditId).getTotalDebt();
    }
}
