package com.services.utils.xml;

import com.services.utils.Date.DateUtil;
import com.services.utils.object.FieldUtil;
import com.services.utils.type.TypeUtil;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

/**
 * XML工具类
 */
public class XMLParser {

    private String dateFormat = "yyyyMMddHHmmssms";
    private String encoding = "UTF-8";
    private boolean format = false;

    public String parserString(Object obj) {
        return parserString(obj.getClass().getSimpleName(), obj);
    }

    public String parserString(String rootElementName, Object obj) {
        Element root = DocumentFactory.getInstance().createElement(rootElementName);
        fillElement(root, obj);
        Document dom = DocumentFactory.getInstance().createDocument(root);
        dom.setXMLEncoding(encoding);
        if (format) {
            return format(dom);
        } else {
            return dom.asXML();
        }
    }

    private String format(Document dom) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = null;
        StringWriter sw = new StringWriter();
        writer = new XMLWriter(sw, format);
        try {
            writer.write(dom);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    private void fillElement(Element element, Object obj) {
        if (null == obj) {
            return;
        }
        if (TypeUtil.isPrimitive(obj.getClass())) {
            element.setText(obj.toString());
            return;
        }
        if (obj instanceof String) {
            element.setText((String) obj);
            return;
        }
        if (obj instanceof Date) {
            if (null == dateFormat) {
                element.setText("" + ((Date) obj).getTime());
            } else {
                element.setText(DateUtil.format((Date) obj, dateFormat));
            }
            return;
        }
        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            for (Entry<?, ?> entry : map.entrySet()) {
                Element eEntry = element.addElement("entry");
                Element eKey = eEntry.addElement("key");
                Element eValue = eEntry.addElement("value");
                fillElement(eKey, entry.getKey());
                fillElement(eValue, entry.getValue());
            }
            return;
        }
        if (obj instanceof Collection) {
            Collection<?> collection = (Collection<?>) obj;
            for (Object item : collection) {
                Element e = element.addElement("item");
                fillElement(e, item);
            }
            return;
        }
        Class<?> classes = obj.getClass();
        String[] names = FieldUtil.getters(classes);
        for (String name : names) {
            Object value = null;
            try {
                value = FieldUtil.get(obj, name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Element elementOfObject = element.addElement(name);
            fillElement(elementOfObject, value);
        }
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isFormat() {
        return format;
    }

    public void setFormat(boolean format) {
        this.format = format;
    }

}
