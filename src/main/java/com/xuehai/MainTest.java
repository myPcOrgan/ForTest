package com.xuehai;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.primitives.Ints.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

/**
 * @author ：周黎钢.
 * @date ：Created in 16:20 2018/8/9
 * @description:
 */
public class MainTest {

    public static void main(String[] args) {
        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(numbers -> numbers.stream())
                .collect(toList());
        assertEquals(asList(1, 2, 3, 4), together);
        String min= Stream.of("efg","c","d2").min(Comparator.comparing((a)->a)).get();

        System.out.println(min);
    }
}
