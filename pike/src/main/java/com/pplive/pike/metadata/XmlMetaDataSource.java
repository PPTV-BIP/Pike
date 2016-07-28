package com.pplive.pike.metadata;

import com.pplive.pike.Configuration;
import com.pplive.pike.util.MetaUtil;
import com.pplive.pike.util.SerializeUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;
import java.util.Set;

/**
 * Created by jiatingjin on 2016/7/28.
 */

public class XmlMetaDataSource implements ITableInfoProvider {

    private Table[] tables;

    @XmlRootElement(name="tables")
    private static class _Tables {
        @XmlElement(name = "table")
        com.pplive.pike.client.Table[] tables;
    }

    public XmlMetaDataSource(String tableInfoFile) {
        try {
            _Tables _tables = SerializeUtils.xmlDeserialize(_Tables.class, tableInfoFile);
            this.tables = convertTable(_tables);
        }
        catch(RuntimeException e){
            System.err.println(String.format("read table info failed from file %s", tableInfoFile));
            throw(e);
        }
    }

    public XmlMetaDataSource(Configuration conf) {
        this(conf.getString(Configuration.XmlMetaDataSource));
    }

    private XmlMetaDataSource() {

    }

    public static XmlMetaDataSource createDirectly(String fileContentXml) {
        try {
            XmlMetaDataSource result = new XmlMetaDataSource();
            _Tables _tables = SerializeUtils.xmlDeserialize(_Tables.class, new StringReader(fileContentXml));
            result.tables = convertTable(_tables);
            return result;
        } catch (RuntimeException e) {
            throw (e);
        }
    }

    private static Table[] convertTable(_Tables _tables) {
        int len = _tables.tables.length;
        Table[] tables = new Table[len];
        for (int i = 0; i < len; ++i) {
            tables[i] = MetaUtil.convertTable(_tables.tables[i]);
        }
        return tables;
    }

    @Override
    public Table getTable(String name) {
        for(Table t : this.tables) {
            if (t.getName().equals(name))
                return t;
        }
        return  null;
    }

    @Override
    public String[] getTableNames() {
        String[] names = new String[this.tables.length];
        for(int n = 0; n < names.length; n +=1){
            names[n] = this.tables[n].getName();
        }
        return names;
    }

    @Override
    public long getTableBytesByHour(String name) {
        return 0;
    }

    @Override
    public void registColumns(String id, String tableName, Set<String> columns) {

    }
}
