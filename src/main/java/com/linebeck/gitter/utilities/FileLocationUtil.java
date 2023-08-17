package com.linebeck.gitter.utilities;

import java.io.File;

public class FileLocationUtil {

    public static String correctFilePath(String path) {
        String normalizedFilePath = path
                .replace("/", File.separator)
                .replace("\\", File.separator);
        return normalizedFilePath;
    }
}
