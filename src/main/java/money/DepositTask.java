package money;

import com.gallego.money.hex.model.entity.Ledger;
import com.gallego.money.exception.RegistryNotFoundException;
import com.gallego.money.util.Context;

import java.util.List;

public class DepositTask implements MoneyTask {

    List<DepositTaskParam> params;
    public DepositTask() {

    }

    public void setTaskParams(List<DepositTaskParam> params){
        this.params = params;
    }

    @Override
    public void process() {
        System.out.println("task!!!"+this.hashCode() + ""+ Thread.currentThread().getName());
        params.forEach(x-> {
            SavingAccount savingAccount = Context.gateway.getSavingAccount(x.getSavingAccountId());
            savingAccount.addAmount(x.getDepositAmount());
            Context.gateway.update(savingAccount);
            Ledger ledger;
            try {
                ledger = Context.gateway.getLedger(x.getSavingAccountId());
            }catch (RegistryNotFoundException ex){
                ledger = new Ledger(x.getSavingAccountId());
                Context.gateway.persist(ledger);
            }
            ledger.credit(x.getDepositAmount());
        });

    }
}
