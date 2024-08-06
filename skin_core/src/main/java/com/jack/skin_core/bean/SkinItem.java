package com.jack.skin_core.bean;

public class SkinItem {
    //属性的名字  textColor text background
    String name;
    //属性的值的类型 color mipmap
    String typeName;
    //属性的值的名字  colorPrimary
    String entryName;
    //属性的资源ID
    int resId;


    public SkinItem(String name, String typeName, String entryName, int resId) {
        this.name = name;
        this.typeName = typeName;
        this.entryName = entryName;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getEntryName() {
        return entryName;
    }

    public int getResId() {
        return resId;
    }

}
