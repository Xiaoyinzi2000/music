package com.monk.player.tools;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.example.andy.player.R;
import com.monk.player.aidl.SongBean;
import com.monk.player.application.MyApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 文件工具类
 * Created by wcy on 2016/1/3.
 */
public class FileUtils {
    private static final String MP3 = ".mp3";
    private static final String LRC = ".lrc";

    private static String getAppDir() {
        return Environment.getExternalStorageDirectory() + "/FantasticMusic";
    }

    public static String getMusicDir() {
        String dir = getAppDir() + "/Music/";
        return mkdirs(dir);
    }

    public static String getLrcDir() {
        String dir = getAppDir() + "/Lyric/";
        return mkdirs(dir);
    }

    public static String getAlbumDir() {
        String dir = getAppDir() + "/Album/";
        return mkdirs(dir);
    }

    public static String getLogDir() {
        String dir = getAppDir() + "/Log/";
        return mkdirs(dir);
    }

    public static String getSplashDir(Context context) {
        String dir = context.getFilesDir() + "/splash/";
        return mkdirs(dir);
    }

    public static String getRelativeMusicDir() {
        String dir = "FantasticMusic/Music/";
        return mkdirs(dir);
    }

    public static String getCorpImagePath(Context context) {
        return context.getExternalCacheDir() + "/corp.jpg";
    }

    /**
     * 获取歌词路径<br>
     * 先从已下载文件夹中查找，如果不存在，则从歌曲文件所在文件夹查找。
     *
     * @return 如果存在返回路径，否则返回null
     */
    public static String getLrcFilePath(SongBean music) {
        if (music == null) {
            return null;
        }

        String lrcFilePath = getLrcDir() + getLrcFileName(music.getSingername(), music.getSongname());
        if (!exists(lrcFilePath)) {
            lrcFilePath = music.getPath().replace(MP3, LRC);
            if (!exists(lrcFilePath)) {
                lrcFilePath = null;
            }
        }
        return lrcFilePath;
    }

    private static String mkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    private static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static String getMp3FileName(String artist, String title) {
        return getFileName(artist, title) + MP3;
    }

    public static String getLrcFileName(String artist, String title) {
        return getFileName(artist, title) + LRC;
    }

    public static String getAlbumFileName(String artist, String title) {
        return getFileName(artist, title);
    }

    public static String getFileName(String artist, String title) {
        artist = stringFilter(artist);
        title = stringFilter(title);
        if (TextUtils.isEmpty(artist)) {
            artist = MyApplication.getContext().getString(R.string.unknown);
        }
        if (TextUtils.isEmpty(title)) {
            title = MyApplication.getContext().getString(R.string.unknown);
        }
        return artist + " - " + title;
    }

    public static String getArtistAndAlbum(String artist, String album) {
        if (TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            return "";
        } else if (!TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            return artist;
        } else if (TextUtils.isEmpty(artist) && !TextUtils.isEmpty(album)) {
            return album;
        } else {
            return artist + " - " + album;
        }
    }

    /**
     * 过滤特殊字符(\/:*?"<>|)
     */
    private static String stringFilter(String str) {
        if (str == null) {
            return null;
        }
        String regEx = "[\\/:*?\"<>|]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static float b2mb(int b) {
        String mb = String.format(Locale.getDefault(), "%.2f", (float) b / 1024 / 1024);
        return Float.valueOf(mb);
    }

    public static void saveLrcFile(String path, String content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File writeMp3File(String file_name,InputStream is){
        File file=new File(file_name);
        OutputStream os=null;
        try{
            os=new FileOutputStream(file);
            byte buffer[]=new byte[1024];

            int len = 0;
            while((len = is.read(buffer)) != -1){
                os.write(buffer,0,len);
            }
            os.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                os.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return file;
    }
}
