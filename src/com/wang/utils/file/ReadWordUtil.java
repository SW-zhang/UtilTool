package com.wang.utils.file;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 读取word文件工具类
 *
 */
public class ReadWordUtil {
    private static final Logger log = LoggerFactory.getLogger(ReadWordUtil.class);

    /**
     * 读取word文件
     *
     * @param path
     * @return
     * @throws IOException
     * @throws XmlException
     * @throws OpenXML4JException
     */
    public static String read(String path) throws IOException, XmlException, OpenXML4JException {
        if (path.equals("")) {
            throw new IOException("文件路径为空！");
        } else {
            File file = new File(path);
            if (!file.exists()) {
                throw new IOException("文件不存在！");
            }
        }
        // 获取扩展名
        String ext = path.substring(path.lastIndexOf(".") + 1);
        if ("doc".equals(ext)) { // 使用doc方式读取
            return readWord_doc(path);
        } else if ("docx".equals(ext)) { // 使用docx方式读取
            return readWord_docx(path);
        } else {
            log.info("没有找到可匹配的文件解析方式。。。");
        }

        return "";
    }

    /**
     * 读取2003版word
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readWord_doc(String path) throws IOException {
        InputStream is = new FileInputStream(new File(path));
        WordExtractor ex = new WordExtractor(is);
        return ex.getText().replaceAll("\\s*|\t|\r|\n", "");
    }

    /**
     * 读取2007版word
     *
     * @param path
     * @return
     * @throws IOException
     * @throws XmlException
     * @throws OpenXML4JException
     */
    public static String readWord_docx(String path) throws IOException, XmlException, OpenXML4JException {
        OPCPackage opcPackage = POIXMLDocument.openPackage(path);
        POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
        return extractor.getText().replaceAll("\\s*|\t|\r|\n", "");
    }
}
