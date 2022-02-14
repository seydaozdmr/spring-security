package com.turkeyjava.security.jwtapp.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        String s="Ankara";
        int sayac=0;
        char[]arr=s.toCharArray();
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr.length-1;j++){
                char temp= arr[j+1];
                arr[j+1]=arr[j];
                arr[j]=temp;
                System.out.println(arr);
                sayac++;
            }
        }
        System.out.println(sayac);

        int [] arr2={1,3,4,2,6};
        int sum=((arr2.length+1)*(arr2.length+2))/2;
        int result=sum - Arrays.stream(arr2).sum();
        int result2=0;
        for(int i=0;i<arr2.length;i++){
            result2+=arr2[i];
        }
        System.out.println(result2-sum);

        List<Animal> listOfAnimal = List.of(
                new Animal("Spider", 8, 0),
                new Animal("Fly", 6, 2),
                new Animal("Dragonfly", 6, 4),
                new Animal("Worm", 0, 0)
        );

//        listOfAnimal.sort((la, ra) -> {
//            if (la.getNumOfLegs() == ra.getNumOfLegs()) {
//                return Integer.compare(la.getNumOfWings(),
//                        ra.getNumOfWings());
//            } else {
//                return Integer.compare(la.getNumOfLegs(),
//                        ra.getNumOfLegs());
//            }
//        });
//        System.out.println(listOfAnimal);
//
//        listOfAnimal.stream()
//                .sorted(Comparator
//                        .comparing(a ->  a.getNumOfLegs())
//                        .thenComparing(a -> a.getNumOfWings()))
//                .collect(Collectors.toList()).forEach(System.out::println);

//        listOfAnimal.stream()
//                .sorted(Comparator
//                        .comparing(a -> a.getNumOfLegs()))
//                //.sorted(Comparator
//                        //.comparing(a -> a.getNumOfWings()))
//                .collect(Collectors.toList()).forEach(System.out::println);
    }
}
class Animal {
    private String name;
    private int numOfLegs;
    private int numOfWings;
    public Animal(String name, int numOfLegs, int numOfWings) {
        this.name = name;
        this.numOfLegs = numOfLegs;
        this.numOfWings = numOfWings;
    }
    // getters, setters and toString()
}
