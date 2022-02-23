package com.turkeyjava.security.basic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String helloApp(){
        /**
         * Getting Security Context
         */
        SecurityContext context= SecurityContextHolder.getContext();

        return "hello "+ context.getAuthentication().getName()+ " turkey java community";
    }

    @RequestMapping("/hellouser")
    public String helloUserApp(Authentication authentication){
        return "hello "+ authentication.getName()+ " turkey java community";
    }

    @RequestMapping("/bye")
    @Async
    public String goodbye() throws InterruptedException {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Thread.sleep(5000);
        return username + " will sended after 5 seconds";
    }

    /**
     * DelegatingSecurityContextCallable,
     * which provides the current context to the new thread, as provided by this listing.
     */
    @RequestMapping("/mythread")
    public String mythread() throws InterruptedException, ExecutionException {
        Callable<String> task = () -> {
            System.out.println("asenkron çalışan thread : "+Thread.currentThread().getName());
            SecurityContext context=SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };
        ExecutorService executorService= Executors.newCachedThreadPool();
        System.out.println("istek geldiğinde onu karşılayan thread : "+Thread.currentThread().getName());
        try{
            var contextTask= new DelegatingSecurityContextCallable<>(task);
            Thread.sleep(4000);
            return "Username : "+ executorService.submit(contextTask).get();
        } finally {
            executorService.shutdown();
        }
    }

    @RequestMapping("/count")
    @Async
    public void countRequest(@RequestParam int value) throws InterruptedException {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        count(value);
        System.out.println(username+ " counted");
    }

    private void count(int value){
        IntStream.range(0,value).map(i->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        }).forEach(System.out::println);
    }
}
