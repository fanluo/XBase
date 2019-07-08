package com.allens.lib_base.retrofit.download.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 下载状态
 */

@Getter
public enum DownState {
    START(0),
    DOWN(1),
    PAUSE(2),
    STOP(3),
    ERROR(4),
    FINISH(5);
    private int state;

    DownState(int state) {
        this.state = state;
    }
}
