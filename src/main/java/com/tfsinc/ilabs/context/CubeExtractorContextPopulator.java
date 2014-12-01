package com.tfsinc.ilabs.context;

import com.awesome.pro.context.ContextBuilder;
import com.awesome.pro.context.ContextFactory;
import com.awesome.pro.db.cassandra.client.CassandraClientManager;
import com.tfsinc.ilabs.references.CubeExtractorConfigReferences;

/**
 * Wrapper class for context builder, thus making it avaiable
 * in a static context.
 * @author siddharth.s
 */
public class CubeExtractorContextPopulator {

	/**
	 * Context builder instance.
	 */
	public static ContextBuilder CONTEXT = null;

	public static final String CONTEXT_NAME = "CubeContext";

	/**
	 * Initializes the context builder instance.
	 */
	public static synchronized final void initialize() {
		CassandraClientManager.initialize(
				CubeExtractorConfigReferences.FILE_CONTEXT);
		CONTEXT = ContextFactory.getContextBuilder(CONTEXT_NAME);
	}

	/**
	 * Shuts down the context builder.
	 */
	public static synchronized final void shutdown() {
		CONTEXT.close();
	}

}
