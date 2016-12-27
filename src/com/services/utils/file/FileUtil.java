package com.services.utils.file;

import com.services.utils.file.obj.EOF;
import com.services.utils.file.obj.ObjectReader;
import com.services.utils.file.obj.ObjectWriter;
import com.services.utils.stream.InputStreamUtil;
import com.services.utils.string.StringUtil;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;

public class FileUtil {

    /**
     * 功能描述：列出某文件夹及其子文件夹下面的文件，并可根据扩展名过滤
     *
     * @param path 文件夹
     */
    public static void list(File path) {
        if (!path.exists()) {
            System.out.println("文件名称不存在!");
        } else {
            if (path.isFile()) {
                if (path.getName().toLowerCase().endsWith(".pdf")
                        || path.getName().toLowerCase().endsWith(".doc")
                        || path.getName().toLowerCase().endsWith(".chm")
                        || path.getName().toLowerCase().endsWith(".html")
                        || path.getName().toLowerCase().endsWith(".htm")) {// 文件格式
                    System.out.println(path);
                    System.out.println(path.getName());
                }
            } else {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    list(files[i]);
                }
            }
        }
    }

    /**
     * 功能描述：拷贝一个目录或者文件到指定路径下，即把源文件拷贝到目标文件路径下
     *
     * @param source 源文件
     * @param target 目标文件路径
     * @return void
     */
    public static void copy(File source, File target) {
        File tarpath = new File(target, source.getName());
        if (source.isDirectory()) {
            tarpath.mkdir();
            File[] dir = source.listFiles();
            for (int i = 0; i < dir.length; i++) {
                copy(dir[i], tarpath);
            }
        } else {
            try {
                InputStream is = new FileInputStream(source); // 用于读取文件的原始字节流
                OutputStream os = new FileOutputStream(tarpath); // 用于写入文件的原始字节的流
                byte[] buf = new byte[1024];// 存储读取数据的缓冲区大小
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                is.close();
                os.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取一个对象
     *
     * @param file
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object readObject(File file) {
        try {
            ObjectReader or = new ObjectReader(file);
            try {
                Object o = or.next();
                if (o instanceof EOF) {
                    return null;
                }
                return o;
            } finally {
                or.close();
            }
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 读文件（一次将所有字节读入内存并返回）
     *
     * @param file
     * @return 文件内容
     * @throws IOException
     */
    public static byte[] readByteFile(File file) {
        try {
            InputStream is = new FileInputStream(file);
            byte[] bs = new byte[(int) file.length()];
            try {
                is.read(bs);
            } finally {
                is.close();
            }
            return bs;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 写一个对象到文件（覆盖）
     *
     * @param file
     * @param o
     * @throws IOException
     */
    public static boolean writeObject(File file, Object o) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ObjectWriter ow = new ObjectWriter(file);
            try {
                ow.writeObject(o);
            } finally {
                ow.close();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 读文件(使用默认编码)
     *
     * @param file
     * @return 文件内容
     */
    public static String readFile(File file) {
        return readFile(file, Charset.defaultCharset().name());
    }

    /**
     * 读资源中的文件
     *
     * @param name
     * @return
     */
    public static String readResource(String name) {
        return readResource(name, Charset.defaultCharset().name());
    }

    /**
     * 读资源中的文件
     *
     * @param name
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readResource(String name, String encoding) {
        try {
            URL url = FileUtil.class.getResource(name);
            try {
                String filename = URLDecoder.decode(url.getPath(), "utf8");
                File file = new File(filename);
                if (file.exists()) {
                    return readFile(new File(filename), encoding);
                }
                InputStream is = FileUtil.class.getResourceAsStream(name);
                InputStreamUtil isu = new InputStreamUtil();
                return isu.streamAsString(is, encoding);
            } catch (UnsupportedEncodingException e) {
                throw e;
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 读文件
     *
     * @param file
     * @param encoding 编码
     * @return 文件内容
     */
    public static String readFile(File file, String encoding) {
        InputStreamReader fr;
        try {
            fr = new InputStreamReader(new FileInputStream(file), encoding);
            StringBuffer sb = new StringBuffer();
            char[] bs = new char[1024];
            int i = 0;
            while ((i = fr.read(bs)) != -1) {
                sb.append(bs, 0, i);
            }
            fr.close();
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 写文件
     *
     * @param file
     * @param string 字符串
     * @return 文件大小
     * @throws IOException
     */
    public static int writeFile(File file, String string) {
        return writeFile(file, string.getBytes());
    }

    /**
     * 写文件
     *
     * @param file
     * @param bytes
     * @return
     */
    public static int writeFile(File file, byte[] bytes) {
        if (null == bytes) {
            return -1;
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(bytes);
                return bytes.length;
            } finally {
                fos.close();
            }
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * 写文件
     *
     * @param file
     * @param string   字符串
     * @param encoding 编码
     * @return 文件大小
     * @throws IOException
     */
    public static int writeFile(File file, String string, String encoding) {
        try {
            return writeFile(file, string.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 取得当前类所在的文件
     *
     * @param clazz
     * @return
     */
    public static File getClassFile(Class<?> clazz) {
        URL path = clazz.getResource(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + ".classs");
        if (path == null) {
            String name = clazz.getName().replaceAll("[.]", "/");
            path = clazz.getResource("/" + name + ".class");
        }
        return new File(path.getFile());
    }

    /**
     * 取得当前类所在的ClassPath目录，比如tomcat下的classes路径
     *
     * @param clazz
     * @return
     */
    public static File getClassPathFile(Class<?> clazz) {
        File file = getClassFile(clazz);
        for (int i = 0, count = clazz.getName().split("[.]").length; i < count; i++)
            file = file.getParentFile();
        if (file.getName().toUpperCase().endsWith(".JAR!")) {
            file = file.getParentFile();
        }
        return file;
    }

    /**
     * 取得当前类所在的ClassPath路径
     *
     * @param clazz
     * @return
     */
    public static String getClassPath(Class<?> clazz) {
        try {
            return java.net.URLDecoder.decode(getClassPathFile(clazz).getAbsolutePath(), "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        String suffix = "";
        if (!StringUtil.isTrimEmpty(fileName)) {
            suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        if (!StringUtil.isTrimEmpty(suffix) && suffix.length() > 8) {
            suffix = "";
        }
        return suffix;
    }
}
