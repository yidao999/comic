package com.example.comic_demo.mvp.model.entity;

/**
 * author: 小川
 * Date: 2019/9/5
 * Description:
 */
public enum  DownState {
    START(0),
    DOWN(1),
    PAUSE(2),
    STOP(3),
    ERROR(4),
    FINISH(5),
    NONE(6),
    DELETE(-1),
    CACHE(-2);
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    DownState(int state) {
        this.state = state;
    }
}
