package com.example.comic_demo.mvp.ui.holder;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comic_demo.R;
import com.example.comic_demo.mvp.model.entity.MineTitle;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: 小川
 * Date: 2019/9/14
 * Description:
 */
public class MineHolder extends BaseHolder<MineTitle> {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_mine_icon)
    ImageView iv_mine_icon;
    @BindView(R.id.tv_size)
    TextView tv_size;

    public MineHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull MineTitle data, int position) {
        tv_title.setText(data.getTitle());
        if(position == 1){
            tv_size.setVisibility(View.VISIBLE);
            //            tv_size.setText(data.getSize());
        }
        if(position == 3){
            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = "https://github.com/yidao999";
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
                    tv_size.getContext().startActivity(intent);
                }
            });
        }
        iv_mine_icon.setImageResource(data.getResID());
    }
}
