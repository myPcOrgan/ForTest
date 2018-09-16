//package com.xuehai;
//
//import java.util.*;
//
///**
// * @author ：周黎钢.
// * @date ：Created in 14:53 2018/7/29
// * @description:
// */
//public class FSearch {
//    static int[][] datas = null;
//    static int[][] move = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
//    static int result = 0;
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int m = sc.nextInt();
//        int startX = 0, startY = 0;
//        String temp = null;
//        datas = new int[n + 2][m + 2];
//        for (int i = 1; i <= n; i++) {
//            temp = sc.next();
//            for (int j = 1; j <= m; j++) {
//                char techar = temp.charAt(j - 1);
//                switch (techar) {
//                    case 'S':
//                        datas[i][j] = 1;
//                        startX = i;
//                        startY = j;
//                        break;
//                    case 'T':
//                        datas[i][j] = 4;
//                        break;
//                    case '.':
//                        datas[i][j] = 2;
//                        break;
//                    case '#':
//                        datas[i][j] = 3;
//                        break;
//                }
//            }
//        }
//        dfs(startX, startY);
//        System.out.println(result);
//    }
//
//    public static void dfs(int x, int y) {
//        if (datas[x][y] == 4) {//如果走到了终点，数目加1
//            result += 1;
//            return;
//        } else {
//            for (int i = 0; i < 4; i++) {  //尝试四个方向
//                datas[x][y] = 0;   //标记这个位置走过了，因为一个位置只能走一次
//                int a=x + move[i][0];
//                int b=y + move[i][1];
//                if (datas[a][b] == 2 || datas[a][b] == 4) {
//                    dfs(a,b);
//                }
//                datas[x][y] = 2;   //回溯，因为2代表可以走
//            }
//        }
//    }
//}
//
//
