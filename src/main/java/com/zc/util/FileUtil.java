package com.zc.util;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

public class FileUtil {


    public static Set<String> matchefilePath(String matcher) {
        String location = getCodeSourcePath();
        return matchefilePath(location, matcher);
    }

    public static Set<String> matchefilePath(String basePath, String matcher) {

        if (matcher == null || "".equals(matcher.trim())) {
            return null;
        }

        Set<String> filePathSet = new HashSet<>();

        String location = basePath;
        File baseDir = new File(location);
        String[] arr = matcher.split("/");

        int length = arr.length;
        String end = arr[length - 1];
        if (length == 1) {
            FileFilter fileFilter = new WildcardFileFilter(matcher);
            File[] files = baseDir.listFiles(fileFilter);
            for (File file : files) {
                filePathSet.add(file.getAbsolutePath());
            }
        } else {
            FileFilter fileFilter = new WildcardFileFilter(arr[0]);
            List<File> dirList = listDirs(baseDir, fileFilter);
            for (int i = 0; i < length - 2; i++) {
                fileFilter = new WildcardFileFilter(arr[i + 1]);
                dirList = listDirs(dirList, fileFilter);
            }

            fileFilter = new WildcardFileFilter(end);
            filePathSet = listFilePaths(dirList, fileFilter);
        }
        return filePathSet;
    }


    public static List<File> listDirs(List<File> dirFiles, FileFilter fileFilter) {
        List<File> dirList = new ArrayList<>();
        for (File dirFile : dirFiles) {
            dirList.addAll(listDirs(dirFile, fileFilter));
        }
        return dirList;
    }

    public static List<File> listDirs(File[] dirFiles, FileFilter fileFilter) {
        List<File> dirList = new ArrayList<>();
        for (File dirFile : dirFiles) {
            dirList.addAll(listDirs(dirFile, fileFilter));
        }
        return dirList;
    }

    public static List<File> listDirs(File dirFile, FileFilter fileFilter) {
        List<File> dirList = new ArrayList<>();
        File[] dirFiles = dirFile.listFiles(fileFilter);
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                dirList.add(file);
            }
        }
        return dirList;
    }

    public static List<File> listFiles(File dirFile, FileFilter fileFilter) {
        List<File> fileList = new ArrayList<>();
        File[] dirFiles = dirFile.listFiles(fileFilter);
        for (File file : dirFiles) {
            if (!file.isDirectory()) {
                fileList.add(file);
            }
        }
        return fileList;
    }


    public static Set<String> listFilePaths(File dirFile, FileFilter fileFilter) {
        Set<String> pathSet = new HashSet<>();
        File[] dirFiles = dirFile.listFiles(fileFilter);
        for (File file : dirFiles) {
            if (!file.isDirectory()) {
                pathSet.add(file.getAbsolutePath());
            }
        }
        return pathSet;
    }

    public static Set<String> listFilePaths(Collection<File> dirFiles, FileFilter fileFilter) {
        Set<String> pathSet = new HashSet<>();
        for (File dirFile : dirFiles) {
            pathSet.addAll(listFilePaths(dirFile, fileFilter));
        }
        return pathSet;
    }

    public static String getCodeSourcePath() {

        String location = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return location;
    }

}
