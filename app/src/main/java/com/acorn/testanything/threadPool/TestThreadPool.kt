package com.acorn.testanything.threadPool

import java.util.concurrent.*

/**
 * https://www.jianshu.com/p/646e3f2f53d1
 * Created by acorn on 2020/1/28.
 */
fun main() {
    val threadPool = fixedThreadPool()
    for (i in 0..30) {
        val runnable = Runnable {
            Thread.sleep(3000)
            println("run:$i,当前线程:${Thread.currentThread().name}")
        }
        threadPool.execute(runnable)
    }

    //线程池常用方法
//    1.shutDown()  关闭线程池，不影响已经提交的任务
//    2.shutDownNow() 关闭线程池，并尝试去终止正在执行的线程
//    3.allowCoreThreadTimeOut(boolean value) 允许核心线程闲置超时时被回收
//    4.submit 一般情况下我们使用execute来提交任务，但是有时候可能也会用到submit，使用submit的好处是submit有返回值。
//    5.beforeExecute() - 任务执行前执行的方法
//    6.afterExecute() -任务执行结束后执行的方法
//    7.terminated() -线程池关闭后执行的方法
}

/**
 * 1.如果线程池中的线程数未达到核心线程数，则会立马启用一个核心线程去执行。
 * 2.如果线程池中的线程数已经达到核心线程数，且任务队列workQueue未满，则将新线程放入workQueue中等待执行。
 * 3.如果线程池中的线程数已经达到核心线程数但未超过线程池规定最大值，且workQueue已满，则开启一个非核心线程来执行任务。
 * 4.如果线程池中的线程数已经超过线程池规定最大值，则拒绝执行该任务，采取饱和策略，并抛出RejectedExecutionException异常。
 * 上面例子中设置的任务队列长度为50，任务只有30个，任务队列未满，只走到第二个流程，不会开启额外的5-3=2个非核心线程，
 * 如果将任务队列设为25，则前三个任务被核心线程执行，剩下的30-3=27个任务进入队列会满超出2个，
 * 此时会开启2个非核心线程来执行剩下的两个任务，这是刚好达到线程池的最大线程数，假如还有任务，
 * 将会拒绝执行，抛出RejectedExecutionException异常。
 */
fun basicThreadPool(): ThreadPoolExecutor {
//    corePoolSize： 线程池中核心线程的数量。
//    maximumPoolSize：线程池中最大线程数量。
//    keepAliveTime：非核心线程的超时时长，当系统中非核心线程闲置时间超过keepAliveTime之后，则会被回收。如果ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，则该参数也表示核心线程的超时时长。
//    unit：keepAliveTime这个参数的单位，有纳秒、微秒、毫秒、秒、分、时、天等。
//    workQueue：线程池中的任务队列，该队列主要用来存储已经被提交但是尚未执行的任务。存储在这里的任务是由ThreadPoolExecutor的execute方法提交来的。
//    threadFactory：为线程池提供创建新线程的功能，这个我们一般使用默认即可。
    return ThreadPoolExecutor(
        3, 5, 1,
        TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(50)
    )
}

/**
 * 结果：每3s打印5次任务，跟上面的基础线程池类似。
 * 特点：参数为核心线程数，只有核心线程，无非核心线程无超时时长，并且阻塞队列无界。
 * 适用：执行长期的任务，性能好很多
 */
fun fixedThreadPool(): ExecutorService {
    //源码实现
//    public static ExecutorService newFixedThreadPool(int nThreads) {
//        return new ThreadPoolExecutor(nThreads, nThreads,
//        0L, TimeUnit.MILLISECONDS,
//        new LinkedBlockingQueue<Runnable>());
//    }
    return Executors.newFixedThreadPool(5)
}

/**
 * 结果：每3s打印1次任务。
特点：只有一个核心线程，当被占用时，其他的任务需要进入队列等待,fixedThreadPool设置核心线程为1时就是SingleThreadPool。
适用：一个任务一个任务执行的场景
 */
fun singleThreadPool(): ExecutorService {
    //源码实现
//    public static ExecutorService newSingleThreadExecutor() {
//        return new FinalizableDelegatedExecutorService
//                (new ThreadPoolExecutor(1, 1,
//        0L, TimeUnit.MILLISECONDS,
//        new LinkedBlockingQueue<Runnable>()));
//    }
    return Executors.newSingleThreadExecutor()
}

/**
 * 结果：3s后打印30次任务。
特点：没有核心线程，只有非核心线程，并且每个非核心线程空闲等待的时间为60s，采用SynchronousQueue队列。


因为没有核心线程，其他全为非核心线程，SynchronousQueue是不存储元素的，每次插入操作必须伴随一个移除操作，
一个移除操作也要伴随一个插入操作。当一个任务执行时，先用SynchronousQueue的offer提交任务，
如果线程池中有线程空闲，则调用SynchronousQueue的poll方法来移除任务并交给线程处理；如果没有线程空闲，
则开启一个新的非核心线程来处理任务。由于maximumPoolSize是Integer.MAX_VALUE，无界的，
所以如果线程处理任务速度小于提交任务的速度，则会不断地创建新的线程，这时需要注意不要过度创建，
应采取措施调整双方速度，不然线程创建太多会影响性能。从其特点可以看出，
CachedThreadPool适用于有大量需要立即执行的耗时少的任务的情况。
适用：执行很多短期异步的小程序或者负载较轻的服务器
 */
fun cacheThreadPool(): ExecutorService {
    //源码实现
//    public static ExecutorService newCachedThreadPool() {
//        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
//        60L, TimeUnit.SECONDS,
//        new SynchronousQueue<Runnable>());
//    }
    return Executors.newCachedThreadPool()
}

/**
 * 特点：核心线程数量是固定的，非核心线程无穷大。当非核心线程闲置时，则会被立即回收。
ScheduledThreadPool也是四个当中唯一一个具有定时定期执行任务功能的线程池。它适合执行一些周期性任务或者延时任务。
适用：一个任务一个任务执行的场景
 */
fun scheduledThreadPool() {
    //源码实现
//    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
//        return new ScheduledThreadPoolExecutor(corePoolSize);
//    }
//    public ScheduledThreadPoolExecutor(int corePoolSize) {
//        super(corePoolSize, Integer.MAX_VALUE,
//            DEFAULT_KEEPALIVE_MILLIS, MILLISECONDS,
//            new DelayedWorkQueue());
//
//    }

    val runnable = Runnable {
        println("runnable")
    }
    val scheduledPool = Executors.newScheduledThreadPool(5)
    //延迟5秒执行
    scheduledPool.schedule(runnable, 5, TimeUnit.SECONDS);
    //延迟5s后启动，每1s执行一次
    scheduledPool.scheduleAtFixedRate(runnable, 5, 1, TimeUnit.SECONDS);
    //启动后第一次延迟5s执行，后面延迟1s执行
    scheduledPool.scheduleWithFixedDelay(runnable, 5, 1, TimeUnit.SECONDS);
}

/**
 * 手动取消线程
 * 一般线程执行完run方法之后，线程就正常结束了,此方法手动取消
 */
private fun cancelAThread() {
    val pool = Executors.newFixedThreadPool(2)

    val callable = Callable<String> {
        println("test")
        "true"
    }

    val f = pool.submit(callable)

    println(f.isCancelled)
    println(f.isDone)
    f.cancel(true)
}