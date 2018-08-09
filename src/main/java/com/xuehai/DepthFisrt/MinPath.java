//package com.xuehai;
//
///**
// * @author ：周黎钢.
// * @date ：Created in 16:48 2018/7/29
// * @description:
// */
//public class MinPath {
//
//    static int m,n;   //静态变量m表示城市的个数,n表示总路线的条数
//    static int e[][]=new int[101][101];
//    static int book[]=new int[101];   //标记数组，主要在后面标记已走过的路线，防止重复
//    static int min=999999;
//    public static void main(String[] args)
//    {
//        e=new int[][]{
//                {0,2,min,min,10},
//                {min,0,3,min,7},
//                {4,min,0,4,min},
//                {min,min,min,0,5},
//                {min,min,3,min,0}};
//        dfs(0,0);   //从第一个城市出发，记录步数从0开始
//        System.out.println(min);
//    }
//    private static void dfs(int sta, int temp)
//    {
//        if(sta==4)     //此处的5也就是到达第五个城市
//        {
//            if(min>temp)
//                min=temp;
//            return;
//        }
//        for(int i=0;i<=4;i++)
//        {
//            if(e[sta][i]!=min && book[i]==0&&e[sta][i]!=0 )
//            {
//                book[i]=1;   //对走过的路线进行标记
//                dfs(i,temp+e[sta][i]);
//                book[i]=0;
//            }
//        }
//        return;
//    }
//}
