package com.ritvi.kaajneeti.fragment.homeactivity.kaaj;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 15-03-2018.
 */

public class ImageViewPagerFragment extends Fragment{

    @BindView(R.id.iv_image)
    ImageView iv_image;

    String image_url;

    public ImageViewPagerFragment(String image_url){
        this.image_url=image_url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_image_view_pager,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TagUtils.getTag(),"loading url:-"+image_url);
        Glide.with(getActivity())
                .load(image_url)
                .into(iv_image);
    }
}
