package com.pplive.pike.generator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.pplive.pike.base.Period;
import com.pplive.pike.exec.spout.PikeBatchSpout;
import com.pplive.pike.exec.spout.TextFileSpout;
import com.pplive.pike.metadata.ITableInfoProvider;

public class LocalTextFileSpoutGenerator implements ISpoutGenerator {
	
	private final ITableInfoProvider _tableInfoProvider;
	private final String _tableDataFile;
	
	public LocalTextFileSpoutGenerator(ITableInfoProvider tableInfoProvider, String tableDataFile){
		if (tableInfoProvider == null)
			throw new IllegalArgumentException("tableInfoProvider cannot be null");
		if (tableDataFile == null || tableDataFile.isEmpty())
			throw new IllegalArgumentException("tableDataFile cannot be null or empty");
		
		this._tableInfoProvider = tableInfoProvider;
		this._tableDataFile = tableDataFile;
	}

	@Override
	public PikeBatchSpout create(String topologyName, String tableName, String[] requiredColumns, Period period, Map<String, Object> conf){
		if (topologyName == null || topologyName.isEmpty())
			throw new IllegalArgumentException("topologyName cannot be null or empty");
		if (tableName == null || tableName.isEmpty())
			throw new IllegalArgumentException("tableName cannot be null or empty");
		if (period == null)
			throw new IllegalArgumentException("period cannot be null");

		// TODO, support multiple table data files.
		final String spoutName = generateSpoutName(topologyName, tableName);
		TextFileSpout spout = new TextFileSpout(spoutName, this._tableDataFile, this._tableInfoProvider, 
				tableName, period.periodSeconds());
		return spout;
	}
	
	private static String generateSpoutName(String topologyName, String tableName) {
		return String.format("%s_%s_%s" , topologyName, tableName, getNowTimeString());
	}
	
	private static String getNowTimeString() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
	}
}
