package com.wangjf.library.file;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

/**
 * Created by wangjf on 2018/3/21.
 */

public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
    private MediaScannerConnection mMsc;
    private File mFile;
    public SingleMediaScanner(Context context, File f) {
        mFile = f;
        mMsc = new MediaScannerConnection(context, this);
        mMsc.connect();
    }
    @Override
    public void onMediaScannerConnected() {
        mMsc.scanFile(mFile.getAbsolutePath(), null);
    }
    @Override
    public void onScanCompleted(String s, Uri uri) {
        mMsc.disconnect();
    }
}
