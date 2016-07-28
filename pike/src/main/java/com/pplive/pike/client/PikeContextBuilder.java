package com.pplive.pike.client;

import com.pplive.pike.Configuration;
import com.pplive.pike.generator.ISpoutGenerator;
import com.pplive.pike.generator.KafkaSpoutGenerator;
import com.pplive.pike.generator.LocalTextFileSpoutGenerator;
import com.pplive.pike.metadata.ITableInfoProvider;
import com.pplive.pike.metadata.XmlMetaDataSource;

/**
 * Created by jiatingjin on 2016/7/28.
 */
public class PikeContextBuilder {
    private Configuration conf;


    private ITableInfoProvider metaDataSource;

    private String sql;

    private String topologyName;

    private SPOUT spout;

    private String localTextFile;

    private enum SPOUT {
        KAFKA, LOCAL_TEXT_FILE
    }


    public PikeContextBuilder(Configuration conf) {
        this.conf = conf;
    }

    public PikeContextBuilder withMetaDataSource(ITableInfoProvider metaDataSource) {
        this.metaDataSource = metaDataSource;
        return  this;
    }

    public PikeContextBuilder withXmlmetaDataSource(String metaFile) {
        this.metaDataSource = new XmlMetaDataSource(metaFile);
        return  this;
    }

    public PikeContextBuilder withXmlmetaDataSource() {
        this.metaDataSource = new XmlMetaDataSource(conf);
        return  this;
    }

    public PikeContextBuilder withKafaSpout() {
        spout = SPOUT.KAFKA;
        return this;
    }

    public PikeContextBuilder withLocalTextFileSpout(String textFile) {
        spout = SPOUT.LOCAL_TEXT_FILE;
        localTextFile = textFile;
        return this;
    }

    public PikeContextBuilder withSql(String sql) {
        this.sql = sql;
        return this;
    }

    public PikeContextBuilder withTopologyName(String topologyName) {
        this.topologyName = topologyName;
        return  this;
    }

    public PikeContextBuilder saveAs() {
        return  this;
    }

    public PikeContext build() {
        return new PikeContext(conf, metaDataSource, sql, topologyName, createSpoutGenerator());
    }

    private ISpoutGenerator createSpoutGenerator() {
        ISpoutGenerator spoutGenerator = null;
        switch (spout) {
            case KAFKA:
                spoutGenerator = new KafkaSpoutGenerator(this.metaDataSource);
                break;
            case LOCAL_TEXT_FILE:
                spoutGenerator = new LocalTextFileSpoutGenerator(this.metaDataSource, this.localTextFile);
                break;
            default:

        }
        return spoutGenerator;
    }
}
