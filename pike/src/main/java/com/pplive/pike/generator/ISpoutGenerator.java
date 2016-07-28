package com.pplive.pike.generator;

import java.util.Map;

import com.pplive.pike.base.Period;
import com.pplive.pike.exec.spout.PikeBatchSpout;

public interface ISpoutGenerator {

	public PikeBatchSpout create(String topologyName, String tableName, String[] requiredColumns, Period period, Map<String, Object> conf);
}
