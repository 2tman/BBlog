package iandroid.club.bblogs;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iandroid.club.bbase_lib.activity.BaseActivity;
import iandroid.club.bblogs.entity.Article;
import iandroid.club.bblogs.entity.Blog;
import iandroid.club.bblogs.entity.Category;

/**
 * @Description: 主界面
 * @Author: 2tman
 * @Time: 2018/1/24
 */
public class MainActivity extends BaseActivity {

    /**
     * 博客列表集合
     */
    private List<Blog> mData = new ArrayList<>();

    private final String CSDN_BASE_URL = "http://blog.csdn.net";
    private final String JIANSHU_BASE_URL = "https://www.jianshu.com";

    private RecyclerView.Adapter adapter;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerview);
        ButterKnife.bind(this);
        mProgressBar.setVisibility(View.GONE);

        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);

        initData();

        initRecyclerView();

        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mData.clear();

        mData.add(new Blog(Category.JIANSHU_BLOG,"u/4f2c483c12d8", JIANSHU_BASE_URL, "沈哲");
        mData.add(new Blog(Category.JIANSHU_BLOG,"u/5d38c81be78e", JIANSHU_BASE_URL, "刘望舒"));
        mData.add(new Blog(Category.JIANSHU_BLOG,"u/4ef984470da8", JIANSHU_BASE_URL, "《Android高级进阶》作者"));
        mData.add(new Blog(Category.JIANSHU_BLOG,"u/cd0fe10b01d2", JIANSHU_BASE_URL, "CangWang"));
        mData.add(new Blog(Category.CSDN_BLOG, "lmj623565791", CSDN_BASE_URL, "张鸿洋"));
        mData.add(new Blog(Category.JIANSHU_BLOG,"u/383970bef0a0", JIANSHU_BASE_URL, "Carson_Ho"));
        mData.add(new Blog(Category.JIANSHU_BLOG,"u/3ed7c63149f2", JIANSHU_BASE_URL, "骆驼骑士"));
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
                        LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_item_blog, parent, false));
                return commonViewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((MyViewHolder) holder).setData(position, mData.get(position));
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_blog;
        TextView tv_category;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_blog = (TextView) itemView.findViewById(R.id.tv_blog);
            tv_category = (TextView) itemView.findViewById(R.id.tv_category);
        }

        public void setData(int position, final Blog blog) {
            tv_blog.setText(blog.getBlogTitle());
            tv_category.setText(blog.getCategory().getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CrawlerActivity.class);
                    intent.putExtra("blog", blog);
                    startActivity(intent);
                }
            });
        }
    }
}
