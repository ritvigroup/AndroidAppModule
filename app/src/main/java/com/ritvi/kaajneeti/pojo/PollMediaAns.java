package com.ritvi.kaajneeti.pojo;

/**
 * Created by sunil on 16-04-2018.
 */

public class PollMediaAns {
    String file_path="";
    String ans="";

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    @Override
    public String toString() {
        return "PollMediaAns{" +
                "file_path='" + file_path + '\'' +
                ", ans='" + ans + '\'' +
                '}';
    }
}
