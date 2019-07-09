package com.allens.lib_base.retrofit.upload.bean;

import java.io.File;

import lombok.Data;

@Data
public class UploadBean {

    private File file;
    private String type;
}
