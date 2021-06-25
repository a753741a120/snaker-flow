package com.snaker.framework.utils;

import com.mysql.cj.jdbc.Blob;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @基本功能:
 * @program:demo
 * @author:pm
 * @create:2021-06-22 16:52:08
 **/
public class BlobUtils {

    /**
     * blob转byte
     * @param blob
     * @return
     * @throws Exception
     */
    public byte[] blobToByte(Blob blob) throws Exception {
        byte[] bytes = null;
        try {
            InputStream in=blob.getBinaryStream();
            BufferedInputStream inBuffered = new BufferedInputStream(in);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = inBuffered.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            inBuffered.close();
            in.close();
            bytes = out.toByteArray();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return bytes;
    }
}