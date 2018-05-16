package com.ritvi.kaajneeti.fragment.post;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.ViewPagerAdapter;
import com.ritvi.kaajneeti.fragment.PostFileGroupFragment;
import com.ritvi.kaajneeti.pojo.home.PostAttachmentPOJO;
import com.ritvi.kaajneeti.pojo.home.PostPOJO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 20-04-2018.
 */

@SuppressLint("ValidFragment")
public class PostViewFragment extends Fragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_post_description)
    TextView tv_post_description;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_description)
    TextView tv_description;

    PostPOJO postPOJO;

    public PostViewFragment(PostPOJO postPOJO) {
        this.postPOJO = postPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_post_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (postPOJO != null) {
            String profile_description = "";
            boolean containDescribe = false;
            if (postPOJO.getPostTag().size() > 0) {
                containDescribe = true;
            }

            if (postPOJO.getFeelingDataPOJOS().size() > 0) {
                containDescribe = true;
            }

            if (postPOJO.getPostLocation().length() > 0) {
                containDescribe = true;
            }

            if (containDescribe) {
                profile_description += " is ";

                if (postPOJO.getFeelingDataPOJOS().size() > 0) {
                    profile_description += "<b>feeling " + postPOJO.getFeelingDataPOJOS().get(0).getFeelingName() + "</b>";
                }

                if (postPOJO.getPostTag().size() > 0) {
                    profile_description += " with ";
                    if (postPOJO.getPostTag().size() > 2) {
                        profile_description += "<b>" + postPOJO.getPostTag().get(0).getFirstName() + " and " + (postPOJO.getPostTag().size() - 1) + " other" + "</b>";
                    } else if (postPOJO.getPostTag().size() == 2) {
                        profile_description += "<b>" + postPOJO.getPostTag().get(0).getFirstName() + " " + postPOJO.getPostTag().get(0).getLastName() +
                                " and " + postPOJO.getPostTag().get(1).getFirstName() + " " + postPOJO.getPostTag().get(1).getLastName() + "</b>";
                    } else {
                        profile_description += "<b>" + postPOJO.getPostTag().get(0).getFirstName() + " " + postPOJO.getPostTag().get(0).getLastName() + "</b>";
                    }
                }

                if (postPOJO.getPostLocation().length() > 0) {
                    profile_description += " at <b>" + postPOJO.getPostLocation() + "</b>";
                }
            }
            if (profile_description.length() > 0) {
                profile_description = " - " + profile_description;
            }

            if (postPOJO.getPostDescription().length() > 0) {
                tv_description.setText(postPOJO.getPostDescription());
            } else {
                tv_description.setVisibility(View.GONE);
            }

            if (profile_description.length() > 0) {
                tv_post_description.setText(Html.fromHtml(profile_description));
            } else {
                tv_post_description.setVisibility(View.GONE);
            }


            if (postPOJO.getPostAttachment().size() > 0) {


                ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

                for (PostAttachmentPOJO postAttachmentPOJO : postPOJO.getPostAttachment()) {
                    PostFileGroupFragment postFileGroupFragment = new PostFileGroupFragment(postAttachmentPOJO, true);
                    adapter.addFrag(postFileGroupFragment, "");
                }
                viewPager.setAdapter(adapter);
                viewPager.setVisibility(View.VISIBLE);
            }
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


    }
}
