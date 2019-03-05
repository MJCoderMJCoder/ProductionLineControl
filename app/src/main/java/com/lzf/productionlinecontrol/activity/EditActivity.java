package com.lzf.productionlinecontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lzf.productionlinecontrol.R;
import com.lzf.productionlinecontrol.util.AppManager;
import com.lzf.productionlinecontrol.view.CursorListenerEditText;

public class EditActivity extends AppCompatActivity {

    private final String MJ_CODER = "MichaelJaimeCoder6003";
    private final float LINE_HEIGHT = 22.5f;
    private CursorListenerEditText editText;
    private String newFileNameValue;
    private String editTextValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
        setContentView(R.layout.activity_edit);
        newFileNameValue = getIntent().getStringExtra("newFileNameValue");
        TextView filename = findViewById(R.id.filename);
        filename.setText(newFileNameValue);
        editText = findViewById(R.id.editText);
        final TextView ranks = findViewById(R.id.ranks);
        final TextView etType = findViewById(R.id.etType);
        editText.setOnSelectionChangedListener(new CursorListenerEditText.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(int selStart, int selEnd) {
                try {
                    editTextValue = editText.getText().toString();
                    if (editTextValue.length() > 0) {
                        for (char c : editTextValue.toCharArray()) {
                            if (isChinese(c)) {
                                etType.setText("【汉      字】");
                                break;
                            } else {
                                etType.setText("【字      符】");
                            }
                        }
                    } else {
                        etType.setText("【空      白】");
                    }
                    int sumLines = (editTextValue + MJ_CODER).split("\n").length;
                    if (sumLines > 6) {
                        String temp = editTextValue.substring(0, selEnd);
                        editText.setText((temp + MJ_CODER).replace("\n" + MJ_CODER, "") + editTextValue.substring(selEnd));
                        Toast.makeText(EditActivity.this, "抱歉，总共可以移动6行！", Toast.LENGTH_SHORT).show();
                    } else {
                        String temp = editTextValue.substring(0, selEnd);
                        String[] tempSplit = (temp + MJ_CODER).split("\n");
                        if (tempSplit[tempSplit.length - 1].replace(MJ_CODER, "").length() > 500) {
                            editText.setText(temp.substring(0, selEnd - 1) + editTextValue.substring(selEnd));
                            Toast.makeText(EditActivity.this, "抱歉，总共可以移动500列！", Toast.LENGTH_SHORT).show();
                        } else {
                            ranks.setText("【行：" + (((sumLines - 1) - (tempSplit.length - 1)) * 8) + "，列：" + (tempSplit[tempSplit.length - 1].replace(MJ_CODER, "").length() * 6) + "】");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit:
                if (editTextValue == null) {
                    editTextValue = editText.getText().toString();
                }
                if (editTextValue.length() > 0) {
                    Intent intent = new Intent(this, SaveTxtActivity.class);
                    intent.putExtra("newFileNameValue", newFileNameValue);
                    intent.putExtra("editTextValue", editTextValue);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "当前未输入任何内容；请认真编辑您的文件。", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.editTextRelative:
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
                break;
            case R.id.close:
                AppManager.finishActivity();
                break;
            default:
                break;
        }
    }

    /**
     * 判断一个 char 是否是中文字符（CJK 分别代表中文、日文、韩文）
     *
     * @param c
     * @return
     */
    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
    }
}
