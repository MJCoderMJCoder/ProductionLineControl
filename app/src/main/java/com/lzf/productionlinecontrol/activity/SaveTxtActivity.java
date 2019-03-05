package com.lzf.productionlinecontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lzf.productionlinecontrol.R;
import com.lzf.productionlinecontrol.util.AppManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SaveTxtActivity extends AppCompatActivity {

    private String newFileNameValue;
    private String editTextValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
        setContentView(R.layout.activity_save_txt);
        Intent intent = getIntent();
        if (intent != null) {
            newFileNameValue = getIntent().getStringExtra("newFileNameValue");
            editTextValue = getIntent().getStringExtra("editTextValue");
        }
        TextView isSaveHint = findViewById(R.id.isSaveHint);
        isSaveHint.setText("确定要保存文本： " + newFileNameValue + "  吗？");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                saveFile();
                break;
            case R.id.cancel:
                AppManager.finishActivity(EditActivity.class);
                AppManager.finishActivity(NewTxtActivity.class);
                AppManager.finishActivity(SaveTxtActivity.class);
                break;
            case R.id.back:
                AppManager.finishActivity();
                break;
            case R.id.close:
                AppManager.finishActivity();
                break;
            default:
                break;
        }
    }

    /**
     * 保存文件
     */
    private void saveFile() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    File dir;
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        dir = new File(Environment.getExternalStorageDirectory(), "MJCoder/.zff"); //MJCoder/.zff
                    } else {
                        String dirTemp = null;
                        StorageManager storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
                        Class<?>[] paramClasses = {};
                        Method getVolumePathsMethod;
                        try {
                            getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
                            // 在反射调用之前将此对象的 accessible 标志设置为 true，以此来提升反射速度。
                            getVolumePathsMethod.setAccessible(true);
                            Object[] params = {};
                            Object invoke = getVolumePathsMethod.invoke(storageManager, params);
                            for (int i = 0; i < ((String[]) invoke).length; i++) {
                                if (!((String[]) invoke)[i].equals(Environment.getExternalStorageDirectory().toString())) {
                                    dirTemp = ((String[]) invoke)[i];
                                }
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        dir = new File(dirTemp, "MJCoder/.zff"); //MJCoder/.zff
                    }
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File currentFile = new File(dir, newFileNameValue);
                    if (!currentFile.exists()) {
                        try {
                            currentFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(currentFile); // 构建FileOutputStream对象,文件不存在会自动新建
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");  // 构建OutputStreamWriter对象,参数可以指定编码,默认为操作系统默认编码,windows上是gbk
                    outputStreamWriter.append(editTextValue); // 写入到缓冲区,写入到文件,如果下面已经没有写入的内容了,直接close也会写入
                    outputStreamWriter.flush();
                    fileOutputStream.flush();
                    outputStreamWriter.close(); //关闭写入流,同时会把缓冲区内容写入文件,所以上面的注释掉
                    fileOutputStream.close();//关闭数据流
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AppManager.finishActivity(EditActivity.class);
                            AppManager.finishActivity(NewTxtActivity.class);
                            AppManager.finishActivity(SaveTxtActivity.class);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
    }
}
