package org.tudelft.graphalytics.giraph.conn;

import org.apache.giraph.conf.GiraphConfiguration;
import org.apache.giraph.graph.Computation;
import org.apache.giraph.io.VertexInputFormat;
import org.apache.giraph.io.VertexOutputFormat;
import org.apache.giraph.io.formats.IdWithValueTextOutputFormat;
import org.apache.giraph.io.formats.LongLongNullTextInputFormat;
import org.tudelft.graphalytics.giraph.GiraphJob;

public class ConnectedComponentJob extends GiraphJob {

	public ConnectedComponentJob(String inputPath, String outputPath, String zooKeeperAddress) {
		super(inputPath, outputPath, zooKeeperAddress);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends Computation> getComputationClass() {
		return ConnectedComponentComputation.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends VertexInputFormat> getVertexInputFormatClass() {
		return LongLongNullTextInputFormat.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends VertexOutputFormat> getVertexOutputFormatClass() {
		return IdWithValueTextOutputFormat.class;
	}

	@Override
	protected void configure(GiraphConfiguration config) {
		// No configuration necessary
	}

}
