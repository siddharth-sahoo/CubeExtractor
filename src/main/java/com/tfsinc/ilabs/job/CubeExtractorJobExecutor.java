package com.tfsinc.ilabs.job;

import com.awesome.pro.executor.IThreadPool;
import com.awesome.pro.executor.ThreadPool;
import com.tfsinc.ilabs.references.CubeExtractorConfigReferences;

/**
 * Wrapper class for thread pool executor for cube extraction
 * making it available in a static context.
 * @author siddharth.s
 */
public class CubeExtractorJobExecutor {

	/**
	 * Thread pool executor.
	 */
	public static IThreadPool EXECUTOR = null;

	/**
	 * Initializes the thread pool executor.
	 */
	public static synchronized final void initialize() {
		EXECUTOR = new ThreadPool(
				CubeExtractorConfigReferences.FILE_THREAD_POOL_CONFIG);
		EXECUTOR.start();
	}

	/**
	 * Shuts down the thread pool executor.
	 */
	public static synchronized final void shutdown() {
		EXECUTOR.shutdown();
	}

	/**
	 * Waits for completion of all runnable jobs.
	 */
	public static synchronized final void waitForCompletion() {
		EXECUTOR.waitForCompletion();
	}

}
