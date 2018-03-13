package com.ritvi.kaajneeti.fragment.homeactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.activity.AddPostActivity;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.testing.FacebookMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 12-03-2018.
 */

public class KaajFragment extends Fragment{

    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.tv_whats_mind)
    TextView tv_whats_mind;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;

    UserProfilePOJO userProfilePOJO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_kaaj,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FacebookMainActivity.class));
            }
        });


        tv_whats_mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddPostActivity.class));
            }
        });

        if(getActivity() instanceof HomeActivity){
            HomeActivity homeActivity= (HomeActivity) getActivity();
            userProfilePOJO=homeActivity.userProfilePOJO;

            inflateNewsFeeds();
            tv_profile_name.setText(userProfilePOJO.getFullname());

            Glide.with(getActivity().getApplicationContext())
                    .load(userProfilePOJO.getProfileImage())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);

        }
    }

    public void inflateNewsFeeds(){
        for(int i=0;i<10;i++){
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = null;
            if(i%4==0){
                view = inflater.inflate(R.layout.inflate_poll_feed, null);
            }else if(i%4==1){
                view = inflater.inflate(R.layout.inflate_event_feed, null);

                final TextView tv_title=view.findViewById(R.id.tv_title);
                final LinearLayout ll_preview=view.findViewById(R.id.ll_preview);

                final View preview_card=inflater.inflate(R.layout.preview_content,null);

                final ImageView image_post_set=preview_card.findViewById(R.id.image_post_set);
                final TextView title=preview_card.findViewById(R.id.title);
                final TextView url=preview_card.findViewById(R.id.url);
                final TextView description=preview_card.findViewById(R.id.description);


                TextCrawler textCrawler = new TextCrawler();

                LinkPreviewCallback linkPreviewCallback = new LinkPreviewCallback() {
                    @Override
                    public void onPre() {
                        // Any work that needs to be done before generating the preview. Usually inflate
                        // your custom preview layout here.
                    }

                    @Override
                    public void onPos(SourceContent sourceContent, boolean b) {
                        // Populate your preview layout with the results of sourceContent.

                        if(sourceContent.getImages().size()>0) {
                            Log.d(TagUtils.getTag(), "source:-" +sourceContent.getImages().get(0));
                            Glide.with(getActivity().getApplicationContext())
                                    .load(sourceContent.getImages().get(0))
                                    .into(image_post_set);
                        }
                        Log.d(TagUtils.getTag(), "source final:-" +sourceContent.getFinalUrl());
                        Log.d(TagUtils.getTag(), "source title:-" +sourceContent.getTitle());
                        Log.d(TagUtils.getTag(), "source description:-" +sourceContent.getDescription());

                        title.setText(sourceContent.getTitle());
                        url.setText(sourceContent.getFinalUrl());
                        description.setText(sourceContent.getDescription());
                        ll_preview.addView(preview_card);
                        tv_title.setText(sourceContent.getTitle());

                    }
                };
                textCrawler.makePreview( linkPreviewCallback, "https://www.androidhive.info/2018/01/android-app-ui-designing-using-sketch-app-and-zeplin/");

            }else if(i%4==2){
                view = inflater.inflate(R.layout.inflate_sponsored_add, null);
            }else if(i%4==3){
                view = inflater.inflate(R.layout.inflate_news_feeds, null);
            }

            CircleImageView cv_profile_pic=view.findViewById(R.id.cv_profile_pic);
            TextView tv_profile_name=view.findViewById(R.id.tv_profile_name);

            tv_profile_name.setText(userProfilePOJO.getFullname());
            Glide.with(getActivity().getApplicationContext())
                    .load(userProfilePOJO.getProfileImage())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);


            ll_scroll.addView(view);

        }

    }
}
