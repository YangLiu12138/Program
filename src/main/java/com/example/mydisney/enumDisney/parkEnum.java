package com.example.mydisney.enumDisney;

public enum parkEnum {
    P1("申迪P1", 485),
    PARKTOWN("迪士尼小镇停车场", 321),
    P4("申迪P4", 1694),
    PARK("迪士尼乐园游客停车场",3571),
    P2("申迪P2",1707),
    P6("申迪P6",2013),
    P3("申迪P3",797);

    // 成员变量  
    private String name;  
    private int index;  
    // 构造方法  
    private parkEnum(String name, int index) {
        this.name = name;  
        this.index = index;  
    }  
    // 普通方法  
    public static Integer getIndex(String name) {
        for (parkEnum c : parkEnum.values()) {
            if (c.getName().equals(name)) {
                return c.index;
            }  
        }  
        return null;  
    }  
    // get set 方法  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public int getIndex() {  
        return index;  
    }  
    public void setIndex(int index) {  
        this.index = index;  
    }  
}  