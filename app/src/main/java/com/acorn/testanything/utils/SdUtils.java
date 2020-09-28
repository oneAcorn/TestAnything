package com.acorn.testanything.utils;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.RawRes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by acorn on 2020/9/27.
 */
class SdUtils {
    public static void saveToSDCard(Context context, String path, @RawRes int resId) throws Throwable {
        InputStream inStream = context.getResources().openRawResource(resId);
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);//存入SDCard
        byte[] buffer = new byte[10];
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] bs = outStream.toByteArray();
        fileOutputStream.write(bs);
        outStream.close();
        inStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static String copyResources(Context context, int resId) {
        InputStream in = context.getResources().openRawResource(resId);
        String filename = context.getResources().getResourceEntryName(resId);

        File f = new File(filename);
        String resPath = null;
        try {
            File outFile = new File(Environment.getExternalStorageDirectory() + "/config", filename);
            resPath = outFile.getPath();
            OutputStream out = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resPath;
    }
}
