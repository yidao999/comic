package com.example.comic_demo.mvp.ui.binder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comic_demo.R;
import com.example.comic_demo.app.Constants;
import com.example.comic_demo.mvp.model.entity.HomeTitleEntity;
import com.example.comic_demo.mvp.ui.activity.CategoryActivity;
import com.example.comic_demo.mvp.ui.activity.RankActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * author: 小川
 * Date: 2019/8/15
 * Description:
 */
public class HomeTitleBinder extends ItemViewBinder<HomeTitleEntity, HomeTitleBinder.Holder> {
    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.item_home_title, parent, false);
        return new Holder(inflate);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull HomeTitleEntity item) {
        int position = getPosition(holder);
        if (position == 1) {
            holder.view.setVisibility(View.GONE);
        }
        holder.title.setText(item.getItemTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                switch (position) {
                    case 1:
                        CategoryActivity.launchActivity(context, Constants.CATEGORY_TITLE_FINISH, 1);
                        break;
                    case 2:
                        CategoryActivity.launchActivity(context, Constants.CATEGORY_TITLE_AUDIENCE, 1);
                        break;
                    case 9:
                        CategoryActivity.launchActivity(context, Constants.CATEGORY_TITLE_AUDIENCE, 2);
                        break;
                    case 16:
                        CategoryActivity.launchActivity(context, Constants.CATEGORY_TITLE_NATION, 4);
                        break;
                    case 20:
                        Intent intent = new Intent(context, RankActivity.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_hometitle)
        TextView title;
        @BindView(R.id.v_padding)
        View view;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
