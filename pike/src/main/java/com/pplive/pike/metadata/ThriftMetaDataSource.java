package com.pplive.pike.metadata;

import java.util.Set;

/**
 * Created by jiatingjin on 2016/7/28.
 */
public class ThriftMetaDataSource implements ITableInfoProvider {
    @Override
    public Table getTable(String name) {
        return null;
    }

    @Override
    public String[] getTableNames() {
        return new String[0];
    }

    @Override
    public long getTableBytesByHour(String name) {
        return 0;
    }

    @Override
    public void registColumns(String id, String tableName, Set<String> columns) {

    }
}
