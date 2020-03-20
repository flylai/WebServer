package com.lkm.webserver.servlet;

import com.lkm.webserver.api.Servlet;
import com.lkm.webserver.constant.Misc;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadServlet {

    private static void scanFiles(List<File> fileList, File file) {
        if (file.isDirectory()) {
            File[] subDirFileList = file.listFiles();
            if (subDirFileList == null) {
                return;
            }
            for (File f : subDirFileList) {
                scanFiles(fileList, f);
            }
        } else {
            fileList.add(file);
        }
    }

    private static List<Class<?>> loadServletClasses() {
        List<Class<?>> servletList = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        scanFiles(fileList, new File(Misc.WWW_CLASSES));
        URLClassLoader urlClassLoader = null;
        try {
            urlClassLoader = new URLClassLoader(new URL[]{
                    new File(Misc.WWW_CLASSES).toURI().toURL()
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (urlClassLoader == null) {
            return servletList;
        }
        for (File file : fileList) {
            try {
                String path = file.getPath();
                // 6 means ".class", 1 means dir/ -> "/"
                path = path.substring(Misc.WWW_CLASSES.length() + 1, path.length() - 6).replace("/", ".");
                System.out.println(path);
                Class<?> clazz = urlClassLoader.loadClass(path);
                servletList.add(clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return servletList;
    }

    public static Map<String, Servlet> buildServletMap() {
        List<Class<?>> servletList = loadServletClasses();
        Map<String, Servlet> result = new HashMap<>();
        for (Class<?> servlet : servletList) {
            UrlMatch urlMatch = servlet.getAnnotation(UrlMatch.class);
            try {
                result.put(urlMatch.urlMatch(), (Servlet) servlet.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
