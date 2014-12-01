package com.tfsinc.ilabs.references;

import com.awesome.pro.utilities.PropertyFileUtility;

/**
 * References related to configurations.
 * @author siddharth.s
 */
public class CubeExtractorConfigReferences {

	// Configuration files.
	public static final String FILE_CONFIG = "CubeExtractorConfig.properties";
	public static final String FILE_OLAP_CONFIG = "CubeExtractorOlapConfig.properties";
	public static final String FILE_THREAD_POOL_CONFIG = "CubeExtractorPerf.properties";
	public static final String FILE_CONTEXT = "CubeExtractorContext.properties";

	// Configuration properties.
	public static final String PROPERTY_MONGO_DATABASE = "DatabaseName";
	public static final String PROPERTY_TIME_FILTER = "Time";

	// Default configurations.
	public static final String DEFAULT_MONGO_DATABASE = "ReportingConfigurations";

	// Look up parameters
	public static final String LOOKUP_CLIENT = "Client";
	public static final String DIM_CLIENT = "[DimClient].[Client].&[";
	public static final char DIM_CLIENT_END = ']';

	/**
	 * Cached configurations
	 */
	public static PropertyFileUtility CONFIG = null;

	/**
	 * Initializes cached configurations.
	 */
	public static synchronized final void initialize() {
		CONFIG = new PropertyFileUtility(FILE_CONFIG);
	}

}
