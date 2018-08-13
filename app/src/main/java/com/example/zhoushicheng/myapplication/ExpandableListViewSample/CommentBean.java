package com.example.zhoushicheng.myapplication.ExpandableListViewSample;

import java.util.List;

/**
 * Created by zhoushicheng on 2018/5/10.
 */

public class CommentBean {
    String name;
    String comment;
    boolean isFavor;
    List<CommentReplyBean> replys;

    public CommentBean(String name, String comment, boolean isFavor, List<CommentReplyBean> replys) {
        this.name = name;
        this.comment = comment;
        this.isFavor = isFavor;
        this.replys = replys;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public boolean isFavor() {
        return isFavor;
    }

    public List<CommentReplyBean> getReplys() {
        return replys;
    }
}
