package com.wang.utils.file;

import java.io.File;

public class ChangeFileName {

    private static void changeFileName(String fileFolderName) {
        File fileFolder = new File(fileFolderName);
        if (fileFolder.isDirectory()) {
            File[] files = fileFolder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    String oldName = file.getName();
                    String[] s = oldName.split("]")[1].split("\\.");
                    String newName = s[0] + "." + s[1];
                    file.renameTo(new File("D:\\ss\\" + newName));
                }
            }
        }
    }
}
