package com.leeCoder;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * @author ：周黎钢.
 * @date ：Created in 17:23 2018/8/27
 * @description:
 */
public class SplitOrders {
    public static class Item {


        /**
         * 卖家用户id
         */

        long sellerId;


        /**
         * 商品价格，单位分
         */

        long price;

    }


    public static class Order {


        /**
         * 该订单对应的商品
         */

        List<Item> orderItems;


        /**
         * 该订单金额，单位分
         */

        long totalPrice;


        /**
         * 该订单对应的卖家userId
         */

        long sellerId;

    }


    /**
     * 根据购物车的商品，生成相应的交易订单，根据如下规则
     * <p>
     * 1.每笔交易订单可以有多个商品
     * <p>
     * 2.每笔交易订单的商品只能是同一个卖家
     * <p>
     * 3.每笔交易商品的总价格不能超过1000元
     * <p>
     * 4.生成的交易订单数量最小
     *
     * @param items：购物车所有商品
     */

    public static List<Order> packageItemsToOrders(List<Item> items) {
        Map<Long, List<Item>> collect = items.stream().collect(groupingBy(item -> item.sellerId));
        List<Order> orders = new LinkedList<>();
        collect.forEach((userId, item) -> {
            Collections.sort(item, Comparator.comparing(sitem -> sitem.price));
            List<List<Item>> result = findMinOrderItems(item);
            List<Order> collect1 = result.stream().map(itemsList -> {
                Order order = new Order();
                order.sellerId = userId;
                order.orderItems = itemsList;
                order.totalPrice = itemsList.stream().collect(summingLong(item1 -> item1.price));
                return order;
            }).collect(toCollection(LinkedList::new));
            orders.addAll(collect1);
        });
        return orders;
    }

    /**
     * 查找并返回最小订单数的商品集合
     *
     * @param items
     * @return
     */
    static List<List<Item>> findMinOrderItems(List<Item> items) {
        List<List<Item>> result = new LinkedList<>();
        long sum = 0;
        //剩余商品
        List<Item> left = new LinkedList<>(items);
        //这是排序过的集合，从最大价格商品依次往下查询，只要不大于1000，就插入成功
        while (CollectionUtils.isNotEmpty(left)) {
            List<Item> getResult = new LinkedList<>();
            List<Item> copyleft = new LinkedList<>(left);
            for (int i = copyleft.size() - 1; i >= 0; i--) {
                sum += copyleft.get(i).price;
                if (sum <= 1000) {
                    getResult.add(copyleft.get(i));
                    left.remove(copyleft.get(i));
                } else {
                    sum -= copyleft.get(i).price;
                    continue;
                }
            }
            sum = 0;
            result.add(getResult);
        }
        return result;
    }

    /**
     * @param args
     */

    public static void main(String[] args) {

        // TODO Auto-generated method stub
        Item item = new Item();
        item.price = 930L;
        item.sellerId = 1;
        Item item1 = new Item();
        item1.price = 60L;
        item1.sellerId = 1;
        Item item2 = new Item();
        item2.price = 8L;
        item2.sellerId = 1;
        Item item3 = new Item();
        item3.price = 990L;
        item3.sellerId = 2;
        Item item4 = new Item();
        item4.price = 660L;
        item4.sellerId = 2;
        Item item5 = new Item();
        item5.price = 330L;
        item5.sellerId = 2;
        Item item6 = new Item();
        item6.price = 8L;
        item6.sellerId = 2;
        List<Item> items = new LinkedList<>();
        items.add(item);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        List<Order> orders = packageItemsToOrders(items);
        System.out.println(orders);
    }
}
