package com.usermanual.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.helper.Consts;
import com.usermanual.helper.StorageHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title.setText(bundle.getString(Consts.NEWS_TITLE));
            text.setText(bundle.getString(Consts.NEWS_TEXT));
            Picasso.get().load(StorageHelper.getUrl(bundle.getString(Consts.NEWS_IMAGE_KEY))).placeholder(R.mipmap.new_place).into(image);
        } else {
            image.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
        }

    }

}
