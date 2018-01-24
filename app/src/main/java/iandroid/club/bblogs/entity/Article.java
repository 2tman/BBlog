package iandroid.club.bblogs.entity;

import java.io.Serializable;

/**
 * @Description:
 * @Author: 2tman
 * @Time: 2017/11/2
 */
public class Article implements Serializable{

    //文章id
    private String articleId;
    //文章标题
    private String articleTitle;
    //文章内容
    private String articleContent;
    //文章简述
    private String articleDesc;
    //阅读次数
    private String readCount;
    //时间
    private String createdTime;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
