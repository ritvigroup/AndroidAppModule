package com.ritvi.kaajneeti.fragment.post;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.fragment.promotion.EngagementFragment;
import com.ritvi.kaajneeti.pojo.home.EventPOJO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 20-04-2018.
 */

@SuppressLint("ValidFragment")
public class EventViewFragment extends Fragment {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_event_image)
    ImageView iv_event_image;
    @BindView(R.id.tv_tool_event_name)
    TextView tv_tool_event_name;
    @BindView(R.id.tv_event_name)
    TextView tv_event_name;
    @BindView(R.id.ll_edit)
    LinearLayout ll_edit;
    @BindView(R.id.ll_share)
    LinearLayout ll_share;
    @BindView(R.id.ll_more)
    LinearLayout ll_more;
    @BindView(R.id.tv_date_time)
    TextView tv_date_time;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_promote)
    TextView tv_promote;


    EventPOJO eventPOJO;

    public EventViewFragment(EventPOJO eventPOJO) {
        this.eventPOJO = eventPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_view_event, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        if(eventPOJO.getEventAttachment().size()>0){
            Glide.with(getActivity().getApplicationContext())
                    .load(eventPOJO.getEventAttachment().get(0).getAttachmentFile())
                    .placeholder(R.drawable.ic_default_pic)
                    .error(R.drawable.ic_default_pic)
                    .dontAnimate()
                    .into(iv_event_image);
        }

        if (eventPOJO != null) {
            tv_tool_event_name.setText(eventPOJO.getEventName());
            tv_event_name.setText(eventPOJO.getEventName());
            tv_date_time.setText(eventPOJO.getStartDate());
            tv_location.setText(eventPOJO.getEventLocation());
        }

        tv_promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    EngagementFragment engagementFragment = new EngagementFragment();
                    homeActivity.replaceFragmentinFrameHome(engagementFragment,"engagementFragment");
                }
            }
        });

    }
}
