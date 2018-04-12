package com.ritvi.kaajneeti.pojo.analyze;

/**
 * Created by sunil on 31-03-2018.
 */

public class AnalyzeCategoryPOJO {
    String type;
    String count;

    public AnalyzeCategoryPOJO(String type, String count) {
        this.type = type;
        this.count = count;
    }

    public AnalyzeCategoryPOJO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
