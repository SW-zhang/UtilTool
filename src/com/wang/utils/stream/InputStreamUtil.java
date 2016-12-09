package com.wang.utils.stream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class InputStreamUtil {

    /**
     * 从流中转为字符
     *
     * @param is
     * @param charset
     * @return
     * @throws IOException
     */
    public String streamAsString(InputStream is, String charset) throws IOException {
        Map<byte[], Integer> bslist = new LinkedHashMap<byte[], Integer>();
        int bsslen = 0;
        int len = 0;
        while (true) {
            byte[] bs = new byte[1024 * 16];
            if ((len = is.read(bs)) == -1)
                break;
            bslist.put(bs, len);
            bsslen += len;
        }

        byte[] bss = new byte[bsslen];
        int i = 0;
        for (Entry<byte[], Integer> ent : bslist.entrySet()) {
            System.arraycopy(ent.getKey(), 0, bss, i, ent.getValue());
            i += ent.getValue();
        }

        return new String(bss, 0, bss.length, charset);
    }

    public String streamAsString(InputStream is) throws IOException {
        return streamAsString(is, Charset.defaultCharset().name());
    }
}
