package com.lightbend.akka.sample;

import java.util.concurrent.CompletableFuture;

/**
 * Created by a-7999 on 29/09/19.
 */
public class CompletableFutureMain {

    public static void main(String[] args) {




//        long startTime = System.currentTimeMillis();
//        System.out.println(System.currentTimeMillis());
//        thenCompose();
//        System.out.println(startTime - System.currentTimeMillis());
//        startTime = System.currentTimeMillis();
//        System.out.println(System.currentTimeMillis());
//        printMsg();
//        System.out.println(startTime - System.currentTimeMillis());
//        startTime = System.currentTimeMillis();
//        System.out.println(System.currentTimeMillis());
//        thenCombine();
//        System.out.println(startTime - System.currentTimeMillis());
//        System.out.println(System.currentTimeMillis());
//        thenAcceptBoth();
//        System.out.println(startTime - System.currentTimeMillis());
    }

    private static void thenCompose() {
//        CompletableFuture<String> completableFuture =
//                CompletableFuture.supplyAsync(() -> "Hello")
//                        .thenCompose(value ->
//                                CompletableFuture.supplyAsync(
//                                        () -> value + "  Its thenCompose"));


        CompletableFuture<CompletableFuture<String>> completableFuture =
                CompletableFuture.supplyAsync(() -> "Hello")
                        .thenApply(value -> CompletableFuture.supplyAsync(
                                () -> value + "  Its thenApply"));

        completableFuture.thenAccept(System.out::println); // Hello  Its thenCompose
    }


    private static void thenCombine() {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> "  Its thenCombine"), (value1, value2) -> value1 + value2);
        completableFuture.thenAccept(System.out::println); // Hello  Its thenCombine
    }

    private static void thenAcceptBoth() {
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> "  Its thenAcceptBoth"),
                        (value1, value2) -> System.out.println(value1 + value2)); // Hello  Its thenAcceptBoth
    }

    private static void printMsg() {
        String s = "Hello";
        s += "  Its thenCompose";
        System.out.println(s); // Hello  Its thenCompose
    }
}
