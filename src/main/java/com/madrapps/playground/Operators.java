package com.madrapps.playground;

import io.reactivex.Observable;

public class Operators {
    public static void main(String[] args) throws InterruptedException {
        final Observable<Integer> observable = Observable.range(0, 100)
                .doOnSubscribe(disposable -> System.out.println("Subscribed"));
        observable.map(x -> x + x)
                .filter(x -> x % 3 != 0)
                .subscribe(System.out::println);
    }
}
