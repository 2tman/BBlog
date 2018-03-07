package iandroid.club.bblogs.entity;

/**
 * @Description: 博客类型枚举
 * @Author: 2tman
 * @Time: 2018/1/24
 */
public enum Category {

    CSDN_BLOG("CSDN博客", 1),
    JIANSHU_BLOG("CSDN博客", 2);

    private String name;
    private int value;

    Category(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
