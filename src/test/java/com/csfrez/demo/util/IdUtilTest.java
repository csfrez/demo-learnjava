package com.csfrez.demo.util;

public class IdUtilTest {

    public static void main(String[] args) {
        for(int i=0; i<1000; i++){
            System.out.println(IdUtil.nextId());
        }
    }
}
