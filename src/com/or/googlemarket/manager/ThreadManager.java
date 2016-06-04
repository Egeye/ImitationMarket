package com.or.googlemarket.manager;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 管理线程池
 * 
 * @author Octavio
 *
 */
public class ThreadManager {

	private ThreadManager() {

	}

	private static ThreadManager instance = new ThreadManager();
	private ThreadPoolProxy longPoolProxy;
	private ThreadPoolProxy shortPoolProxy;

	public static ThreadManager getInstance() {
		return instance;

	}

	// 联网耗时，读取本地文件，不应该放在同一线程池
	// 创建线程池,开多少个线程合适：CPU的核数*2+1，
	// synchronized 保障线程池同步
	public synchronized ThreadPoolProxy createLongPool() {
		if (longPoolProxy == null) {
			longPoolProxy = new ThreadPoolProxy(5, 5, 5000L);
		}
		return longPoolProxy;
	}

	public synchronized ThreadPoolProxy createShortPool() {
		if (shortPoolProxy == null) {
			shortPoolProxy = new ThreadPoolProxy(3, 3, 5000L);
		}
		return shortPoolProxy;
	}

	/**
	 * 线程池的代理对象
	 * 
	 * @author Octavio
	 *
	 */
	public class ThreadPoolProxy {
		ThreadPoolExecutor pool;

		// 线程个数
		private int corePoolSize;
		private int maximumPoolSize;
		private long time;

		public ThreadPoolProxy(int corePoolSize, int maximumPoolSie, long time) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSie;
			this.time = time;
		}

		public void execute(Runnable runnable) {
			if (pool == null) {
				// 创建线程池
				pool = new ThreadPoolExecutor(
						// 线程池里面可以管理多少个线程
						corePoolSize,
						// 如果排队满了，额外开的线程数
						maximumPoolSize,
						// 如果线程池已经没有要执行的任务，存活多久
						time,
						// 时间的单位
						TimeUnit.MILLISECONDS,
						// 如果线程池里管理的线程都已经用了，剩下的任务都先临时存到该对象中，最多10个排队
						new LinkedBlockingDeque<Runnable>(10));
			}

			// 直接调用线程池，执行异步任务
			pool.execute(runnable);
		}

		public void cancelExecute(Runnable runnable) {
			// 线程池不为空，不崩溃，是否停止
			if (pool != null && !pool.isShutdown() && pool.isTerminated()) {
				// 取消异步任务
				pool.remove(runnable);
			}
		}
	}
}
