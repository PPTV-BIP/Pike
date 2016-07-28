package com.pplive.pike.metadata;

import com.pplive.pike.util.MetaUtil;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by jiatingjin on 2016/7/26.
 */
public class RawMetaDataSource implements ITableInfoProvider {

    private HashMap<String, Table> tables = new HashMap<>();

    public void addTable(com.pplive.pike.client.Table table) {
        Table t = MetaUtil.convertTable(table);
        tables.put(t.getName(), t);
    }

    public TableManager getTableManager() {
        return new TableManager(this);
    }

    @Override
    public Table getTable(String name) {
        return  tables.get(name);
    }

    @Override
    public String[] getTableNames() {
        return tables.keySet().toArray(new String[]{});
    }

    @Override
    public long getTableBytesByHour(String name) {
        return 0;
    }

    @Override
    public void registColumns(String id, String tableName, Set<String> columns) {

    }
}
