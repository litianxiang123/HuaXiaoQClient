package com.hxq.huaxiaoqclient.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 * Created by Administrator on 2016/9/19.
 */
public class FileUtil<T> {
    /**
     * 将对象保存到本地
     * @param context
     * @param fileName 文件名
     * @param bean  对象
     * @return true 保存成功
     */
    public boolean writeObjectIntoLocal(Context context, String fileName, T bean){
        try {
            // 通过openFileOutput方法得到一个输出流，方法参数为创建的文件名（不能有斜杠），操作模式
            @SuppressWarnings("deprecation")
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bean);//写入
            fos.close();//关闭输入流
            oos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //Toast.makeText(WebviewTencentActivity.this, "出现异常1",Toast.LENGTH_LONG).show();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(WebviewTencentActivity.this, "出现异常2",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    /**
     * 读取本地对象
     * @param context
     * @param fielName 文件名
     * @return
     */
    @SuppressWarnings("unchecked")
    public T readObjectFromLocal(Context context, String fielName){
        T bean;
        try {
            FileInputStream fis = context.openFileInput(fielName);//获得输入流
            ObjectInputStream ois = new ObjectInputStream(fis);
            bean = (T) ois.readObject();
            fis.close();
            ois.close();
            return bean;
        } catch (StreamCorruptedException e) {
            //Toast.makeText(ShareTencentActivity.this,"出现异常3",Toast.LENGTH_LONG).show();//弹出Toast消息
            e.printStackTrace();
            return null;
        } catch (OptionalDataException e) {
            //Toast.makeText(ShareTencentActivity.this,"出现异常4",Toast.LENGTH_LONG).show();//弹出Toast消息
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            //Toast.makeText(ShareTencentActivity.this,"出现异常5",Toast.LENGTH_LONG).show();//弹出Toast消息
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            //Toast.makeText(ShareTencentActivity.this,"出现异常6",Toast.LENGTH_LONG).show();//弹出Toast消息
            e.printStackTrace();
            return null;
        }
    }

}
