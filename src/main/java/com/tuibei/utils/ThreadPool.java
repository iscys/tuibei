package com.tuibei.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private static ExecutorService executorService =Executors.newFixedThreadPool(8);

    public static ExecutorService getThreadPool(){

      return executorService;

    }

}
