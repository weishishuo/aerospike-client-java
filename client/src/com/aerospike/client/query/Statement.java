/* 
 * Copyright 2012-2015 Aerospike, Inc.
 *
 * Portions may be licensed to Aerospike, Inc. under one or more contributor
 * license agreements.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.aerospike.client.query;

import com.aerospike.client.Value;

/**
 * Query statement parameters.
 */
public final class Statement {	
	String namespace;
	String setName;
	String indexName;
	String[] binNames;
	Filter[] filters;
	ClassLoader resourceLoader;
	String resourcePath;
	String packageName;
	String functionName;
	Value[] functionArgs;
	long taskId;
	boolean returnData;
	
	/**
	 * Set query namespace.
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	/**
	 * Get query namespace.
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * Set optional query setname.
	 */
	public void setSetName(String setName) {
		this.setName = setName;
	}

	/**
	 * Get optional query setname.
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * Set optional query index name.  If not set, the server
	 * will determine the index from the filter's bin name.
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * Get optional query index name.
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * Set query bin names.
	 */
	public void setBinNames(String... binNames) {
		this.binNames = binNames;
	}
	
	/**
	 * Get query bin names.
	 */
	public String[] getBinNames() {
		return binNames;
	}

	/**
	 * Set optional query filters.
	 * Currently, only one filter is allowed by the server on a secondary index lookup.
	 * If multiple filters are necessary, see QueryFilter example for a workaround.
	 * QueryFilter demonstrates how to add additional filters in an user-defined 
	 * aggregation function. 
	 */
	public void setFilters(Filter... filters) {
		this.filters = filters;
	}

	/**
	 * Return query filters.
	 */
	public Filter[] getFilters() {
		return filters;
	}

	/**
	 * Set optional query task id.
	 */
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	/**
	 * Return task ID.
	 */
	public long getTaskId() {
		return taskId;
	}

	/**
	 * Set Lua aggregation function parameters for a Lua package located on the filesystem.
	 * This function will be called on both the server and client for each selected item.
	 * 
	 * @param packageName			server package where user defined function resides
	 * @param functionName			aggregation function name
	 * @param functionArgs			arguments to pass to function name, if any
	 */
	public void setAggregateFunction(String packageName, String functionName, Value... functionArgs) {
		this.packageName = packageName;
		this.functionName = functionName;
		this.functionArgs = functionArgs;
	}

	/**
	 * Set Lua aggregation function parameters for a Lua package located in a resource file.
	 * This function will be called on both the server and client for each selected item.
	 * 
	 * @param resourceLoader		class loader where resource is located.  Example: MyClass.class.getClassLoader() or Thread.currentThread().getContextClassLoader() for webapps
	 * @param resourcePath          class path where Lua resource is located
	 * @param packageName			server package where user defined function resides
	 * @param functionName			aggregation function name
	 * @param functionArgs			arguments to pass to function name, if any
	 */
	public void setAggregateFunction(ClassLoader resourceLoader, String resourcePath, String packageName, String functionName, Value... functionArgs) {
		this.resourceLoader = resourceLoader;
		this.resourcePath = resourcePath;
		this.packageName = packageName;
		this.functionName = functionName;
		this.functionArgs = functionArgs;
	}

	/**
	 * Return resource class loader.
	 */
	public ClassLoader getResourceLoader() {
		return resourceLoader;
	}

	/**
	 * Return resource path.
	 */
	public String getResourcePath() {
		return resourcePath;
	}

	/**
	 * Return aggregation file name.
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Return aggregation function name.
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * Return aggregation function arguments.
	 */
	public Value[] getFunctionArgs() {
		return functionArgs;
	}	

	/**
	 * Does command return data.
	 */
	public boolean returnData() {
		return returnData;
	}

	/**
	 * Prepare statement just prior to execution.  For internal use.
	 */
	public void prepare(boolean returnData) {
		this.returnData = returnData;
		
		if (taskId == 0) {
			taskId = System.nanoTime();
		}
	}

	/**
	 * Return if full namespace/set scan is specified.
	 */
	public boolean isScan() {
		return filters == null;
	}
}
