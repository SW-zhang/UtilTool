package com.wang.utils.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 异步执行类
 * 
 *
 */
public final class SyncExcutor {

	private static ExecutorService pool = Executors.newCachedThreadPool();

	/***
	 * 异步执行关心执行结果
	 * 
	 * @param task
	 * @return
	 */
	public static Future<?> excute(Callable<?> task) {
		if (null == task) {
			return null;
		}
		return pool.submit(task);
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
	 * @param timeOut
	 *            超时设置 单位毫秒
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