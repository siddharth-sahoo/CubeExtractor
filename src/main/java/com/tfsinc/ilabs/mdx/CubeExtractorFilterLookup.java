package com.tfsinc.ilabs.mdx;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tfsinc.ilabs.job.DimensionLookupFilter;
import com.tfsinc.ilabs.references.CubeExtractorConfigReferences;

/**
 * This class takes care of generating members for filter
 * in the MDX query.
 * @author siddharth.s
 */
public class CubeExtractorFilterLookup {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(CubeExtractorFilterLookup.class);

	/**
	 * Cached filter members.
	 */
	private static final List<String> FILTERS = new LinkedList<>();

	/**
	 * Initializes and caches the filters to be used in MDX queries.
	 */
	public static synchronized final void initialize() {
		if (FILTERS.size() == 0) {
			FILTERS.add(CubeExtractorConfigReferences.DIM_CLIENT
					+ DimensionLookupFilter.getFilterValue(
							CubeExtractorConfigReferences.LOOKUP_CLIENT)
					+ CubeExtractorConfigReferences.DIM_CLIENT_END);
			FILTERS.add(CubeExtractorConfigReferences.CONFIG.getStringValue(
					CubeExtractorConfigReferences.PROPERTY_TIME_FILTER));
		}
		LOGGER.info("Look up filters initialized for cube extraction.");
	}
	

	/**
	 * @return List of fully qualified filters to be used in MDX queries.
	 * Currently, only client and time filters are applied. They are as
	 * configured in the property file.
	 */
	public static final List<String> getFilters() {
		return FILTERS;
	}

}
