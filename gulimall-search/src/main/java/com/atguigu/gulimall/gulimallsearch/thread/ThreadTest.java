package com.atguigu.gulimall.gulimallsearch.thread;

import lombok.SneakyThrows;

import java.util.concurrent.*;

public class ThreadTest {
    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("main....start....");
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果：" + i);
//        }, executor);
        /*
        * 方法执行完成后的感知
        * */
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            int i = 10 / 0;
//            System.out.println("运行结果：" + i);
//            return i;
//        }, executor).whenComplete((res,exception)->{
////            虽然能得到异常信息,但是没法修改返回数据.
//            System.out.println("异步任务成功完成了....结果是:"+res+";异常是:"+exception);
//        }).exceptionally(throwable -> {
////            可以感知异常,同时返回默认值
//            return 10;
//        });
        /*
        * 方法执行完成后的处理
        * */
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            int i = 10 / 4;
//            System.out.println("运行结果：" + i);
//            return i;
//        }, executor).handle((res,thr)->{
//            if (res!=null){
//                return res*2;
//            }
//            if (thr!=null){
//                return 0;
//            }
//            return 0;
//        });
        /*
        * 线程串行化
        * 1)、thenRun:不能获取到上一步的执行结果，无返回值
        * .thenRunAsync(() -> {
                    System.out.println("任务2启动了...");
                },executor);
          2)、能接收上一步结果，但是无返回值
         .thenAcceptAsync(res->{
                    System.out.println("任务2启动了..."+res);
                },executor);
           3)、既能接收上一步结果，又有返回值
        .thenApplyAsync(res -> {
            System.out.println("任务2启动了...");
            return "Hello" + res;
        }, executor);
        * */
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getId());
//            int i = 10 / 4;
//            System.out.println("运行结果：" + i);
//            return i;
//        }, executor).thenApplyAsync(res -> {
//            System.out.println("任务2启动了...");
//            return "Hello" + res;
//        }, executor);
        /*
        * 两个都完成
        * */
//        CompletableFuture<Object> future01 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("任务1线程：" + Thread.currentThread().getId());
//            int i = 10 / 4;
//            System.out.println("任务1结束：" + i);
//            return i;
//        }, executor);
//        CompletableFuture<Object> future02 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("任务2线程：" + Thread.currentThread().getId());
//            try {
//                Thread.sleep(3000);
//                System.out.println("任务2结束：");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "Hello";
//        }, executor);
//        future01.thenAcceptBothAsync(future02,(f1,f2)->{
//            System.out.println("任务3开始...之前的结果："+f1+"-->"+f2);
//        },executor);
//        CompletableFuture<String> future = future01.thenCombineAsync(future02, (f1, f2) -> {
//            return f1 + ":" + f2 + "-> Haha";
//        }, executor);
        /*
        * 两个任务，只要有一个完成，我们就执行任务3
        * runAfterEitherAsync:不感知结果，自己也无返回值
        * acceptEitherAsync:感知结果，没有返回值
        * applyToEitherAsync:感知结果，有返回值
        * */
//        future01.runAfterEitherAsync(future02,()->{
//            System.out.println("任务3开始...之前的结果");
//        },executor);
//        future01.acceptEitherAsync(future02,(res)->{
//            System.out.println("任务3开始...之前的结果"+res);
//        },executor);
//        CompletableFuture<String> future = future01.applyToEitherAsync(future02, (res) -> {
//            System.out.println("任务3开始...之前的结果" + res);
//            return res.toString() + "->哈哈";
//        }, executor);
        CompletableFuture<String> futureImg = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的图片信息");
            return "hello.jpg";
        },executor);
        CompletableFuture<String> futureAttr = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的属性");
            return "黑色+256G";
        },executor);
        CompletableFuture<String> futureDesc = CompletableFuture.supplyAsync(() -> {
                System.out.println("查询商品的介绍");
            return "华为";
        },executor);
//        CompletableFuture<Void> allOf = CompletableFuture.allOf(futureImg, futureAttr, futureDesc);
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(futureImg, futureAttr, futureDesc);
        anyOf.get();//等待所有线程完成
        System.out.println("main....end...."+anyOf.get());
    }

    public  void thread(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main....start....");
        /*
        * 1)、继承Thread
        *       Thread01 thread = new Thread01();
                thread.start();//启动线程
                System.out.println("main....end....");
        * 2)、实现Runnable接口
        *       Runable01 runable01=new Runable01();
        *       new Thread(runable01).start();
        * 3)、实现Callable接口+FutureTask(可以拿到返回结果,可以处理异常)
        *       FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
                new Thread(futureTask).start();
//        获取返回结果
                Integer integer = futureTask.get();
        * 4)、线程池
        *       给线程池提交任务。
        *     1、创建
        *           1）、Executors
        *           2）、
        * 区别：
        *       1、2不能得到返回值。3可以获取返回值
        *       1、2、3都不能控制资源执行
        *       4可以控制资源，性能稳定。
        * */
//        我们以后在业务代码里面，以上三种启动线程的方式都不用。将所有的多线程异步任务都交给线程池执行
//        当前系统中池只有一两个，每个异步任务，提交给线程池让他自己去执行
        /*
         * 七大参数
         * corePoolSize:[5]核心线程数[一直存在除非（allowCoreThreadTimeOut）]；线程池，创建好以后就准备就绪的线程数量，就等待来就收异步任务去执行
         *      5个 Thread thread=new Thread();  thread.start();
         * maximumPoolSize:最大线程数量;控制资源
         * keepAliveTime:存活时间。当前如果正在运行的线程数量大于Core数量。
         *       释放空闲的线程(maximumPoolSize-corePoolSize)。只要线程空闲大于指定的keepAliveTime;
         * unit:时间单位
         * BlockingQueue<Runnable> workQueue:阻塞队列，如果任务有很多，就会将目前多的任务放在队列里面。
         *               只要有线程空闲，就回去队列里面取出新的任务去执行
         * threadFactory:线程的创建工厂。
         * RejectedExecutionHandler handler:如果队列满了，按照我们指定的拒绝策略拒绝执行任务
         * 工作顺序：
         * 1)、线程池创建，准备好core数量的核心线程，准备接受任务
         * 1.1、core满了，就将再进来的任务放入阻塞队列中。空闲的core就会自己去阻塞队列获取任务执行
         * 1.2、阻塞队列满了，就直接开新线程执行，最大只能开到max指定的数量
         * 1.3、max满了就用RejectedExecutionHandler拒绝任务
         * 1.4、max都执行完成，有很多空闲，在指定的事件以后keepAliveTime以后，释放max-core这些线程
         *      new LinkedBlockingDeque<>(),默认是Integer的最大值。内存不够
         *面试题：一个线程池 core:7，max:20，queue：50,100并发进来怎么分配的
         * 7个会立即执行，50个会进入队列，再开13个进行执行。剩下的30个使用拒绝策略(一般都是丢弃)
         * 如果不想抛弃还要执行。CallerRunsPolicy;
         * */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
                200,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
//        Executors.newCachedThreadPool(),core是0，所有都可回收，
//        Executors.newFixedThreadPool(),固定大小，core=max,都不可回收
//        Executors.newScheduledThreadPool(),定时任务的线程池
//        Executors.newSingleThreadExecutor(),单线程的线程池,后台从队列里面获取任务,挨个执行
        executor.execute(new Runable01());
        System.out.println("main....end....");
    }

    public static class Thread01 extends Thread {
        @Override
        public void run() {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        }
    }

    public static class Runable01 implements Runnable {
        @Override
        public void run() {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        }
    }

    public static class Callable01 implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }
    }
}
