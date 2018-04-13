package com.example.wlj.fileproviderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final String PATH = "/data/user/0/com.example.wlj.fileproviderdemo/cache/themes/test.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(PATH);
        Log.e("AAA-->", "onCreate: " + file.getPath());
        try {
            InputStream inputStream = getAssets().open("main.png");
            writeFile(inputStream,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// 写入文件
    private static void writeFile(InputStream in, File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        byte[] buffer = new byte[8192];
        int read;

        while (-1 != (read = in.read(buffer))) {
            out.write(buffer, 0, read);
        }
        in.close();
        out.close();
    }
}
