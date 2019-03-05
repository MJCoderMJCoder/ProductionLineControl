package com.lzf.productionlinecontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lzf.productionlinecontrol.LzfApplication;
import com.lzf.productionlinecontrol.R;
import com.lzf.productionlinecontrol.bean.Text;
import com.lzf.productionlinecontrol.util.AppManager;

public class NewTxtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
        setContentView(R.layout.activity_new_txt);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                AppManager.finishActivity();
                break;
            case R.id.cancel:
                AppManager.finishActivity();
                break;
            case R.id.create:
                EditText newFileName = findViewById(R.id.newFileName);
                String newFileNameValue = newFileName.getText().toString().trim();
                if (newFileNameValue.length() > 0) {
                    if (fileNameExit(newFileNameValue)) {
                        Toast.makeText(this, "该文件名已存在，请重新命名。", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(this, EditActivity.class);
                        intent.putExtra("newFileNameValue", newFileNameValue);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "请输入您将要创建的文件名", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private boolean fileNameExit(String newFileNameValue) {
        for (Text text : LzfApplication.textList) {
            if (newFileNameValue.equals(text.getTextName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
    }
}
