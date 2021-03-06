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
package nl.tudelft.graphalytics.giraph.algorithms.pr;

import org.apache.giraph.aggregators.DoubleSumAggregator;
import org.apache.giraph.master.DefaultMasterCompute;

import static nl.tudelft.graphalytics.giraph.algorithms.pr.PageRankConfiguration.DANGLING_NODE_SUM;

/**
 * Master computation for the PageRank algorithm to manage tracking of the sum of dangling node values.
 *
 * @author Tim Hegeman
 */
public class PageRankMasterComputation extends DefaultMasterCompute {

	@Override
	public void initialize() throws InstantiationException, IllegalAccessException {
		registerAggregator(DANGLING_NODE_SUM, DoubleSumAggregator.class);
	}

}
