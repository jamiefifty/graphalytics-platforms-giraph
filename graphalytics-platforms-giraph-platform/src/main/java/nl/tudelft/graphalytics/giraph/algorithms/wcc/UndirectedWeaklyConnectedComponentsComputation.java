/*
 * Copyright 2015 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.tudelft.graphalytics.giraph.algorithms.wcc;

import java.io.IOException;

import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;

/**
 * Undirected Connected Component algorithm.
 * Credits: mostly from Giraph example
 * https://github.com/apache/giraph/blob/trunk/giraph-examples/src/main/java/org/apache/giraph/examples/ConnectedComponentsComputation.java
 * <p/>
 * Bug Fixed (Wing)
 * - The vertex value was never initialized with the vertex id
 *
 * @author Wing Ngai
 */
public class UndirectedWeaklyConnectedComponentsComputation extends BasicComputation<LongWritable, LongWritable, NullWritable, LongWritable> {
	/**
	 * Propagates the smallest vertex id to all neighbors. Will always choose to
	 * halt and only reactivate if a smaller id has been sent to it.
	 */
	@Override
	public void compute(Vertex<LongWritable, LongWritable, NullWritable> vertex, Iterable<LongWritable> messages) throws IOException {
		// First superstep is special, because we can simply look at the neighbors
		if (getSuperstep() == 0) {
			// Initialize value to minimum id of neighbours
			long minId = vertex.getId().get();
			for (Edge<LongWritable, NullWritable> edge : vertex.getEdges()) {
				long targetVertexId = edge.getTargetVertexId().get();
				if (targetVertexId < minId) {
					minId = targetVertexId;
				}
			}

			// Store the new component id and broadcast it if it is not equal to this vertex's own id
			vertex.getValue().set(minId);
			if (minId != vertex.getId().get()) {
				sendMessageToAllEdges(vertex, vertex.getValue());
			}

			vertex.voteToHalt();
		} else {
			long currentComponent = vertex.getValue().get();

			// did we get a smaller id ?
			for (LongWritable message : messages) {
				long candidateComponent = message.get();
				if (candidateComponent < currentComponent) {
					currentComponent = candidateComponent;
				}
			}

			// propagate new component id to the neighbors
			if (currentComponent != vertex.getValue().get()) {
				vertex.getValue().set(currentComponent);
				sendMessageToAllEdges(vertex, vertex.getValue());
			}

			vertex.voteToHalt();
		}
	}
}