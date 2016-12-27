package com.services.utils.param;

import com.services.utils.string.StringUtil;

public class ParamUtil {

    private String nameValueSpliter, arraySpliter;

    public ParamUtil(String nameValueSpliter, String arraySpliter) {
        this.nameValueSpliter = nameValueSpliter;
        this.arraySpliter = arraySpliter;
    }

    public String getParamSpliter() {
        return nameValueSpliter;
    }

    public String getArraySpliter() {
        return arraySpliter;
    }

    public ParamMap parseParamList(String string) {
        if (StringUtil.isTrimEmpty(string)) {
            return new ParamMap();
        }
        String[] param = StringUtil.splitByString(string, arraySpliter);
        ParamMap map = new ParamMap();
        for (String keyvalue : param) {
            Param nameValue = parseParam(keyvalue);
            map.add(nameValue);
        }
        return map;
    }

    public Param parseParam(String namevalue) {
        String name = StringUtil.getLeft(namevalue, nameValueSpliter);
        if (StringUtil.isTrimEmpty(name)) {
            throw new RuntimeException("'" + namevalue + "' is not a good string when nameValueSpliter='" + nameValueSpliter + "'");
        }
        String value = namevalue.substring((name + nameValueSpliter).length());
        return new Param(StringUtil.trim(name), StringUtil.trim(value));
    }

}
