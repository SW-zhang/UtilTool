package com.services.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * lucene util
 *
 * @author wang
 */
public class LuceneUtil {

    private static int fragmenterLength = 100;

    /**
     * @param query   索引查询对象
     * @param prefix  高亮前缀字符串
     * @param stuffix 高亮后缀字符串
     * @return
     * @Title: createHighlighter
     * @Description: 创建高亮器
     */
    public static Highlighter createHighlighter(Query query, String prefix, String stuffix) {
        Formatter formatter = new SimpleHTMLFormatter((prefix == null || prefix.trim().length() == 0) ? "<font color=\"red\">" : prefix, (stuffix == null || stuffix.trim().length() == 0) ? "</font>"
                : stuffix);
        Scorer fragmentScorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
        Fragmenter fragmenter = new SimpleFragmenter(fragmenterLength <= 0 ? 50 : fragmenterLength);
        highlighter.setTextFragmenter(fragmenter);
        return highlighter;
    }

    /**
     * @param document    索引文档对象
     * @param highlighter 高亮器
     * @param analyzer    索引分词器
     * @param field       高亮字段
     * @return
     * @throws IOException
     * @throws InvalidTokenOffsetsException
     * @Title: highlight
     * @Description: 生成高亮文本
     */
    public static String highlight(Document document, Highlighter highlighter, Analyzer analyzer, String field) throws IOException {
        List<IndexableField> list = document.getFields();
        for (IndexableField fieldable : list) {
            String fieldValue = fieldable.stringValue();
            if (fieldable.name().equals(field)) {
                try {
                    fieldValue = highlighter.getBestFragment(analyzer, field, fieldValue);
                } catch (InvalidTokenOffsetsException e) {
                    fieldValue = fieldable.stringValue();
                }
                return (fieldValue == null || fieldValue.trim().length() == 0) ? fieldable.stringValue() : fieldValue;
            }
        }
        return null;
    }

    /**
     * 获取分词结果
     *
     * @param str 输入的字符串
     * @return 分词结果
     */
    public static List<String> getWords(String str) {
        List<String> result = new ArrayList<String>();
        TokenStream stream = null;
        try {
            stream = Lucene.getAnalyzer().tokenStream("content", new StringReader(str));
            CharTermAttribute attr = stream.addAttribute(CharTermAttribute.class);
            stream.reset();
            while (stream.incrementToken()) {
                result.add(attr.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
