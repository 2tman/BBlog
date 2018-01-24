package iandroid.club.bblogs;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 网络爬虫
 */
public class CrawlerActivity extends AppCompatActivity {

    private String blogid = "lmj623565791";
    private String baseUrl = "http://blog.csdn.net/";

    private RecyclerView mRecyclerView;
    private List<Article> articleList = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;

    @OnClick(R.id.btn_crawler)
    public void crawlerClick() {
        mProgressBar.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                super.run();
                jsoupGet();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawler);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        initRecyclerView();
    }

    private void initRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.drawable_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                MyViewHolder commonViewHolder = new MyViewHolder(
                        LayoutInflater.from(CrawlerActivity.this).inflate(R.layout.layout_item_article, parent, false));
                return commonViewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((MyViewHolder) holder).setData(position, articleList.get(position));
            }

            @Override
            public int getItemCount() {
                return articleList.size();
            }
        });
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_desc;
        TextView tv_time;
        TextView tv_read_count;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_read_count = (TextView) itemView.findViewById(R.id.tv_read_count);
        }

        public void setData(int position, final Article article) {
            tv_title.setText(article.getArticleTitle());
            tv_desc.setText(article.getArticleDesc());
            tv_time.setText(article.getCreatedTime());
            tv_read_count.setText(article.getReadCount());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转详情
                    Toast.makeText(itemView.getContext(), "文字id:" + article.getArticleId() + " 文字标题：" + article.getArticleTitle(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    private void jsoupGet() {

        try {
            Connection connection = Jsoup.connect(baseUrl + blogid);
            // 修改http包中的header,伪装成浏览器进行抓取
//            connection.header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Mobile Safari/537.36");
            connection.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
            Document doc = connection.get();
            Elements articleList = doc.select("div.list_item");

            if (articleList != null && articleList.size() > 0) {
                List<Article> list = new ArrayList<>();
                for (Element articleItem : articleList) {
                    String title = articleItem.select("span.link_title").select("a").text();
                    String desc = articleItem.select("div.article_description").text();
                    String date = articleItem.select("div.article_manage").select("span.link_postdate").text();
                    String readCount = articleItem.select("div.article_manage").select("span.link_view").after("a").text();
                    String href = articleItem.select("span.link_title").select("a").attr("href");
                    String articleId = href.replace("/" + blogid + "/article/details/", "");
                    Article article = new Article();
                    article.setArticleTitle(title);
                    article.setArticleDesc(desc);
                    article.setReadCount(readCount);
                    article.setCreatedTime(date);
                    article.setArticleId(articleId);
                    list.add(article);

                }
                if (list != null && list.size() > 0) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = list;
                    handler.sendMessage(message);
                } else {
                    handler.sendEmptyMessage(0);
                }
            } else {
                handler.sendEmptyMessage(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(-1);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mProgressBar.setVisibility(View.GONE);
            if (msg.what == 1) {
                if (msg.obj != null) {
                    List<Article> article = (List<Article>) msg.obj;
                    articleList.clear();
                    articleList.addAll(article);
                    adapter.notifyDataSetChanged();
                    String json = getGson(article);
                    Logger.e(json);
                }
            }
        }
    };

    private static String getGson(Object obj) {
        return new Gson().toJson(obj);
    }
}
