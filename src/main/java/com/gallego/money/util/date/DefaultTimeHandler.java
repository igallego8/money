package com.gallego.money.util.date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class DefaultTimeHandler implements TimeHandler{

    private LocalDate date;

    public  DefaultTimeHandler( LocalDate date){
        this.date = date;
    }
    @Override
    public void change(DateChangeHandler handler, int offset) {
       date = handler.process(date , offset);
    }

    @Override
    public LocalDate today() {
        return date;
    }

    @Override
    public void setDate(LocalDate localDate) {
        this.date = localDate;
        publish();
    }

    @Value("${publisher.time.threads}")
    private int threads=10;
    private ExecutorService executorService = Executors.newFixedThreadPool(threads);
    private SubmissionPublisher sb = new SubmissionPublisher(executorService, Flow.defaultBufferSize());




    @Override
    public void subscribe(Flow.Subscriber subscriber) {
        sb.subscribe(subscriber);
    }

  //  @Scheduled(fixedRate = 1000)
    public void publish()
    {
        System.out.println("submitting");
        sb.submit(date);
    }


}
