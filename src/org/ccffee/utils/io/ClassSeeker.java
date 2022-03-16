package org.ccffee.utils.io;

import org.ccffee.utils.iteration.ArrayUtils;
//import sun.misc.Launcher;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassSeeker {

    URLClassLoader[] loaders;

    public ClassSeeker(URLClassLoader...loaders){
        this.loaders = loaders;
    }

    public Class<?>[] seekForClasses(String className) {
        Class<?>[] classes = new Class<?>[0];
        for(URLClassLoader loader : loaders)for(URL url : loader.getURLs()) {
            for(String s : seekForClassPaths(url, className))
                try {
                    classes = ArrayUtils.addAndExpand(classes, loader.loadClass(s));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
        }
        try{
            classes = ArrayUtils.addAndExpand(classes,ClassLoader.getPlatformClassLoader().loadClass(className));
        }catch (ClassNotFoundException e){
        }
        return classes;
    }
    public String[] seekForClassPaths(URL url, String className) {
        String[] out = new String[0];
        String path = url.getFile().replace("%20", " ");
        File parent = new File(path);
        ClassFileNameFilter filter = new ClassFileNameFilter(className);
        if(!parent.exists())return out;
        if(parent.isDirectory()) {
            for(File f : FileUtils.listAllFiles(parent,filter))out = ArrayUtils.addAndExpand(out,filter.formatClassPath(f.toURI().getPath().substring(path.length())));
        }else if(parent.getName().toLowerCase().endsWith(".jar")||parent.getName().toLowerCase().endsWith(".zip")) {
            try {
                Enumeration<? extends ZipEntry> e;
                ZipFile f = new ZipFile(parent);
                e = f.entries();
                while(e.hasMoreElements()) {
                    String s = e.nextElement().getName();
                    if(filter.accept(s))out = ArrayUtils.addAndExpand(out, filter.formatClassPath(s));
                }
                f.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return out;
    }
    class ClassFileNameFilter implements FileFilter {
        String name;
        public ClassFileNameFilter(String className) {
            name = className+".class";
        }

        @Override
        public boolean accept(File pathname) {
            return accept(pathname.toURI().getPath());
        }
        public boolean accept(String classPath) {
            if(name.length()<=classPath.length()) {
                boolean bool = true;
                int i;
                for(i = 1;i<=name.length();i++) bool = bool && equals(name.charAt(name.length()-i), classPath.charAt(classPath.length()-i));
                bool = bool && (name.length()==classPath.length()||isDot(classPath.charAt(classPath.length()-i)));
                return bool;
            }else return false;
        }
        private boolean equals(char c1, char c2) {
            return c1==c2||(c1=='.'&&isDot(c2));
        }
        private boolean isDot(char c) {
            return c=='/'||c=='$'||c=='.';
        }
        public String formatClassPath(String classPath) {
            return classPath.replace('/', '.').substring(0, classPath.length()-6);
        }
    }

}
