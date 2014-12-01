package com.tfsinc.ilabs.main;

import org.apache.log4j.Logger;

import com.tfsinc.ilabs.context.CubeExtractorContextPopulator;
import com.tfsinc.ilabs.job.CubeExtractorJobExecutor;
import com.tfsinc.ilabs.mdx.CubeExtractorFilterLookup;
import com.tfsinc.ilabs.mdx.CubeExtractorMDXGenerator;
import com.tfsinc.ilabs.references.CubeExtractorConfigReferences;

/**
 * The main class of the project which serves as the entry and
 * exit point of executions.
 * @author siddharth.s
 */
public class CubeExtractorMain {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(CubeExtractorMain.class);

	/**
	 * Takes care of all initializations.
	 */
	private static synchronized final void start() {
		CubeExtractorConfigReferences.initialize();
		CubeExtractorFilterLookup.initialize();
		CubeExtractorMDXGenerator.initialize();
		CubeExtractorJobExecutor.initialize();
		//CubeExtractorContextPopulator.initialize();
		LOGGER.info("Initializations for cube extraction compelte.");
	}

	public static void main(String[] args) {
		start();
		CubeExtractorMDXGenerator.start();
		stop();
	}

	/**
	 * Shuts down and tears down all initializations.
	 */
	private static synchronized final void stop() {
		//CubeExtractorContextPopulator.shutdown();
		CubeExtractorJobExecutor.shutdown();
		CubeExtractorMDXGenerator.shutdown();
		LOGGER.info("Cube extractor shutting down.");
	}

	/**
	 * Serves as a single exit point.
	 * @param reason Reason for failure.
	 */
	public static synchronized  void fail(final String reason) {
		LOGGER.error(reason);
		System.exit(1);
	}

}
