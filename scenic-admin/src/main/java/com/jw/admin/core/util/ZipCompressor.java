package com.jw.admin.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 同一文件夹下的多个文件夹打成一个zip包
 * @author wangzhi
 * 2016-09-01
 *
 */
public class ZipCompressor {
    static final int BUFFER = 8192;     
    
    private File zipFile;     
      
    public ZipCompressor(String pathName) {     
        zipFile = new File(pathName);     
    }     
    public void compress(String... pathName) {   
        ZipOutputStream out = null;     
        try {    
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);     
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,     
                    new CRC32());     
            out = new ZipOutputStream(cos);     
            String basedir = "";   
            for (int i=0;i<pathName.length;i++){  
                compress(new File(pathName[i]), out, basedir);     
            }  
            out.close();    
        } catch (Exception e) {     
            throw new RuntimeException(e);     
        }   
    }     
    public void compress(String srcPathName) {     
        File file = new File(srcPathName);     
        if (!file.exists())     
            throw new RuntimeException(srcPathName + "不存在！");     
        try {     
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);     
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,     
                    new CRC32());     
            ZipOutputStream out = new ZipOutputStream(cos);     
            String basedir = "";     
            compress(file, out, basedir);     
            out.close();     
        } catch (Exception e) {     
            throw new RuntimeException(e);     
        }     
    }     
    
    private void compress(File file, ZipOutputStream out, String basedir) {     
        /* 判断是目录还是文件 */    
        if (file.isDirectory()) {     
            ////System.out.println("压缩：" + basedir + file.getName());     
            this.compressDirectory(file, out, basedir);     
        } else {     
            ////System.out.println("压缩：" + basedir + file.getName());     
            this.compressFile(file, out, basedir);     
        }     
    }     
    
    /** 压缩一个目录 */    
    private void compressDirectory(File dir, ZipOutputStream out, String basedir) {     
        if (!dir.exists())     
            return;     
    
        File[] files = dir.listFiles();     
        for (int i = 0; i < files.length; i++) {     
            /* 递归 */    
            compress(files[i], out, basedir + dir.getName() + "/");     
        }     
    }     
    
    /** 压缩一个文件 */    
    private void compressFile(File file, ZipOutputStream out, String basedir) {     
        if (!file.exists()) {     
            return;     
        }     
        try {     
            BufferedInputStream bis = new BufferedInputStream(     
                    new FileInputStream(file));     
            ZipEntry entry = new ZipEntry(basedir + file.getName());     
            out.putNextEntry(entry);     
            int count;     
            byte data[] = new byte[BUFFER];     
            while ((count = bis.read(data, 0, BUFFER)) != -1) {     
                out.write(data, 0, count);     
            }     
            bis.close();     
        } catch (Exception e) {     
            throw new RuntimeException(e);     
        }     
    }     
   public static void main(String[] args) {     
        ZipCompressor zc = new ZipCompressor("E:/resource117.zip");     
        //zc.compress("D:\\workspace-sts-3.7.2.branches.2.1\\admin\\src\\main\\webapp\\resources\\TCUIK0LCG5PPU0BVF5G4Y6C5QFGH3ZR0\\20160901031354.csv","D:\\workspace-sts-3.7.2.branches.2.1\\admin\\src\\main\\webapp\\resources\\TCUIK0LCG5PPU0BVF5G4Y6C5QFGH3ZR0\\20160901031515.csv");
        String path1="D:\\workspace-sts-3.7.2.branches.2.1\\admin\\src\\main\\webapp\\resources\\TCUIK0LCG5PPU0BVF5G4Y6C5QFGH3ZR0\\20160901031354.csv";
        String path2="D:\\workspace-sts-3.7.2.branches.2.1\\admin\\src\\main\\webapp\\resources\\TCUIK0LCG5PPU0BVF5G4Y6C5QFGH3ZR0\\20160901031515.csv";
      // String str=path1+","+path2;
        //zc.compress("D:\\workspace-sts-3.7.2.branches.2.1\\admin\\src\\main\\webapp\\resources\\TCUIK0LCG5PPU0BVF5G4Y6C5QFGH3ZR0");
        String[] path22=new String[]{path1,path2};
       // zc.compress("D:\\workspace-sts-3.7.2.branches.2.1\\admin\\src\\main\\webapp\\resources\\TCUIK0LCG5PPU0BVF5G4Y6C5QFGH3ZR0");
        zc.compress(path22);
    }
public File getZipFile() {
	return zipFile;
}
public void setZipFile(File zipFile) {
	this.zipFile = zipFile;
} 
   
}
