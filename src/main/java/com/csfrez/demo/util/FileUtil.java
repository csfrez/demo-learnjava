package com.csfrez.demo.util;

import java.io.*;

public class FileUtil {

    public static void createFile(String filePath, ByteArrayOutputStream byteArrayOutputStream){
        try(FileOutputStream fileOutputStream = new FileOutputStream(filePath)){
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ByteArrayOutputStream getOutputStream(String filePath){
        try(FileInputStream fileInputStream = new FileInputStream(new File(filePath))) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer);
            }
            return byteArrayOutputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ByteArrayOutputStream output = FileUtil.getOutputStream("D:\\Temp\\file\\test.png");
        String filePath = "D:\\Temp\\file\\" + System.currentTimeMillis() + ".png";
        FileUtil.createFile(filePath, output);
    }
}
