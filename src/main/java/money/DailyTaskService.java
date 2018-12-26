package money;

import com.gallego.money.util.date.TimeHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Flow;
import java.util.concurrent.Semaphore;

public class DailyTaskService implements Flow.Subscriber {

    private LocalDate lastDate;
    private final String id = UUID.randomUUID().toString();
    private Flow.Subscription subscription;
    private static final int SUB_REQ=10;

    public static final Semaphore semaphore= new Semaphore(1,true);

    List<MoneyTask> tasks = new ArrayList<>();

    private static DailyTaskService instance;

    public  static DailyTaskService getInstance(List<MoneyTask> tasks){
        if (instance == null)
            instance = new DailyTaskService(tasks);
        return instance;
    }

    public DailyTaskService(List<MoneyTask> tasks){
        this.tasks = tasks;
    }


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println(  "subscriber: "+ id +" ==> Subscribed");
        this.subscription = subscription;
        subscription.request(SUB_REQ);
    }

    @Override
    public  void  onNext(Object o) {

        System.out.println("service"+this.hashCode()+" "+ Thread.currentThread().getName());
      //  try {
//semaphore.acquire();

        LocalDate today = (LocalDate) o;
        if (lastDate == null ){
            lastDate = today;
            tasks.stream().forEach(t-> {

                    t.process();

            });
        }else{
            if (lastDate.getDayOfYear()<today.getDayOfYear()){
                tasks.stream().forEach(t-> {

                        t.process();

                });
            }
        }


       // semaphore.release();

            // } catch (InterruptedException e) {
            //e.printStackTrace();
     //   }

    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(  "onError!!");
    }

    @Override
    public void onComplete() {
        System.out.println(  "Done!!");
    }
}
