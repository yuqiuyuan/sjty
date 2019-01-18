package com.dre.sjty.file;

import com.oracle.jrockit.jfr.EventDefinition;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  存在严重的bug，存在线程中的线程不够用的情况，所有的线程都在等待
 */
@Deprecated
public class SjtyThreadFile {
    ExecutorService executor = Executors.newFixedThreadPool(50);
    Set<String> fileSet = new LinkedHashSet<String>();
    Set<String> folderSet = new LinkedHashSet<String>();
    private String path;

    public SjtyThreadFile(String path) {
        this.path = path;
    }

    public void find() {
        executor.submit(new SjtyFindRunnable(path));
        while (true) {
            if (executor.isTerminated()) {
                System.out.println("end");
                break;
            }
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class SjtyFindRunnable implements Runnable {
        private String path;

        SjtyFindRunnable(String path) {
            this.path = path;
        }

        public void run() {
            File file = new File(path);
            if (file.exists()) {
                File[] files = file.listFiles();
                if (files != null && files.length != 0) {
                    for (File file2 : files) {
                        if (file2.isDirectory()) {
                            folderSet.add(file2.getAbsolutePath());
                            System.out.println(Thread.currentThread() + "文件夹:" + file2.getAbsolutePath());
                            executor.submit(new SjtyFindRunnable(file2.getAbsolutePath()));
                        } else {
                            fileSet.add(file2.getAbsolutePath());
                            System.out.println(Thread.currentThread() + "文件:" + file2.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();   //获取开始时间
        SjtyThreadFile findFile = new SjtyThreadFile("/Users/yuqiuyuan");
        findFile.find();
        System.out.println("文件夹数量：" + findFile.folderSet.size() + "，文件数量：" + findFile.fileSet.size());
        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }

}
