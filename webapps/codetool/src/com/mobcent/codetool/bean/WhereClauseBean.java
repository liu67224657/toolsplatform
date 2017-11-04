package com.mobcent.codetool.bean;

public class WhereClauseBean {
    //not in, in , like, = 
    private String flag;
     //行名
    private String columnName;
    //list的名字 $list1, 
    private String listName;
    //表别名 t1.user_id中的t1
    private String label;
    //存放标志的为  比如  in ,  like ,>,<
    
    public String getFlag() {
        return flag;
    }
    public String getColumnName() {
        return columnName;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getListName() {
        return listName;
    }
    public void setListName(String listName) {
        this.listName = listName;
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
}
