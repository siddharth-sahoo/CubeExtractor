package com.tfsinc.ilabs.job;

import org.apache.log4j.Logger;
import org.olap4j.CellSet;

import com.tfsinc.ilabs.olap.OlapClientManager;

/**
 * An instance of this class processes a single measure group.
 * It receives an MDX query, executes it and populates context
 * using context builder.
 * @author siddharth.s
 */
public class CubeExtractorRunnableJob implements Runnable {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			CubeExtractorRunnableJob.class);

	/**
	 * MDX query to be executed.
	 */
	private final String query;

	/**
	 * @param mdx MDX query to be executed.
	 */
	public CubeExtractorRunnableJob(final String mdx) {
		query = mdx;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LOGGER.info(query);
		final CellSet cellSet = OlapClientManager.executeMdxQuery(query);
		
	}

}
