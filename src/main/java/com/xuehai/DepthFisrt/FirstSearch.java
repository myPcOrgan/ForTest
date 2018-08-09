package com.xuehai.DepthFisrt;

import com.google.common.collect.Sets;

import java.io.File;
import java.util.*;

/**
 * @author ：周黎钢.
 * @date ：Created in 16:11 2018/7/23
 * @description:
 */
public class FirstSearch {
    public static void main(String[] args) {
//        File file = new File("E:\\周报");
//        depth(file);
//        breadth(file);
//        search();
        getAll(0);
        recursives(1);
    }

    /**
     * 深度优先遍历
     *
     * @param file
     */
    static void depth(File file) {
        Stack<File> files = new Stack<>();
        files.push(file);
        while (!files.isEmpty()) {
            File parent = files.pop();
            if (parent.isFile()) {
                System.out.println(parent.getName());
            }
            if (parent.isDirectory()) {
                File[] children = parent.listFiles();
                if (children == null || children.length == 0) {
                    continue;
                }
                for (File child : children) {
                    if (child.isFile()) {
                        System.out.println(child.getName());
                    } else if (child.isDirectory()) {
                        files.push(child);
                    }
                }
            }
        }
    }

    /**
     * 广度优先遍历
     *
     * @param file
     */
    static void breadth(File file) {
        Deque<File> files = new ArrayDeque();
        //Deque是双向队列这里换成push就会变成深度遍历
//        files.push(file);
        files.add(file);
        while (!files.isEmpty()) {
            File parent = files.poll();
            if (parent.isFile()) {
                System.out.println(parent.getName());
            }
            File[] children = parent.listFiles();
            if (children == null || children.length == 0) {
                continue;
            }
            for (File child : children) {
                if (child.isFile()) {
                    System.out.println(child.getName());
                } else if (child.isDirectory()) {
                    files.add(child);
                }
            }
        }
    }

    /**
     * 地道战
     */
    static void search() {
        int[][] channels = {{1, 3}, {2, 3}, {3, 4}, {3, 5}, {4, 5}, {5, 6}};
        Deque<Integer> stack = new ArrayDeque<>();
        int start = 1;
        int end = 6;
        stack.add(start);
        Set<Integer> path = Sets.newHashSet();
        while (!stack.isEmpty()) {
            Integer s = stack.poll();
            boolean exist = false;
            for (int i = 0; i < channels.length; i++) {
                if (channels[i][0] == s) {
                    if (channels[i][1] == end) {
                        path.add(channels[i][0]);
                        path.add(end);
                        System.out.println(path);
                        continue;
                    }
                    stack.add(channels[i][1]);
                    path.add(channels[i][0]);
                    exist = true;
                }
            }
            if (!exist) {
                path = Sets.newHashSet();
            }
        }
    }

    /**
     * 地道战
     */
    static void recursives(int start) {
        int[][] channels = {{1, 3}, {2, 3}, {3, 4}, {3, 5}, {4, 5}, {5, 6}};
        int[] pass=new int[999];
        int end = 6;
        for (int i = 0; i < channels.length; i++) {
            if (channels[i][0] == start&&pass[i]==0) {
                if (channels[i][1] == end) {
                    list.forEach(ele->{
                        System.out.print(ele+" ");
                    });
                    System.out.print(channels[i][0]+" "+end+" ");
                    System.out.println();
                    return;
                }
                list.add(channels[i][0]);
                pass[i]=1;
                recursives(channels[i][1]);
                pass[i]=0;
                list.remove((Object)channels[i][0]);
            }
        }
    }

    /**
     * 遍历所有排序
     */
    static List list = new LinkedList();
    static int[] searched = new int[999];

    static void getAll(int step) {
        int[] source = new int[]{1, 2, 3, 4};
        if (step == source.length) {
            list.forEach(element -> {
                System.out.print(element + " ");
            });
            System.out.println();
            return;
        }
        for (int i = 0; i < source.length; i++) {
            if (searched[i] == 0) {
                list.add(source[i]);
                searched[i] = 1;
                getAll(step + 1);
                list.remove((Object) source[i]);
                searched[i] = 0;
            }
        }
    }
}
