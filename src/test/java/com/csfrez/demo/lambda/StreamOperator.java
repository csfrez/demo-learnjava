package com.csfrez.demo.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamOperator {

    public static void main(String[] args) {
        Stream.of(1, 2, 3)
            .map(v -> v + 1)
            .flatMap(v -> Stream.of(v * 5, v * 10))
            .forEach(System.out::println);

        Stream.of(1, 2, 3)
            .takeWhile(v -> v < 3)
            .dropWhile(v -> v < 2)
            .forEach(System.out::println);

        // 输出 6
        Stream.of(1, 2, 3).reduce((v1, v2) -> v1 + v2)
            .ifPresent(System.out::println);

        // 输出 120
        int result1 = Stream.of(1, 2, 3, 4, 5)
                .reduce(1, (v1, v2) -> v1 * v2);
        System.out.println(result1);

        // 输出 15
        int result2 = Stream.of(1, 2, 3, 4, 5)
                .parallel()
                .reduce(0, (v1, v2) -> v1 + v2, (v1, v2) -> v1 + v2);
        System.out.println(result2);

        Map<Character, List<String>> names = Stream.of("Alex", "Bob", "David", "Amy")
                .collect(Collectors.groupingBy(v -> v.charAt(0)));
        System.out.println(names);

        String str = Stream.of("a", "b", "c")
                .collect(Collectors.joining(", "));
        System.out.println(str);

        double avgLength = Stream.of("hello", "world", "a")
                .collect(Collectors.averagingInt(String::length));
        System.out.println(avgLength);

        IntSummaryStatistics statistics = Stream.of("a", "b", "cd")
                .collect(Collectors.summarizingInt(String::length));
        System.out.println(statistics.getAverage());
        System.out.println(statistics.getCount());

        List<String> wordList = List.of("Jack", "Peter", "Frez");
        List<String> output = wordList.stream().
                map(String::toUpperCase).
                collect(Collectors.toList());
        System.out.println(output);

        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        List<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream()).collect(Collectors.toList());
        System.out.println(outputStream);

        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens =
                Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
        System.out.println(List.of(evens));

        Stream.of("one", "two", "three", "four")
            .filter(e -> e.length() > 3)
            .peek(e -> System.out.println("Filtered value: " + e))
            .map(String::toUpperCase)
            .peek(e -> System.out.println("Mapped value: " + e))
            .collect(Collectors.toList());

        testLimitAndSkip();

        testSortedAndLimit();

        testMax();

        testDistinct();

        testMatch();

        testGenerate();

        testIterate();

        testReduction();
    }

    public static void testLimitAndSkip() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<String> personList2 = persons.stream().
                map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);
    }

    public static void testSortedAndLimit(){
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<String> personList2 = persons.stream().sorted((p1, p2) ->
                p1.getName().compareTo(p2.getName())).map(Person::getName).limit(2).collect(Collectors.toList());
        System.out.println(personList2);
    }

    public static void testMax(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("README.md"));
            int longest = br.lines().
                    mapToInt(String::length).
                    max().
                    getAsInt();
            br.close();
            System.out.println(longest);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void testDistinct(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("README.md"));
            List<String> words = br.lines().
                    flatMap(line -> Stream.of(line.split(" "))).
                    filter(word -> word.length() > 0).
                    map(String::toLowerCase).
                    distinct().
                    sorted().
                    collect(Collectors.toList());
            br.close();
            System.out.println(words);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void testMatch(){
        List<Person> persons = new ArrayList();
        persons.add(new Person(1, "name" + 1, 10));
        persons.add(new Person(2, "name" + 2, 21));
        persons.add(new Person(3, "name" + 3, 34));
        persons.add(new Person(4, "name" + 4, 6));
        persons.add(new Person(5, "name" + 5, 55));
        boolean isAllAdult = persons.stream().
                allMatch(p -> p.getAge() > 18);
        System.out.println("All are adult? " + isAllAdult);
        boolean isThereAnyChild = persons.stream().
                anyMatch(p -> p.getAge() < 12);
        System.out.println("Any child? " + isThereAnyChild);
    }

    public static void testGenerate(){
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
                limit(10).forEach(System.out::println);

        Stream.generate(new PersonSupplier()).
                limit(10).
                forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));

    }

    public static void testIterate(){
        Stream.iterate(0, n -> n + 3).limit(10). forEach(x -> System.out.print(x + " "));
    }

    public static void testReduction(){
        Map<Integer, List<Person>> personGroups = Stream.generate(new PersonSupplier()).
                limit(1000).
                collect(Collectors.groupingBy(Person::getAge));
        Iterator it = personGroups.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
            System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
        }

        Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.partitioningBy(p -> p.getAge() < 18));
        System.out.println("Children number: " + children.get(true).size());
        System.out.println("Adult number: " + children.get(false).size());
    }

    private static class PersonSupplier implements Supplier<Person> {
        private int index = 0;
        private Random random = new Random();
        @Override
        public Person get() {
            return new Person(index++, "StormTestUser" + index, random.nextInt(100));
        }
    }

    private static class Person {
        public int no;
        private String name;
        private int age;
        public Person (int no, String name) {
            this.no = no;
            this.name = name;
        }
        public Person (int no, String name, int age) {
            this.no = no;
            this.name = name;
            this.age = age;
        }
        public String getName() {
            System.out.println(name);
            return name;
        }

        public int getAge() {
            System.out.println(age);
            return age;
        }
    }
}
