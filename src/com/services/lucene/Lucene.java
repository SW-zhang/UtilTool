package com.services.lucene;

import com.services.lucene.analyzer.IKAnalyzer5x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.FileSystems;


/**
 * 搜索引擎
 *
 * @author wang
 */
public class Lucene {

    private static final Logger log = LoggerFactory.getLogger(Lucene.class);

    private static double ramBufferSizeMB = 100.00;// writer提交之前可缓存的数据大小
    private static final String dir = LuceneConfig.getLuceneDir();
    private static Directory directory = null;
    private volatile static IndexWriter writer = null;

    static {
        try {
            directory = FSDirectory.open(FileSystems.getDefault().getPath(dir));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(": 初始化directory异常", e);
        }
    }

    /**
     * 获取分词器
     *
     * @return
     */
    protected static Analyzer getAnalyzer() {
        // return new StandardAnalyzer();
        // return new CJKAnalyzer();
        return new IKAnalyzer5x(true);
    }

    /**
     * 获取分词器
     *
     * @return
     */
    protected static Analyzer getIKAnalyzer() {
        return new IKAnalyzer5x(true);
    }

    /**
     * 获取writer对象
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    protected static IndexWriter getIndexWriter() {
        if (writer == null) {
            synchronized (Lucene.class) {
                if (writer == null) {
                    try {
                        IndexWriter.isLocked(directory);
                        IndexWriterConfig iwc = new IndexWriterConfig(getAnalyzer());
                        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
                        iwc.setRAMBufferSizeMB(ramBufferSizeMB);

                        writer = new IndexWriter(directory, iwc);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(": 获取writer对象异常", e);
                        try {
                            writer.close();
                            writer = null;
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }

        return writer;
    }

    /**
     * 获取IndexReader
     *
     * @return
     */
    protected static IndexReader getIndexReader() {
        IndexReader reader = null;
        try {
            reader = DirectoryReader.open(directory);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(": 获取reader对象异常", e);
        }
        return reader;
    }

    /**
     * 获取IndexSearcher
     *
     * @return
     * @throws IndexNotFoundException
     */
    protected static IndexSearcher getSearcher() {
        return new IndexSearcher(getIndexReader());
    }

    /**
     * 关闭IndexReader
     *
     * @param reader
     */
    protected static void closeIndexReader(IndexReader reader) {
        close(reader);
    }

    /**
     * 关闭IndexWriter
     */
    public static void closeIndexWriter() {
        close(writer);
    }

    private static void close(Closeable object) {
        if (null != object) {
            try {
                object.close();
                object = null;
            } catch (IOException e) {
                e.printStackTrace();
                log.error(": 关闭对象异常", e);
            }

        }
    }
}
