package com.lzf.productionlinecontrol.bean;

import java.io.File;

/**
 * Created by MJCoder on 2019-03-01.
 */
public class Text {
    private File file;  //该文本的存储载体
    private String textName; //该文本标题
    private String textContent; //该文本内容
    private boolean loaded; //该文本是否已经被加载
    private boolean selected; //该文本是否已经被选中

    public Text() {
    }

    public Text(File file, String textName, String textContent, boolean loaded, boolean selected) {
        this.file = file;
        this.textName = textName;
        this.textContent = textContent;
        this.loaded = loaded;
        this.selected = selected;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Text{" +
                "file=" + file +
                ", textName='" + textName + '\'' +
                ", textContent='" + textContent + '\'' +
                ", loaded=" + loaded +
                ", selected=" + selected +
                '}';
    }
}
