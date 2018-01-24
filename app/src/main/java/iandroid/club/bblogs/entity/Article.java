package iandroid.club.bblogs.entity;

import org.jsoup.nodes.Element;

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
    //详情url
    private String detailUrl;

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

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    /**
     * csdn blog 解析
     *
     * @param articleItem
     * @return
     */
    public static Article getCsdnBlog(Element articleItem) {
        String title = articleItem.select("span.link_title").select("a").text();
        String desc = articleItem.select("div.article_description").text();
        String date = articleItem.select("div.article_manage").select("span.link_postdate").text();
        String readCount = articleItem.select("div.article_manage").select("span.link_view").after("a").text();
        String href = articleItem.select("span.link_title").select("a").attr("href");
        Article article = new Article();
        article.setArticleTitle(title);
        article.setArticleDesc(desc);
        article.setReadCount(readCount);
        article.setCreatedTime(date);
        article.setDetailUrl(href);
        return article;
    }

    /**
     * 简书blog解析
     *
     * @param articleItem
     * @return
     */
    public static Article getJianshuBlog(Element articleItem) {
        String title = articleItem.select("a.title").text();
        String desc = articleItem.select("p.abstract").text();
        String date = articleItem.select("div.info").select("span.link_postdate").text();
        String readCount = articleItem.select("div.article_manage").select("span.time").text();
        String href = articleItem.select("a.title").attr("href");
        Article article = new Article();
        article.setArticleTitle(title);
        article.setArticleDesc(desc);
        article.setReadCount(readCount);
        article.setCreatedTime(date);
        article.setDetailUrl(href);
        return article;
    }
}
