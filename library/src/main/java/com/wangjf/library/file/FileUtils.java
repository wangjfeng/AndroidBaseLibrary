package com.wangjf.library.file;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.genvict.library.log.DLog;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjf on 2018/3/21.
 */

public class FileUtils {
    /***
     * copy file
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static boolean copyFile(File sourceFile, File targetFile) {
        boolean success = false;
        if (null == sourceFile || null == targetFile) {
            return success;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(targetFile);
            in = fis.getChannel();
            out = fos.getChannel();
            in.transferTo(0, in.size(), out);
            success = true;
        } catch (Exception e) {
            DLog.e("FileUtils" , "copyFile():"+e.getMessage());
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                DLog.e("FileUtils" , "copyFile():"+e.getMessage());
            }
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                DLog.e("FileUtils" , "copyFile():"+e.getMessage());
            }
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
                DLog.e("FileUtils" , "copyFile():"+e.getMessage());
            }
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                DLog.e("FileUtils" , "copyFile():"+e.getMessage());
            }
        }
        return success;
    }

    /***
     * save String into file
     *
     * @param folder
     * @param fileName
     * @param content
     * @return
     */
    public static boolean saveFile(String folder, String fileName, String content) {
        boolean success = false;
        if (TextUtils.isEmpty(content)) {
            return success;
        }
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        return saveFile(inputStream, folder, fileName);
    }

    /***
     * save stream into file
     *
     * @param inputStream
     * @param folder
     * @param fileName
     * @return
     */
    public static boolean saveFile(InputStream inputStream, String folder, String fileName) {
        boolean success = false;
        if (TextUtils.isEmpty(folder) || TextUtils.isEmpty(fileName) || null == inputStream) {
            return success;
        }
        File fileDir = createFolder(folder);
        File file = new File(fileDir, fileName);
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            while ((len = inputStream.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            DLog.e("FileUtils" , "saveFile():"+e.getMessage());
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                DLog.e("FileUtils" , "saveFile():"+e.getMessage());
            }
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                DLog.e("FileUtils" , "saveFile():"+e.getMessage());
            }
        }
        return success;
    }

    /**
     * read String from file
     *
     * @param filePath
     * @return
     */
    public static String readFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
                stringBuffer.append("\n");
            }
        } catch (Exception e) {
            DLog.e("FileUtils" , "readFile():"+e.getMessage());
            return null;
        } finally {
            try {
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
                DLog.e("FileUtils" , "readFile():"+e.getMessage());
            }
        }
        return stringBuffer.toString();
    }

    /***
     * delete file or folder
     *
     * @param file
     */
    public static void delete(File file) {
        if (null == file || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else {
            for (File f : file.listFiles()) {
                delete(f);
            }
            file.delete();
        }
    }

    /**
     * create folder by file path
     *
     * @param filePath
     * @return
     */
    public static File createFolder(String filePath) {
        File file = null;
        if (TextUtils.isEmpty(filePath)) {
            return file;
        }
        file = new File(filePath);
        if (null != file && !file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * cut file name by url contains suffix name
     *
     * @param url
     * @return
     */
    public static String buildFileNameByUrl(String url) {
        String fileName = null;
        if (!TextUtils.isEmpty(url)) {
            String tempUrl = url;
            int index = url.indexOf("?");
            if (index > 0) {
                tempUrl = url.substring(0, index);
            }
            index = tempUrl.lastIndexOf("/");
            fileName = tempUrl.substring(index + 1, tempUrl.length());
        }
        return fileName;
    }

    @SuppressWarnings("deprecation")
    public static long getExternalStorageFreeSize() {
        //MB
        String state = Environment.getExternalStorageState();
        if( Environment.MEDIA_MOUNTED.equals(state))
        {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            return availCount*blockSize/0x100000;
        }
        return 0;
    }

    @SuppressWarnings("deprecation")
    public static long getTFCardFreeSize(String path) {

        //MB
        File sdcardDir = new File(path);
        if (sdcardDir.exists()) {
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            return availCount*blockSize/0x100000;
        }
        return 0;
    }

    @SuppressWarnings("deprecation")
    public static long getSystemFreeSize() {
        //MB
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long blockCount = sf.getBlockCount();
        long availCount = sf.getAvailableBlocks();
        return availCount*blockSize/0x100000;
    }

    /**
     *  从assets目录中复制整个文件夹内容
     *  @param  context  Context 使用CopyFiles类的Activity
     *  @param  oldPath  String  原文件路径  如：/aa
     *  @param  newPath  String  复制后路径  如：xx:/bb/cc
     */
    public static void copyFilesFromAssets(Context context, String oldPath, String newPath) {
        try {
            //获取assets目录下的所有文件及目录名
            String fileNames[] = context.getAssets().list(oldPath);
            if (fileNames.length > 0) {
                //如果是目录
                File file = new File(newPath);
                //如果文件夹不存在，则递归
                file.mkdirs();
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context,oldPath + "/" + fileName,newPath+"/"+fileName);
                }
            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount=0;
                while((byteCount=is.read(buffer))!=-1) {
                    //循环从输入流读取 buffer字节
                    //将读取的输入流写入到输出流
                    fos.write(buffer, 0, byteCount);
                }
                //刷新缓冲区
                fos.flush();
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            DLog.e("FileUtils" , "copyFilesFromAssets():"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取文件夹下所有文件名
     *
     * @param folderPath
     * @param fileNameList
     * @return
     */
    public static List<String> getFolderFileNames(final String folderPath, List<String> fileNameList) {

        if (TextUtils.isEmpty(folderPath)){
            return null;
        }

        if (fileNameList == null) {
            fileNameList = new ArrayList<>();
        }
        File folder = new File(folderPath);
        if (!folder.exists()) {
            if(folder.mkdirs() || folder.isDirectory()){
                folder.setExecutable(true, false);
                folder.setReadable(true, false);
                folder.setWritable(true, false);
            }
        }

        File[] files = folder.listFiles();

        if (files != null) {

            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();

                if (files[i].isDirectory()) {
                    getFolderFileNames(files[i].getAbsolutePath(), fileNameList);
                } else {
                    fileNameList.add(fileName);
                }
            }
        }

        return fileNameList;
    }

    /**
     * 获取文件夹目录下所有文件
     *
     * @param folderPath
     * @param fileList
     * @return
     */
    public static List<File> getFolderFiles(final String folderPath, List<File> fileList) {

        if (fileList == null) {
            fileList = new ArrayList<>();
        }
        File folder = new File(folderPath);
        if (!folder.exists()) {
            if(folder.mkdirs() || folder.isDirectory()){
                folder.setExecutable(true, false);
                folder.setReadable(true, false);
                folder.setWritable(true, false);
            }
        }

        File[] files = folder.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {

                if (files[i].isDirectory()) {
                    getFolderFiles(files[i].getAbsolutePath(), fileList);
                } else {
                    fileList.add(files[i]);
                }
            }
        }
        return fileList;
    }

    /**
     * 通过反射调用获取内置存储和外置sd卡根路径(通用)
     *
     * @param mContext    上下文
     * @param is_removale 是否可移除，false返回内部存储，true返回外置sd卡
     * @return
     */
    public static String getStoragePath(Context mContext, boolean is_removale) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            DLog.e("FileUtils" , "getStoragePath():"+e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            DLog.e("FileUtils" , "getStoragePath():"+e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            DLog.e("FileUtils" , "getStoragePath():"+e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            DLog.e("FileUtils" , "getStoragePath():"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title: getExtSDCardPaths
     * @Description: to obtain storage paths, the first path is theoretically
     *               the returned value of
     *               Environment.getExternalStorageDirectory(), namely the
     *               primary external storage. It can be the storage of internal
     *               device, or that of external sdcard. If paths.size() >1,
     *               basically, the current device contains two type of storage:
     *               one is the storage of the device itself, one is that of
     *               external sdcard. Additionally, the paths is directory.
     * @return List<String>
     * @throws IOException
     */
    public static List<String> getExtSDCardPaths() {
        List<String> paths = new ArrayList<String>();
        String extFileStatus = Environment.getExternalStorageState();
        File extFile = Environment.getExternalStorageDirectory();
        if (extFileStatus.equals(Environment.MEDIA_MOUNTED)
                && extFile.exists() && extFile.isDirectory()
                && extFile.canWrite()) {
            paths.add(extFile.getAbsolutePath());
        }
        try {
            // obtain executed result of command line code of 'mount', to judge
            // whether tfCard exists by the result
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mount");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int mountPathIndex = 1;
            while ((line = br.readLine()) != null) {
                // format of sdcard file system: vfat/fuse
                if ((!line.contains("fat") && !line.contains("fuse") && !line
                        .contains("storage"))
                        || line.contains("secure")
                        || line.contains("asec")
                        || line.contains("firmware")
                        || line.contains("shell")
                        || line.contains("obb")
                        || line.contains("legacy") || line.contains("data")) {
                    continue;
                }
                String[] parts = line.split(" ");
                int length = parts.length;
                if (mountPathIndex >= length) {
                    continue;
                }
                String mountPath = parts[mountPathIndex];
                if (!mountPath.contains("/") || mountPath.contains("data")
                        || mountPath.contains("Data")) {
                    continue;
                }
                File mountRoot = new File(mountPath);
                if (!mountRoot.exists() || !mountRoot.isDirectory()
                        || !mountRoot.canWrite()) {
                    continue;
                }
                boolean equalsToPrimarySD = mountPath.equals(extFile
                        .getAbsolutePath());
                if (equalsToPrimarySD) {
                    continue;
                }
                paths.add(mountPath);
            }
        } catch (IOException e) {
            DLog.e("FileUtils" , "getExtSDCardPaths():"+e.getMessage());
            e.printStackTrace();
        }
        return paths;
    }

    /**
     * 删除文件夹中文件，并更新媒体库
     *
     * @param context
     * @param folderPath
     * @return
     */
    public static boolean deleteFiles(Context context, final String folderPath) {

        File folder = new File(folderPath);
        if (!folder.exists()) {
            return false;
        }

        File[] files = folder.listFiles();

        if (files != null) {

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    //递归删除子文件夹中的文件
                    deleteFiles(context, files[i].getAbsolutePath());
                } else {
                    File tmpFile = files[i];

                    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    ContentResolver contentResolver = context.getContentResolver();
                    String where = MediaStore.Images.Media.DATA + "='" + tmpFile.getAbsolutePath() + "'";
                    //删除图片
                    int count = contentResolver.delete(uri, where, null);
                    if(count > 0) {
                        //文件删除后，扫描文件，更新媒体库
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            new SingleMediaScanner(context, tmpFile);
                        } else {
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                        }
                    } else if(count == 0 && files[i].delete()){
                        //ContentResolver中无记录，直接删除
                        //文件删除后，扫描文件，更新媒体库
                        new SingleMediaScanner(context, tmpFile);
                    } else {
                        return false;
                    }
                }
            }

            //删除文件夹
            if(!folder.delete()) {
                return false;
            }
        }

        return true;
    }

    public static final String ACTION_MEDIA_SCANNER_SCAN_DIR = "android.intent.action.MEDIA_SCANNER_SCAN_DIR";
    public static void scanDirAsync(Context ctx, String dir) {
        Intent scanIntent = new Intent(ACTION_MEDIA_SCANNER_SCAN_DIR);
        scanIntent.setData(Uri.fromFile(new File(dir)));
        ctx.sendBroadcast(scanIntent);
    }

    /**
     *
     * @param folder
     * @param fileExt
     * @return
     */
    public static boolean deleteFilesAsExtInFolders(String folder, String fileExt) {
        File folderFile = new File(folder);
        if (folderFile.isDirectory()) {
            File tempFile = null;
            File[] filesList = folderFile.listFiles();
            for (int i = 0; i < filesList.length; i++) {
                tempFile = filesList[i];
                if (tempFile.getName().endsWith(fileExt)) {
                    tempFile.delete();
                }
            }
            return true;
        }

        return false;
    }

    /**
     *
     * @param context
     * @param filePath
     */
    public static void scanMediaFile(Context context, String filePath){
        Uri dataUri = Uri.parse(filePath);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, dataUri));
    }

    /**
     *
     * @param context
     * @param fileFolder
     */
    public static void scanMediaFolder(Context context, String fileFolder){
        try {
            List<File> fileList = null;
            fileList = getFolderFiles(fileFolder, fileList);
            String filePath = "";
            for (int i = 0; i < fileList.size(); i++) {
                filePath = "file://" + fileList.get(i).getAbsolutePath();
                scanMediaFile(context, filePath);
            }
        } catch (Exception e) {
            DLog.e("FileUtils" , "scanMediaFolder():"+e.getMessage());
        }
    }

    /**
     * 删除文件后更新数据库  通知媒体库更新文件夹
     * @param context
     * @param fileAbsolutePath
     */
    public static void updateFileFromDatabase(Context context, String fileAbsolutePath){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String[] paths = new String[]{Environment.getExternalStorageDirectory().toString()};
            MediaScannerConnection.scanFile(context, paths, null, null);
            MediaScannerConnection.scanFile(context,
                    new String[] { fileAbsolutePath }, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String arg0, Uri arg1) {
                        }
                    });
        } else {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }
}
