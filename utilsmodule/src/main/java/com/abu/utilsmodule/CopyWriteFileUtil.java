package com.abu.utilsmodule;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class CopyWriteFileUtil {
    //工具类实例对象
    private static volatile CopyWriteFileUtil copyWriteFileUtil;

    //私有化构造函数
    private CopyWriteFileUtil(){}

    //获取工具类实例
    public static CopyWriteFileUtil getInstance(){
       if (copyWriteFileUtil==null){
           synchronized (CopyWriteFileUtil.class){
               if (copyWriteFileUtil==null){
                   copyWriteFileUtil=new CopyWriteFileUtil();
               }
           }
       }
       return copyWriteFileUtil;
    }

    //读取文件返回内容
    public String reaFile(File file){
        if (file.exists()){
            try {
                String str;
                File read=file;
                FileInputStream fis = new FileInputStream(read);
                BufferedInputStream bufferedInputStream=new BufferedInputStream(fis);
                InputStreamReader inputStreamReader=new InputStreamReader(bufferedInputStream,"GBK");
                BufferedReader reader=new BufferedReader(inputStreamReader);
                StringBuffer result = new StringBuffer();
                while ((str=reader.readLine())!=null) {
                    result.append(str);
                   // result.append("\n");
                }

                reader.close();
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Log.i("TAG","文件不存在");
            return "文件不存在";
        }
        return "文件读取错误";
    }

    //写入数据到指定文件
    public void writeFile(String content, File file){
        FileOutputStream fileOutputStream= null;
        try {
            fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(bufferedOutputStream,"GBK");
            BufferedWriter writer=new BufferedWriter(outputStreamWriter);
            writer.write(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //复制文件
    public void copyfile(File oldFile, File newFile) {
        //用于判断视频数据是否复制完成
        boolean b=false;
        //显示加载中控件
        if (onCopyProgress!=null){
            onCopyProgress.onStartCopy();
        }
        try {
            //1,创建缓冲输入流
            InputStream inputStream = new FileInputStream(oldFile); //数据源
            BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
            InputStreamReader inputStreamReader=new InputStreamReader(bufferedInputStream,"GBK");
            BufferedReader reader=new BufferedReader(inputStreamReader);

            //2，创建缓冲输出流
            //File file = new File(destDir.getAbsolutePath() + File.separator + oldFile.getName()); //写入数据目标文件
            FileOutputStream fileOutputStream=new FileOutputStream(newFile);
            BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(bufferedOutputStream,"GBK");
            BufferedWriter writer=new BufferedWriter(outputStreamWriter);

            //3.输入流读数据、输出流写数据
            String str;
            while ((str=reader.readLine())!=null) {
                writer.write(str);
                writer.newLine();
                writer.flush();
            }
            b=true;
            if (b){
                //关闭加载中控件
                if (onCopyProgress!=null){
                    onCopyProgress.onFinishCopy();
                }
            }
            //4.关流,只需要关缓冲流，文件流不用关
            writer.flush();
            writer.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void copyFile(File src, File des) throws IOException {
        //显示加载中控件
        if (onCopyProgress!=null){
            onCopyProgress.onStartCopy();
        }
        FileOutputStream writer = null;
        BufferedInputStream bufR = null;
        BufferedOutputStream bufW = null;
        try {
            writer = new FileOutputStream(des);
            bufR = new BufferedInputStream(new FileInputStream(src));
            bufW = new BufferedOutputStream(writer);
            int temp = 0;
            while ((temp = bufR.read()) != -1) {
                bufW.write(temp);
            }
            //关闭加载中控件
            if (onCopyProgress!=null){
                onCopyProgress.onFinishCopy();
            }

            bufW.flush();
            bufW.close();
            bufR.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //文件拷贝过程接口
    private OnCopyProgress onCopyProgress;

    public void setOnCopyProgress(OnCopyProgress onCopyProgress) {
        this.onCopyProgress = onCopyProgress;
    }

    public interface OnCopyProgress{
        void onStartCopy();
        void onFinishCopy();
    }

}
