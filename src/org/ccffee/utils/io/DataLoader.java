package org.ccffee.utils.io;

import java.io.*;
import java.util.Arrays;

public final class DataLoader {

    private DataLoader() {

    }
    public static Data[][] loadData(InputStream in)throws IOException {
        byte[][][] b = load(in);
        Data[][] data = new Data[b.length][];
        for(int l = 0; l<b.length;l++){
            data[l]=new Data[b[l].length];
            for(int c = 0; c<b[l].length;c++)data[l][c]=new Data(b[l][c]);
        }
        return data;
    }
    public static Data[][] loadData(File file)throws IOException {
        FileInputStream in = new FileInputStream(file);
        Data[][] d = loadData(in);
        in.close();
        return d;
    }
    public static byte[][][] load(File file)throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[][][] d = load(in);
        in.close();
        return d;
    }
    public static byte[][][] load(InputStream in) throws IOException {
        byte[][][] data = new byte[0][0][];
        int bte = in.read();
        int l=-1,c=-1,i=0;
        boolean next = false;
        while(bte != -1) {
            if(l==-1||c==-1 || i>=data[l][c].length) {
                if(bte == 251) {
                    l++;
                    c=-1;
                    data = Arrays.copyOf(data, l+1);
                    data[l] = new byte[0][];
                }
                else if(l==-1)throw new StreamFormatException("missing next line byte");
                else if(c!=-1&&data[l][c].length!=0&&data[l][c].length%250==0&&!next) {
                    if(bte!=0)data[l][c] = Arrays.copyOf(data[l][c], data[l][c].length+bte);
                    else next = true;
                }
                else {
                    next = false;
                    c++;
                    i=0;
                    data[l] = Arrays.copyOf(data[l], c+1);
                    data[l][c]= new byte[bte];
                }
            }else {
                data[l][c][i] = (byte) bte;
                i++;
            }
            bte = in.read();
        }
        return data;
    }
    public static void save(Data[][] data, File out) throws IOException {
        FileOutputStream o = new FileOutputStream(out);
        save(data,o);
        o.close();
    }
    public static void save(byte[][][] data, File out) throws IOException {
        FileOutputStream o = new FileOutputStream(out);
        save(data,o);
        o.close();
    }
    public static void save(Data[][] data, OutputStream out) throws IOException {
        for(int l = 0; l<data.length;l++) {
            out.write(251);
            for(int c = 0;c<data[l].length;c++) {
                byte[] dat = data[l][c].toByteArray();
                if(data[l][c]!=null) {
                    int n;
                    for(n = 0; n<dat.length; n++) {
                        if(n%250==0)out.write((dat.length-n)<250?dat.length-n:250);
                        out.write(dat[n]);
                    }
                    if(dat.length%250==0)out.write(0);
                }else out.write(0);
            }
        }
    }
    public static void save(byte[][][] data, OutputStream out) throws IOException {
        for(int l = 0; l<data.length;l++) {
            out.write(251);
            for(int c = 0;c<data[l].length;c++) {
                if(data[l][c]!=null) {
                    int n;
                    for(n = 0; n<data[l][c].length; n++) {
                        if(n%250==0)out.write((data[l][c].length-n)<250?data[l][c].length-n:250);
                        out.write(data[l][c][n]);
                    }
                    if(data[l][c].length%250==0)out.write(0);
                }else out.write(0);
            }
        }
    }

}
