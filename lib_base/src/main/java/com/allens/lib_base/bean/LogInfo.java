package com.allens.lib_base.bean;


import com.allens.lib_base.app.BaseApplication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogInfo {

    /***
     * 单个文件最大多少M
     */
    public int maxM = 3;

    /**
     * 最大文件数
     */
    public int maxFileSize = 5;

    /***
     * 文件名称
     */
    public String fileName = "logs";

    //tag
    public String tag;

    /**
     * isOpen
     */
    public boolean isOpen;

    /**
     * 保存文件地址
     */
    public String path ;


}
