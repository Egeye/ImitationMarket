package com.or.googlemarket.manager;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * �����̳߳�
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

	// ������ʱ����ȡ�����ļ�����Ӧ�÷���ͬһ�̳߳�
	// �����̳߳�,�����ٸ��̺߳��ʣ�CPU�ĺ���*2+1��
	// synchronized �����̳߳�ͬ��
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
	 * �̳߳صĴ������
	 * 
	 * @author Octavio
	 *
	 */
	public class ThreadPoolProxy {
		ThreadPoolExecutor pool;

		// �̸߳���
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
				// �����̳߳�
				pool = new ThreadPoolExecutor(
						// �̳߳�������Թ�����ٸ��߳�
						corePoolSize,
						// ����Ŷ����ˣ����⿪���߳���
						maximumPoolSize,
						// ����̳߳��Ѿ�û��Ҫִ�е����񣬴����
						time,
						// ʱ��ĵ�λ
						TimeUnit.MILLISECONDS,
						// ����̳߳��������̶߳��Ѿ����ˣ�ʣ�µ���������ʱ�浽�ö����У����10���Ŷ�
						new LinkedBlockingDeque<Runnable>(10));
			}

			// ֱ�ӵ����̳߳أ�ִ���첽����
			pool.execute(runnable);
		}

		public void cancelExecute(Runnable runnable) {
			// �̳߳ز�Ϊ�գ����������Ƿ�ֹͣ
			if (pool != null && !pool.isShutdown() && pool.isTerminated()) {
				// ȡ���첽����
				pool.remove(runnable);
			}
		}
	}
}
