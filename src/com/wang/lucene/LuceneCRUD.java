package com.wang.lucene;

import com.alibaba.fastjson.JSON;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 搜索引擎索引增删改查
 *
 * @author wang
 */
public class LuceneCRUD {

    private static final Logger log = LoggerFactory.getLogger(LuceneCRUD.class);

    /**
     * 根据内容，构建索引
     *
     * @param item
     * @return
     */
    public static boolean createIndexer(Object item) {
        return createIndexer(Arrays.asList(item));
    }

    /**
     * 根据内容，构建索引
     *
     * @param items
     * @return
     */
    public static boolean createIndexer(Collection<?> items) {
        log.info(String.format(": Begin-->创建索引：size = %s", items.size()));
        IndexWriter iwriter = null;
        try {
            // 配置索引
            iwriter = Lucene.getIndexWriter();
            // 将文档信息存入索引
            List<Document> docs = new ArrayList<Document>();
            for (Object item : items) {
                if (search(getIdValue(item), new String[]{"id"}, item.getClass(), false).size() > 0) {
                    updateIndex(item);
                    continue;
                }
                docs.add(toDocument(item));
            }
            iwriter.addDocuments(docs);
            iwriter.commit();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加索引异常", e);
        } finally {
        }
        log.info(String.format(": End-->索引创建完成"));
        return true;
    }

    /**
     * 根据keyword搜索索引
     *
     * @param keyword   搜索关键字
     * @param highlight 是否高亮
     * @return
     */
    public static List<Item> search(String keyword, Boolean highlight) {
        return search(keyword, LuceneConfig.getLuceneSearchFields().split(","), Item.class, highlight);
    }

    /**
     * 根据keyword搜索索引
     *
     * @param keyword     搜索关键字
     * @param queryFields 查询域
     * @param clazz
     * @param highlight   是否高亮
     * @return
     */
    public static <T> List<T> search(String keyword, String[] queryFields, Class<T> clazz, Boolean highlight) {
        List<T> result = new ArrayList<T>();
        IndexSearcher isearcher = null;
        try {
            // 设定搜索目录
            isearcher = Lucene.getSearcher();
            Analyzer analyzer = Lucene.getAnalyzer();
            queryFields = queryFields == null ? LuceneConfig.getLuceneSearchFields().split(",") : queryFields;
            MultiFieldQueryParser parser = new MultiFieldQueryParser(queryFields, analyzer);
            // 设定具体的搜索词
            Query query = parser.parse(keyword);
            ScoreDoc[] hits = isearcher.search(query, Integer.MAX_VALUE).scoreDocs;

            // 结果对象组装，排除不组装字段
            List<String> no_result = Arrays.asList(LuceneConfig.getLuceneNoResultFields().split(","));
            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = isearcher.doc(hits[i].doc);
                result.add(getResultObject(query, analyzer, hitDoc, clazz, highlight, no_result));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("搜索索引异常", e);
            return result;
        } finally {
            Lucene.closeIndexReader(isearcher.getIndexReader());
        }
        return result;
    }

    /**
     * 分页查询
     *
     * @param keyword     关键字
     * @param queryFields 查询域
     * @param clazz
     * @param page
     */
    public static <T> void searchForPage(String keyword, String[] queryFields, Class<T> clazz, Page<T> page, Boolean highlight) {
        IndexSearcher searcher = null;
        try {
            searcher = Lucene.getSearcher();
            Analyzer analyzer = Lucene.getAnalyzer();
            MultiFieldQueryParser parser = new MultiFieldQueryParser(queryFields, analyzer);
            // 设定具体的搜索词
            Query query = parser.parse(keyword);

            int totalRecord = searchTotalRecord(searcher, query);
            // 设置总记录数
            page.setTotalRecord(totalRecord);
            TopDocs topDocs = searcher.searchAfter(page.getAfterDoc(), query, page.getPageSize());
            List<T> docList = new ArrayList<T>();
            ScoreDoc[] docs = topDocs.scoreDocs;
            int index = 0;
            // 结果对象组装，排除不组装字段
            List<String> no_result = Arrays.asList(LuceneConfig.getLuceneNoResultFields().split(","));
            for (ScoreDoc scoreDoc : docs) {
                int docID = scoreDoc.doc;
                Document document = null;
                try {
                    document = searcher.doc(docID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (index == docs.length - 1) {
                    page.setAfterDoc(scoreDoc);
                    page.setAfterDocId(docID);
                }
                docList.add(getResultObject(query, analyzer, document, clazz, highlight, no_result));
                index++;
            }
            page.setItems(docList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("搜索索引异常", e);
        } finally {
            Lucene.closeIndexReader(searcher.getIndexReader());
        }
    }

    /**
     * @param query
     * @return
     * @throws IOException
     * @Title: searchTotalRecord
     * @Description: 获取符合条件的总记录数
     */
    public static int searchTotalRecord(IndexSearcher search, Query query) {
        ScoreDoc[] docs = null;
        try {
            TopDocs topDocs = search.search(query, Integer.MAX_VALUE);
            if (topDocs == null || topDocs.scoreDocs == null || topDocs.scoreDocs.length == 0) {
                return 0;
            }
            docs = topDocs.scoreDocs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return docs.length;
    }

    /**
     * 更新索引文档
     *
     * @param item
     */
    private static void updateIndex(Object item) {
        log.info(String.format(": Begin-->更新索引 id = %s", getIdValue(item)));
        IndexWriter writer = null;
        try {
            writer = Lucene.getIndexWriter();
            writer.updateDocument(new Term("id", getIdValue(item)), toDocument(item));
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("更新索引异常", e);
        } finally {
        }
        log.info(String.format(": End-->更新索引完成"));
    }

    /**
     * 删除索引文档
     *
     * @param id
     */
    public static void deleteIndex(String id) {
        log.info(String.format(": Begin-->删除索引:{id : %s}", id));
        IndexWriter writer = null;
        try {
            writer = Lucene.getIndexWriter();
            writer.deleteDocuments(new Term("id", id));
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("删除索引异常", e);
        } finally {
        }
        log.info(String.format(": End-->删除索引完成"));
    }

    /**
     * 批量删除索引文档
     *
     * @param ids
     */
    public static void deleteIndexs(List<?> ids) {
        log.info(String.format(": Begin-->批量删除索引:{%s}", JSON.toJSONString(ids)));
        IndexWriter writer = null;
        try {
            writer = Lucene.getIndexWriter();
            Term[] terms = new Term[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                terms[i] = new Term("id", String.valueOf(ids.get(i)));
            }
            writer.deleteDocuments(terms);
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("删除索引异常", e);
        } finally {
        }
        log.info(String.format(": End-->批量删除索引完成"));
    }

    /**
     * 删除所有索引文档
     */
    public static void deleteAllIndex() {
        log.info(String.format(": Begin-->删除所有索引文档。。。"));
        IndexWriter writer = null;
        try {
            writer = Lucene.getIndexWriter();
            writer.deleteAll();
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("删除全部索引异常", e);
        } finally {
        }
        log.info(String.format(": Begin-->删除所有索引文档结束"));
    }

    /**
     * 搜索结果组装
     *
     * @param query
     * @param analyzer
     * @param doc
     * @param clazz
     * @param highlight
     * @param no_result
     * @return
     * @throws Exception
     */
    private static <T> T getResultObject(Query query, Analyzer analyzer, Document doc, Class<T> clazz, Boolean highlight, List<String> no_result) throws Exception {
        // 对多field进行搜索
        java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
        T item = clazz.newInstance();
        for (java.lang.reflect.Field field : fields) {
            // 结果域排除字段
            if (no_result.contains(field.getName())) {
                continue;
            }
            String setMethodName = "set" + toFirstLetterUpperCase(field.getName());
            if (highlight) {
                if (!field.getName().equals("id")) {
                    String highlight_text = LuceneUtil.highlight(doc, LuceneUtil.createHighlighter(query, "", ""), analyzer, field.getName());
                    item.getClass().getMethod(setMethodName, String.class).invoke(item, highlight_text);
                    continue;
                }
            }
            item.getClass().getMethod(setMethodName, String.class).invoke(item, doc.get(field.getName()));

        }

        return item;
    }

    /**
     * 得到对象的id属性值
     *
     * @param item
     * @return
     */
    private static String getIdValue(Object item) {
        String value = "";
        try {
            String getMethodName = "get" + toFirstLetterUpperCase("id");
            value = item.getClass().getMethod(getMethodName).invoke(item).toString();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("得到对象的id属性值异常", e);
        }
        return value;
    }

    /**
     * 将对象转换为Document
     */
    private static Document toDocument(Object item) {
        Document doc = new Document();
        try {
            java.lang.reflect.Field[] fields = item.getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                String fieldName = field.getName();
                String getMethodName = "get" + toFirstLetterUpperCase(fieldName);
                String obj = item.getClass().getMethod(getMethodName).invoke(item).toString();
                doc.add(new Field(fieldName, (String) obj, TextField.TYPE_STORED));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("对象转换为Document异常", e);
        }

        return doc;
    }

    /**
     * 首字母转大写
     *
     * @param str
     * @return
     */
    private static String toFirstLetterUpperCase(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }
}
