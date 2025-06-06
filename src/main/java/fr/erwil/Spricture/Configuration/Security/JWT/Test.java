package fr.erwil.Spricture.Configuration.Security.JWT;

import java.util.Comparator;
import java.util.List;

public class Test {
    public List<Integer> oddNumbers(List<Integer> input) {
        return input.stream().filter((number) -> number % 2 != 0).toList();
    }

    public List<Integer> doubleValues(List<Integer> input) {
        return input.stream().map((number) -> number * 2).toList();
    }

    public List<Integer> doubleOnlyOdds(List<Integer> input) {
        return input.stream()
                .filter(number -> number % 2 != 0)
                .map(number -> number * 2)
                .toList();
    }

    List<Integer> doubleOnlyOddsSorted(List<Integer> input) {
        return input.stream()
                .filter(number -> number % 2 != 0)
                .map(number -> number * 2)
                .sorted()
                .toList();
    }

    List<Integer> doubleOnlyOddsReverseSorted(List<Integer> input) {
        return input.stream()
                .filter(number -> number % 2 != 0)
                .map(number -> number * 2)
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    List<Integer> var(List<Integer> input) {
        var subList = input.subList(2, 5);
        subList.add(1);
        return subList;
    }

    void instanceOf(Object input) {
        if (input instanceof List<?> list) {
            System.out.println(list.size());
        }
    }
}