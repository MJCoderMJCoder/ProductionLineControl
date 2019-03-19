package com.lzf.productionlinecontrol.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzf.productionlinecontrol.LzfApplication;
import com.lzf.productionlinecontrol.R;
import com.lzf.productionlinecontrol.bean.Text;
import com.lzf.productionlinecontrol.util.AppManager;
import com.lzf.productionlinecontrol.util.ReusableAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TextManagerActivity extends AppCompatActivity {

    private ReusableAdapter<Text> reusableAdapter;
    private TextView txtContent;
    private Text textCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
        setContentView(R.layout.activity_text_manager);
        txtContent = findViewById(R.id.txtContent);
        ListView txtNameList = findViewById(R.id.txtNameList);
        reusableAdapter = new ReusableAdapter<Text>(LzfApplication.textList, R.layout.item_textname) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void bindView(ViewHolder holder, Text obj) {
                holder.setText(R.id.txtName, obj.getTextName());
                if (obj.isSelected()) {
                    holder.setBackgroundColor(R.id.txtNameRelative, Color.BLUE);
                    holder.setTextColor(R.id.txtName, Color.WHITE);
                    holder.setBackground(R.id.box, R.drawable.box_white);
                    if (obj.isLoaded()) {
                        holder.setImageResource(R.id.box, R.drawable.tick_white);
                    } else {
                        holder.setImageResource(R.id.box, R.drawable.tick_lucency);
                    }
                } else {
                    holder.setBackgroundColor(R.id.txtNameRelative, Color.WHITE);
                    holder.setTextColor(R.id.txtName, Color.BLACK);
                    holder.setBackground(R.id.box, R.drawable.box_black);
                    if (obj.isLoaded()) {
                        holder.setImageResource(R.id.box, R.drawable.tick_black);
                    } else {
                        holder.setImageResource(R.id.box, R.drawable.tick_lucency);
                    }
                }
            }
        };
        txtNameList.setAdapter(reusableAdapter);
        txtNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (LzfApplication.textList.get(i).isSelected()) {
                    textCache = LzfApplication.textList.get(i);
                    textCache.setSelected(false);
                    textCache = null;
                    txtContent.setText("");
                } else {
                    if (textCache != null) {
                        textCache.setSelected(false);
                    }
                    textCache = LzfApplication.textList.get(i);
                    textCache.setSelected(true);
                    txtContent.setText(textCache.getTextContent());
                }
                reusableAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        readFile();
        txtContent.setText("");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                AppManager.finishActivity();
                break;
            case R.id.newTxt:
                startActivity(new Intent(this, NewTxtActivity.class));
                break;
            case R.id.delete:
                if (textCache != null) {
                    if (textCache.isLoaded()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        AlertDialog alert = builder.setIcon(R.mipmap.ic_launcher)
                                .setTitle("警告")
                                .setMessage("该文件正在使用！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();             //创建AlertDialog对象
                        alert.show();                    //显示对话框
                    } else {
                        if (textCache.getFile().delete()) {
                            reusableAdapter.delete(textCache);
                            txtContent.setText("");
                        }
                    }
                } else {
                    Toast.makeText(this, "请先选中一个文本", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.load:
                if (textCache != null) {
                    textCache.setLoaded(true);
                    reusableAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "请先选中一个文本", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void readFile() {
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
                    if (dir.isDirectory()) {
                        String s[] = dir.list();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                reusableAdapter.clear();
                            }
                        });
                        for (int i = 0; i < s.length; i++) {
                            File currentFile = new File(dir, s[i]);
                            FileInputStream fileInputStream = new FileInputStream(currentFile);  // 构建FileInputStream对象
                            InputStreamReader reader = new InputStreamReader(fileInputStream, "UTF-8"); // 构建InputStreamReader对象,编码与写入相同
                            StringBuffer stringBuffer = new StringBuffer();
                            while (reader.ready()) {
                                stringBuffer.append((char) reader.read());  // 转成char加到StringBuffer对象中
                            }
                            final Text text = new Text(currentFile, s[i], stringBuffer.toString(), false, false);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    reusableAdapter.add(text);
                                }
                            });
                            reader.close();  // 关闭读取流
                            fileInputStream.close();  // 关闭输入流,释放系统资源
                        }
                    }
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
