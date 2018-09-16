package com.leeCoder;

import com.google.common.collect.Lists;
import com.xuehai.domain.model.Task;
import com.xuehai.dto.common.ClassInfo;
import com.xuehai.dto.common.Student;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.primitives.Ints.asList;
import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;

/**
 * @author ：周黎钢.
 * @date ：Created in 16:20 2018/8/9
 * @description:
 */
public class LambadaTest {
    private static List<Student> students = new LinkedList<>();
    private static Map<ClassInfo,Student> classStus = new HashMap<>();

    public static void main(String[] args) throws UnsupportedEncodingException {
        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(numbers -> numbers.stream())
                .collect(toList());
        assertEquals(asList(1, 2, 3, 4), together);
        String min = Stream.of("efg", "c", "d2").min(Comparator.comparing((a) -> a)).get();
        List<Task> tasks = new LinkedList<>();
        tasks.sort(Comparator.comparing(Task::getCreateTime).reversed().thenComparing(Task::getBookType));
        System.out.println(min);
        BinaryOperator<Integer> accumulator = (acc, ele) -> acc + ele;
        Integer sum = accumulator.apply(accumulator.apply(1, 2), 3);
        System.out.println(sum instanceof Integer);
        System.out.println(MessageFormat.format("a={0}\t,b={1}", "1", "2"));
        System.out.println(String.format("a=%s\t,b=%s", "1", "2"));
        Map<Integer, Integer> map = new HashMap();
        map.put(3, 4);
        map.put(3, 5);
        System.out.println(map.get(3));
        Optional optional = Optional.ofNullable(null);
        System.out.println(optional.isPresent());
        System.out.println(optional.orElse(31));
        System.out.println(optional.orElseGet(() -> "student"));
        Set<Integer> set = new HashSet<>(asList(1, 3, 2, 4));
        List list = set.stream().map(x -> x + 1).collect(toCollection(LinkedList::new));
        set.forEach(a -> System.out.print(a + " "));
        System.out.println();
        testJoining();
        testGroupingBy();
        testMap();
//        testMentKalo();
        testParallelArray();
        System.out.println();
        int get=20000;
        System.out.println(taxRate(get));
        System.out.println(newTaxRate(get));
        System.out.println(taxRate(get)-newTaxRate(get));
        compareTwoFor();
        testLambadaEfficiency();

    }

    static void testLambadaEfficiency(){
        Long start=DateTime.now().getMillis();
        List<Integer>integers=new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            integers.add(i);
        }
        int max = 0;
        Long start1=DateTime.now().getMillis();
        for (int i = 0; i < integers.size(); i++) {
            if(integers.get(i)> max){
                max =integers.get(i);
            }
        }
        Long end1=DateTime.now().getMillis();
        System.out.println("最大值："+ max +",普通for循环耗时："+(end1-start1));
        Long start2=DateTime.now().getMillis();
        final int[] max1 = {0};
        integers.forEach(integer -> {
            if(integer> max1[0]){
                max1[0] =integer;
            }
        });
        Long end2=DateTime.now().getMillis();
        System.out.println("最大值："+ max1[0] +",foreach耗时："+(end2-start2));
        Long start3=DateTime.now().getMillis();
        int max2=integers.stream().max(Comparator.comparingInt(integer->integer)).get();
        Long end3=DateTime.now().getMillis();
        System.out.println("最大值："+ max2 +",直接求最大值耗时："+(end3-start3));
        float a=5.1f;
        float b=-5.2f;
        BigDecimal c=new BigDecimal(String.valueOf(a));
        BigDecimal d=new BigDecimal(String.valueOf(b));
        System.out.println(Float.valueOf(c.subtract(d).toString()));
    }


    static void compareTwoFor(){
        Long start2=DateTime.now().getMillis();//这行代码只要去掉，第一个耗时就会增加，初步估计是因为DateTime初始化有时间
        List<Integer>integers=new LinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            integers.add(i);
        }
//        Long end2=DateTime.now().getMillis();
//        System.out.println("赋初值，耗时："+(end2-start2));
        Long start=DateTime.now().getMillis();
        for (int i = 0,len=integers.size(); i < len; i++) {

        }
        Long end=DateTime.now().getMillis();
        System.out.println("有定值，耗时："+(end-start));
        Long start1=DateTime.now().getMillis();
        for (int i = 0; i < integers.size(); i++) {

        }
        Long end1=DateTime.now().getMillis();
        System.out.println("没有定值，耗时："+(end1-start1));
    }
    static double taxRate(int get){
        if(get<=3500){
            System.out.println(0);
            return 0;
        }
        double r1=(get-3500)*0.03;
        double r2=(get-5000)*0.1;
        double r3=(get-8000)*0.2;
        double r4=(get-12500)*0.25;
        double r5=(get-38500)*0.3;
        double r6=(get-58500)*0.35;
        double r7=(get-83500)*0.45;
        if(get<=5000){
            return r1;
        }else if(get<=8000){
            return 45+r2;
        }else if(get<=12500){
            return 45+300+r3;
        }else if(get<=38500){
            return 45+300+900+r4;
        }else if(get<=58500){
            return 45+300+900+26000*0.25+r5;
        }else if(get<=83500){
            return 45+300+900+26000*0.25+20000*0.3+r6;
        }else{
            return 45+300+900+26000*0.25+20000*0.3+25000*0.35+r7;
        }
    }

    static double newTaxRate(int get){
        if(get<=5000){
            System.out.println(0);
            return 0;
        }
        double r1=(get-5000)*0.03;
        double r2=(get-8000)*0.1;
        double r3=(get-17000)*0.2;
        double r4=(get-30000)*0.25;
        double r5=(get-40000)*0.3;
        double r6=(get-60000)*0.35;
        double r7=(get-85000)*0.45;
        if(get<=8000){
            return r1;
        }else if(get<=17000){
            return 90+r2;
        }else if(get<=30000){
            return 45+900+r3;
        }else if(get<=40000){
            return 45+900+2600+r4;
        }else if(get<=60000){
            return 45+900+2600+2500+r5;
        }else if(get<=85000){
            return 45+900+2600+2500+20000*0.3+r6;
        }else{
            return 45+900+2600+2500+20000*0.3+25000*0.35+r7;
        }
    }
    static void testParallelArray(){
        int[]values=new int[5];
        Arrays.parallelSetAll(values,i->i+values[i]+10);
        Arrays.stream(values).forEach(value -> System.out.print(value+" "));
    }    /**
     * 利用并行的lambada实现蒙特卡咯模拟法,掷N次筛子
     */
    static void testMentKalo(){
        int N=100000;
        double fraction=1.0/N;
        Map<Object, DoubleSummaryStatistics> collect = IntStream.range(0, N)
                .parallel()
                .mapToObj(n -> twoDiceThrows(ThreadLocalRandom.current()))
                .collect(groupingBy(side -> side, summarizingDouble(n -> fraction)));
        collect.forEach((num,probability)->{
            System.out.println(num+"的概率"+"是"+1/probability.getSum());
        });
//        collect.entrySet().forEach(System.out::println);

    }
    private static Object twoDiceThrows(ThreadLocalRandom random) {
        int firstThrow = random.nextInt(1, 7);
        int secondThrow = random.nextInt(1, 7);
        return firstThrow + secondThrow;
    }

    /**
     * map的遍历
     */
    static void testMap(){
        classStus.forEach((classInfo,student)->{
            System.out.println(classInfo.getClassName()+"有学生"+student.getUserName());
        });
    }

    static void testJoining(){
        String collect = students.stream().map(Student::getUserName).collect(joining(",","[","]"));
        System.out.println(collect);
        List<Integer> collect1 = students.stream().map(Student::getUserId).collect(toList());
        System.out.println("join1:"+StringUtils.join(collect1,","));
    }
    static void testReduce(List<Integer> together) {
        Integer reduce = together.stream().reduce(0, (acc, ele) -> acc + ele);
        System.out.println(reduce);
    }

    static void testThreadLocalInitial() {
        ThreadLocal<String> stringThreadLocal = ThreadLocal.withInitial(() -> "ds");
        System.out.println(stringThreadLocal.get());
    }

    static void testPartitioningBy(List<Integer> together) {
        Map<Boolean, List<Integer>> collect = together.stream().collect(partitioningBy(a -> a.equals(3)));
        collect.get(true).forEach(a -> System.out.print(a + " "));
        System.out.println();
        collect.get(false).forEach(a -> System.out.print(a + " "));
        System.out.println();
    }

    static {
        ClassInfo class1 = new ClassInfo()
                .setClassId(1)
                .setClassName("1班");
        ClassInfo class2 = new ClassInfo()
                .setClassId(2)
                .setClassName("2班");
        Student student1 = new Student()
                .setClassInfo(class1)
                .setUserId(100)
                .setUserName("xiaowang");
        Student student2 = new Student()
                .setClassInfo(class1)
                .setUserId(101)
                .setUserName("xiaoliu");
        Student student3 = new Student()
                .setClassInfo(class2)
                .setUserId(200)
                .setUserName("wangxiu");
        students.add(student1);
        students.add(student2);
        students.add(student3);
        classStus.put(student1.getClassInfo(),student1);
        classStus.put(student3.getClassInfo(),student3);
    }

    static void testGroupingBy() {
        Map<Integer, List<Student>> collect2 = students.stream().collect(groupingBy(student -> student.getClassInfo().getClassId()
        ));
        //输出每个班级的学生
        collect2.get(1).forEach(student -> System.out.println(student));
        System.out.println();
        collect2.get(2).forEach(student -> System.out.println(student));
        System.out.println();
        //计算每个分组的数量
        Map<Integer, Long> collect = students.stream().collect(groupingBy(student -> student.getClassInfo().getClassId(), counting()));
        System.out.println("1班有学生"+collect.get(1)+"个");
        System.out.println("2班有学生"+collect.get(2)+"个");
        //收集每个班级学生的名字集合
        Map<Integer, List<String>> collect1 = students.stream().collect(groupingBy(student -> student.getClassInfo().getClassId(), mapping(Student::getUserName, toList())));
        System.out.println("1班有学生"+collect1.get(1).stream().collect(joining(",","[","]")));
        System.out.println("2班有学生"+collect1.get(2).stream().collect(joining(",","[","]")));
    }
}
