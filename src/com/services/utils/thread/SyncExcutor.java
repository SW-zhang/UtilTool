package com.services.utils.thread;

import java.util.concurrent.*;

/**
 * 异步执行类
 */
public final class SyncExcutor {

    private static final int corePoolSize = 8;//核心线程数量
    private static final int maximumPoolSize = 32;//最大线程数量
    private static final int CAPACITY = 1000;//队列任务最大数量
    private static final long keepAliveTime = 60L;//超过核心线程最大空闲时间，过了空闲时间就回收

    // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
    public static final ThreadPoolExecutor pool = new ThreadPoolExecutor(
            corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(CAPACITY));

    /***
     * 异步执行关心执行结果
     *
     * @param task
     * @return
     */
    public static Future<?> excute(Callable<?> task) {
        try {
            if (null == task) {
                return null;
            }
            return pool.submit(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 异步执行不需要关心执行结果
     *
     * @param taskList
     */
    public static void excute(Runnable... taskList) {
        if (null == taskList) {
            return;
        }
        for (Runnable task : taskList) {
            pool.execute(task);
        }
    }

    /***
     * 得到异步执行结果
     *
     * @param future
     * @param timeOut 超时设置 单位毫秒
     * @return
     * @throws RuntimeException
     */
    public static Object getExcuteResult(Future<?> future, long timeOut) throws RuntimeException {
        if (null == future) {
            return null;
        }
        try {
            return future.get(timeOut, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(SyncExcutor.class + " 中断异常");
        } catch (ExecutionException e) {
            throw new RuntimeException(SyncExcutor.class + " 执行异常");
        } catch (TimeoutException e) {
            throw new RuntimeException(SyncExcutor.class + " 超时异常");
        }
    }

    /***
     * 得到异步执行结果 默认超时时间 10 秒
     *
     * @param future
     * @return
     * @throws RuntimeException
     */
    public static Object getExcuteResult(Future<?> future) throws RuntimeException {
        return getExcuteResult(future, 10000L);
    }

    public static void shutDown() {
        if (!pool.isShutdown()) {
            pool.shutdown();
        }
    }
}