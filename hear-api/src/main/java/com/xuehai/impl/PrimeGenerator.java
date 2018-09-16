package com.xuehai.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 周黎钢
 * @date 2017/11/28 13:43
 */
public class PrimeGenerator implements Runnable{
    final List<BigInteger>bigIntegers=new ArrayList<>();
    private volatile Boolean cancelled=false;
    @Override
    public void run() {
        BigInteger bg=BigInteger.ONE;
        while (!cancelled){
            bg=bg.nextProbablePrime();
            synchronized (this){
                bigIntegers.add(bg);
            }
        }
    }
    public synchronized void cancle(){
        cancelled=true;
    }
    public synchronized List<BigInteger> getBigIntegers(){
        return new ArrayList<>(bigIntegers);
    }
}
