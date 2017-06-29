package com.madrapps.playground;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.ArrayList;
import java.util.Random;

public class Operators {
    public static void main(String[] args) throws InterruptedException {
        final Observable<Integer> observable = Observable.range(0, 100)
                .doOnSubscribe(disposable -> System.out.println("Subscribed"));
        observable.map(x -> x + x)
                .filter(x -> x % 3 != 0)
                .subscribe(System.out::println);


        Observable<Long> progress = Observable.fromArray(1L, 2L, 3L, 4L, 5L, 6L);
        Observable<Long> scan = progress.scan((total, partial) -> total + partial);

        scan.subscribe(System.out::println);

        Observable<String> progressString = Observable.fromArray("1", "how", "are");
        Observable<String> scan1 = progressString.scan((total, partial) -> total + partial);

        scan1.subscribe(System.out::println);

        Observable<Pair> progressPair = Observable.fromArray(new Pair("1", "2"), new Pair("3", "4"), new Pair("5", "6"));
        Observable<Pair> scan2 = progressPair.scan((total, partial) -> new Pair(total.val1, total.val2));

        scan2.subscribe(System.out::println);


        Maybe<Long> reduce = progress.reduce((x, y) -> x + y);
        reduce.subscribe(System.out::println);

        Single<ArrayList<Long>> reduceIntoCollection = progress.reduce(new ArrayList<Long>(), (list, item) -> {
            list.add(item);
            return list;
        });
        reduceIntoCollection.subscribe(x -> {
            System.out.println("Start collection");
            x.forEach(System.out::println);
            System.out.println("End collection");
        });


        Single<ArrayList<Long>> collect = progress.collect(ArrayList<Long>::new, ArrayList<Long>::add);
        collect.subscribe(System.out::println);


        Observable<Integer> randomInteger = Observable.create(s -> {
            final Random random = new Random();
            while (!s.isDisposed()) {
                s.onNext(random.nextInt(5));
            }
            s.onComplete();
        });
        randomInteger.distinct().take(4).subscribe(System.out::println);

    }

    public static class Pair {
        final String val1;
        final String val2;

        Pair(String val1, String val2) {
            this.val1 = val1;
            this.val2 = val2;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "val1='" + val1 + '\'' +
                    ", val2='" + val2 + '\'' +
                    '}';
        }
    }
}
