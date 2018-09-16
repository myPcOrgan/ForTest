//package com.xuehai;
//
//import java.util.Stack;
//
///**
// * @author ：周黎钢.
// * @date ：Created in 16:26 2018/7/29
// * @description:
// */
//
//public class DFPath {
//
//    public static void main(String[] args) {
//        //迷宫点，1表示可走，0表示不可走，(0,0)为入口，(0,3)为出口
//        int[][] maze = {
//                {1,1,1,0,1},
//                {1,0,1,0,1},
//                {1,0,1,1,1},
//                {1,1,0,1,1},
//                {0,0,0,1,1}};
//        //可以走的方向，上下左右
//        int[][] move = {{0,-1},{0,1},{-1,0},{1,0}};
//        //使用栈s存储路径
//        Stack<Point> s = new Stack<Point>();
//        int a = path(maze, move, s);
//        if(a==1){
//            while(!s.isEmpty()){//输出是倒的
//                Point step = s.pop();
//                System.out.println(step.x+" "+step.y);
//            }
//        }
//    }
//    /**
//     * 将(0,0)点入栈，将maze[0][0]=-1，表示点(0,0)已经访问过了
//     * 维护一个当前点temp，第一次temp为栈顶，判断temp其周围四个点是否可达，
//     * 如果可达则将temp更新为可达的那个点，并将maze路径中对应位置标记位-1，表示已经访问过
//     * 如果不可达则弹出栈顶，取栈下一个元素
//     * 当stack不为空时
//     * @param maze
//     * @param move
//     * @param s
//     * @return
//     */
//    public static int path(int[][] maze,int[][] move,Stack<Point> s){
//        Point temp = new Point(0,0); //起点
//        maze[0][0]=-1;//表示已经访问过这个点了
//        int m=maze.length-1,n=maze[0].length-1;
//        s.push(temp);
//        //栈不为空
//        while(!s.isEmpty()){
//            //取栈顶的点
//            temp = s.peek();
//            int d = 0;
//            //遍历上下左右四个方向
//            while(d<4){
//                int i = temp.x + move[d][0];
//                int j = temp.y + move[d][1];
//                //该点不越界，且可达
//                if(i>=0 && j>=0 && i<=m && j<=n && maze[i][j]==1){
//                    temp = new Point(i,j); //到达新点
//                    s.push(temp);
//                    maze[i][j] = -1;  //到达新点，标志已经到达
//                    if(i == 0 && j == n){
//                        return 1;  //到达出口，迷宫有路，返回1
//                    }else{
//                        d = 0;  //此时temp点已经更新为新点了，重新初始化方向
//                    }
//                }else{
//                    d++; //改变方向
//                }
//            }
//            //遍历其周围四个点，都没法通过，则弹出这个点
//            s.pop();
//        }
//        return 0;
//    }
//    static class Point{
//        int x,y;
//        public Point(int x,int y) {
//            this.x = x;//横坐标
//            this.y = y;//纵坐标
//        }
//    }
//
//}
