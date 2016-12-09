package com.wang.lucene;

import com.wang.utils.properties.ReadProperties;

/**
 * 配置
 *
 * @author wang
 */
public class LuceneConfig {

    public static String path = "luceneconf.properties";

    public static String getLuceneDir() {
        return ReadProperties.getValue(path, "lucene_diretory");
    }

    public static String getLuceneSearchFields() {
        return ReadProperties.getValue(path, "lucene_search_fields");
    }

    public static String getLuceneNoResultFields() {
        return ReadProperties.getValue(path, "lucene_noresult_fields");
    }

}
