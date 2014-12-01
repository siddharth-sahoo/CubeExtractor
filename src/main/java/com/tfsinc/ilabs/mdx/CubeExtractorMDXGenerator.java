package com.tfsinc.ilabs.mdx;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.awesome.pro.utilities.db.mongo.MongoConnection;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tfsinc.ilabs.job.CubeExtractorJobExecutor;
import com.tfsinc.ilabs.job.CubeExtractorRunnableJob;
import com.tfsinc.ilabs.main.CubeExtractorMain;
import com.tfsinc.ilabs.mdx.builder.MdxQueryBuilder;
import com.tfsinc.ilabs.mdx.builder.dimension.DimensionBuilder;
import com.tfsinc.ilabs.olap.OlapClientManager;
import com.tfsinc.ilabs.references.CubeExtractorConfigReferences;
import com.tfsinc.ilabs.references.CubeExtractorMongoReferences;

/**
 * Executes the necessary MDX query and builds the context.
 * @author siddharth.s
 */
public class CubeExtractorMDXGenerator {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(CubeExtractorMDXGenerator.class);

	/**
	 * Initializes all connections.
	 */
	public static synchronized final void initialize() {
		OlapClientManager.initialize(CubeExtractorConfigReferences.FILE_OLAP_CONFIG);
		MongoConnection.initialize(CubeExtractorConfigReferences.FILE_CONFIG);
	}

	/**
	 * Shuts down all live connections.
	 */
	public static synchronized final void shutdown() {
		OlapClientManager.shutdown(true);
	}

	/**
	 * Starts reading configurations and starts populating Cassandra.
	 */
	public static final void start() {
		final DBCursor cursor = MongoConnection.getDocuments(
				CubeExtractorConfigReferences.CONFIG.getStringValue(
						CubeExtractorConfigReferences.PROPERTY_MONGO_DATABASE,
						CubeExtractorConfigReferences.DEFAULT_MONGO_DATABASE),
						CubeExtractorMongoReferences.COLLECTION_MEASURE_GROUPS);

		while (cursor.hasNext()) {
			final BasicDBObject obj = (BasicDBObject) cursor.next();

			final BasicDBList measures = (BasicDBList) obj.
					get(CubeExtractorMongoReferences.FIELD_MEASURE_DEF_MEASURES);
			final int size = measures.size();
			final List<String> measureNames = new LinkedList<>();

			for (int i = 0; i < size; i ++) {
				final BasicDBObject measure = (BasicDBObject) measures.get(i);
				measureNames.add(measure.get(
						CubeExtractorMongoReferences.FIELD_MEASURE_DEF_NAME).toString());
			}

			final String mdx = MdxQueryBuilder.getQueryBuilder().
					setCube(obj.getString(
							CubeExtractorMongoReferences.FIELD_MEASURE_DEF_CUBE_NAME))
							.setMeasures(measureNames)
							.addDimension(1, DimensionBuilder.getDimensionBuilder()
									.setNonEmpty(true)
									.setCrossJoin(false)
									.setAxis(1)
									.addMembers(getMeasureDimensions(obj.getString(
											CubeExtractorMongoReferences.FIELD_MEASURE_DEF_GROUP_NAME)
											)).build()
									)
									.addFilters(CubeExtractorFilterLookup.getFilters())
									.build();
			
			CubeExtractorJobExecutor.EXECUTOR.execute(
					new CubeExtractorRunnableJob(mdx));
		}
	}

	/**
	 * @param measureGroup Name of the measure group
	 * @return List of applicable dimensions.
	 */
	private static final String[] getMeasureDimensions(
			final String measureGroup) {
		final DBObject query = new BasicDBObject(
				CubeExtractorMongoReferences.FIELD_MEASURE_DIMENSION_GROUP_NAME,
				measureGroup);
		final DBCursor cursor = MongoConnection.getDocuments(
				CubeExtractorConfigReferences.CONFIG.getStringValue(
						CubeExtractorConfigReferences.PROPERTY_MONGO_DATABASE,
						CubeExtractorConfigReferences.DEFAULT_MONGO_DATABASE),
						CubeExtractorMongoReferences.COLLECTION_MEASURE_GROUP_DIMENSIONS,
						query);

		if (cursor == null || !cursor.hasNext()) {
			LOGGER.error("No measure group dimension configurations were found.");
			CubeExtractorMain.fail("No configurations were found.");
		}

		if (cursor.size() > 1) {
			LOGGER.error("Multiple mappings found for measure group: " + measureGroup);
			CubeExtractorMain.fail("Duplicate configurations were found.");
		}

		final BasicDBObject measure = (BasicDBObject) cursor.next();
		final BasicDBList dimensions = (BasicDBList) measure.get(
				CubeExtractorMongoReferences.FIELD_MEASURE_DIMENSION_LIST);
		final int size = dimensions.size();
		final String[] list = new String[size];


		for (int i = 0; i < size; i ++) {
			list[i] = dimensions.get(i).toString();
		}

		return list;
	}

}
