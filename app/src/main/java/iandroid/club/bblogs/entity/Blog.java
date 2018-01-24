package iandroid.club.bblogs.entity;

import java.io.Serializable;

/**
 * @Description: 博客类型
 * @Author: 2tman
 * @Time: 2018/1/24
 */
public class Blog implements Serializable{

    //博客类型
    private Category category;

    //博客id
    private String blogid;

    //博客url
    private String baseUrl;

    //博客名
    private String blogTitle;

    public Blog(Category category, String blogid, String baseUrl, String blogTitle) {
        this.category = category;
        this.blogid = blogid;
        this.baseUrl = baseUrl;
        this.blogTitle = blogTitle;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBlogid() {
        return blogid;
    }

    public void setBlogid(String blogid) {
        this.blogid = blogid;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }
}
