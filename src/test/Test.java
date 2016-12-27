package test;

import com.services.utils.file.excel.ExcelObject;
import com.services.utils.file.excel.WriteExcelUtil;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wang on 2016/12/7.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        List<Demo> datas = new ArrayList<Demo>();
        datas.add(new Demo("zhang", 20, "15810334368"));
        datas.add(new Demo("张树旺", 20, "15810334368"));
        OutputStream out = new FileOutputStream("C:\\Users\\wang\\Desktop\\新建文件夹\\11.xls");
        List<ExcelObject> excelObjects = Arrays.asList(
                new ExcelObject(null, new String[]{"姓名", "年龄", "电话"}, new String[]{"name","age", "tel"}, datas),
                new ExcelObject(null, new String[]{"姓名", "电话"}, new String[]{"name", "tel"}, datas)
        );
        WriteExcelUtil.writeExcel(out, excelObjects);
    }
}

