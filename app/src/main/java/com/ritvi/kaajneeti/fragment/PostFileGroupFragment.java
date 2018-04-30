package com.ritvi.kaajneeti.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.pojo.home.PostAttachmentPOJO;
import com.ritvi.kaajneeti.views.VideoPlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 19-04-2018.
 */

@SuppressLint("ValidFragment")
public class PostFileGroupFragment extends Fragment {

    @BindView(R.id.iv_file)
    ImageView iv_file;
    @BindView(R.id.cv_play_video)
    CircleImageView cv_play_video;
    @BindView(R.id.frame_video)
    FrameLayout frame_video;

    PostAttachmentPOJO postAttachmentPOJO;
    boolean open_content;

    public PostFileGroupFragment(PostAttachmentPOJO postAttachmentPOJO, boolean open_content) {
        this.postAttachmentPOJO = postAttachmentPOJO;
        this.open_content = open_content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_post_file_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            String file_path = "";
            if (postAttachmentPOJO.getAttachmentThumb().length() > 0) {
                file_path = postAttachmentPOJO.getAttachmentThumb();
                cv_play_video.setVisibility(View.VISIBLE);
                if (open_content) {
                    frame_video.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                            intent.putExtra("url", postAttachmentPOJO.getAttachmentFile());
                            startActivity(intent);
                        }
                    });
                }
            } else {
                file_path = postAttachmentPOJO.getAttachmentFile();
                cv_play_video.setVisibility(View.GONE);
            }
            Glide.with(getActivity().getApplicationContext())
                    .load(file_path)
                    .placeholder(R.drawable.ic_default_pic)
                    .error(R.drawable.ic_default_pic)
                    .dontAnimate()
                    .into(iv_file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
