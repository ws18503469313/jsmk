package com.itmuch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class B  {


    public static void main(String args[]) throws Exception{
        count();
        Map<String, Object> map = new HashMap<>();
    }
    public static void count() throws Exception {
        String filename = "D:/File/calcCharNum.txt";
        BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
        char [] line = new char[1024];
        int A = 0;
        int a = 0;
        while(reader.read(line) != -1){
            for(int i = 0; i < 1024; i++){
                if(line[i] == 'a'){
                    a++;
                }
                if(line[i] == 'A'){
                    A++;
                }
            }
        }
        System.out.println("a: " + a);
        System.out.println("A: " + A);
    }

    public static void print(String[] args){
        for(int i =1;i<=9;i++){
            for(int j=0;j<=9;j++){
                for(int k=0;k<=9;k++){
                    int num = i*100+j*10+k;
                    int value = i*i*i+j*j*j+k*k*k;
                    if(num== value){
                        System.out.println(num);
                    }
                }
            }
        }
    }

}
