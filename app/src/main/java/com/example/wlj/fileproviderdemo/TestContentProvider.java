package com.example.wlj.fileproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

/**
 * Created by wlj on 18-4-13.
 */

public class TestContentProvider extends ContentProvider {
    private static final String FILE_AUTHORITY = "com.example.wlj.fileproviderdemo"; // 和清单文件注册的一样

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "com.example.wlj.fileproviderdemo.testContentProvider"; // 和注册的一样
    private static final int WALLPAPER = 9;

    static {
        sUriMatcher.addURI(AUTHORITY, "/wallpaper/small/*", WALLPAPER);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.e("AAA-->", "getType: " + uri.toString() );
        int match = sUriMatcher.match(uri);
        String packageName = null;
        switch (match) {
            case WALLPAPER:
                packageName = uri.getLastPathSegment();
                break;
        }
        // 将文件的Uri 以 String传递出去
        return getFileUri(packageName, MainActivity.PATH).toString();
    }

    // 获取文件Uri 地址
    private Uri getFileUri(String packageName, String path) {
        Uri uri;
        uri = FileProvider.getUriForFile(getContext(), FILE_AUTHORITY,
                new File(path));

        granUriPermission(getContext(), packageName, uri);
        return uri;
    }

    // 赋予临时权限
    private void granUriPermission(Context context, String packageName, Uri uri) {
        context.grantUriPermission(packageName, uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
