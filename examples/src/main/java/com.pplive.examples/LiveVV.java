package com.pplive.examples;

import com.pplive.pike.Configuration;
import com.pplive.pike.client.*;
import com.pplive.pike.metadata.RawMetaDataSource;

class LiveVV {

    PikeContextBuilder contextBuilder;

    void init() {
        Configuration config = new Configuration();
        config.put(Configuration.localMode, "true");
        config.put(Configuration.localRunSeconds, "20");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        config.addResource(classLoader.getResource("examples.yaml"));
        Column[] columns = new Column[3];
        columns[0] = new Column("channel", ColumnType.Long);
        columns[1] = new Column("vvid", ColumnType.String);
        columns[2] = new Column("user", ColumnType.String);
        Table t = new Table("dol_smart", columns);
        RawMetaDataSource dataSource = new RawMetaDataSource();
        dataSource.addTable(t);
        contextBuilder = new PikeContextBuilder(config);
        contextBuilder.withMetaDataSource(dataSource).withLocalTextFileSpout("dol_smart");
        String topologyName = "sm_live_vv_5s";
        String sql = "withperiod 5s select channel, count(*) as vv from dol_smart group by channel";
        contextBuilder.withSql(sql).withTopologyName(topologyName);
        String path = classLoader.getResource("dol_smart").getPath();
        contextBuilder.withLocalTextFileSpout(path);
    }


    public void submit() {
        init();
        PikeContext context = contextBuilder.build();
        context.validateSQLSyntax();
        context.explain();
        context.display();
        context.submit();
    }

    public static void main(String[] args) {
        LiveVV liveVV = new LiveVV();
        liveVV.submit();
    }
}