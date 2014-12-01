package com.tfsinc.ilabs.references;

/**
 * This class contains constants related to MongoDB
 * collections and fields.
 * @author siddharth.s
 */
public class CubeExtractorMongoReferences {

	// Collection names.
	public static final String COLLECTION_MEASURE_GROUPS = "MeasureGroups";
	public static final String COLLECTION_MEASURE_GROUP_DIMENSIONS = "MeasureGroupDimensions";

	// Measure group dimension collection fields.
	public static final String FIELD_MEASURE_DIMENSION_GROUP_NAME = "measureGroup";
	public static final String FIELD_MEASURE_DIMENSION_LIST = "dimensions";

	// Measure group defintion collection fields.
	public static final String FIELD_MEASURE_DEF_GROUP_NAME = "measureGroup";
	public static final String FIELD_MEASURE_DEF_CUBE_NAME = "cube";
	public static final String FIELD_MEASURE_DEF_MEASURES = "measures";
	public static final String FIELD_MEASURE_DEF_NAME = "name";
	public static final String FIELD_MEASURE_DEF_SOURCE_TABLE = "sourceTable";
	public static final String FIELD_MEASURE_DEF_OPERATOR = "operator";
	public static final String FIELD_MEASURE_DEF_SOURCE_COLUMN = "sourceColumn";
	
	

}
