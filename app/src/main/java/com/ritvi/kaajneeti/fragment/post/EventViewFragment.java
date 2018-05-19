package com.ritvi.kaajneeti.fragment.post;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.google.gson.Gson;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.HomeFeedAdapter;
import com.ritvi.kaajneeti.fragment.promotion.EngagementFragment;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.home.EventPOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

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
    @BindView(R.id.tv_day)
    TextView tv_day;
    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_may_be)
    TextView tv_may_be;
    @BindView(R.id.tv_not_going)
    TextView tv_not_going;
    @BindView(R.id.tv_going)
    TextView tv_going;
    @BindView(R.id.ll_going)
    LinearLayout ll_going;
    @BindView(R.id.ll_not_going)
    LinearLayout ll_not_going;
    @BindView(R.id.ll_may_be)
    LinearLayout ll_may_be;
    @BindView(R.id.tv_total_going)
    TextView tv_total_going;
    @BindView(R.id.tv_total_not_going)
    TextView tv_total_not_going;
    @BindView(R.id.tv_total_may_be)
    TextView tv_total_may_be;


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


        callEventAPI();
    }

    public void callEventAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("event_id",eventPOJO.getEventId()));

        new WebServiceBaseResponse<EventPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<EventPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<EventPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        EventViewFragment.this.eventPOJO=responsePOJO.getResult();
                    } else {

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                loadView();
            }
        },EventPOJO.class,"GET_EVENT_DETAILS",true).execute(WebServicesUrls.EVENT_DETAIL);
    }

    public void loadView(){
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

            try {
                String startDate = UtilityFunction.getServerConvertedFullDate(eventPOJO.getStartDate().split(" ")[0]);
                String endDate = UtilityFunction.getServerConvertedFullDate(eventPOJO.getEndDate().split(" ")[0]);

                tv_date_time.setText(startDate + "  to " + endDate);

                String[] dateValues = UtilityFunction.getDateValues(eventPOJO.getStartDate().split(" ")[0]);
                tv_month.setText(dateValues[1]);
                tv_day.setText(dateValues[0]);

            } catch (Exception e) {
                e.printStackTrace();
                tv_date_time.setText(eventPOJO.getStartDate() + "  to " + eventPOJO.getEndDate());
            }
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
        ll_going.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callEventInterestAPI(eventPOJO, "1");
            }
        });

        ll_not_going.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callEventInterestAPI(eventPOJO, "2");
            }
        });

        ll_may_be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callEventInterestAPI(eventPOJO, "3");
            }
        });

        switch (eventPOJO.getMeInterested()) {
            case 1:
                tv_going.setTextColor(Color.parseColor("#00bcd4"));
                tv_not_going.setTextColor(Color.parseColor("#BDBDBD"));
                tv_may_be.setTextColor(Color.parseColor("#BDBDBD"));
                break;
            case 2:
                tv_going.setTextColor(Color.parseColor("#BDBDBD"));
                tv_not_going.setTextColor(Color.parseColor("#00bcd4"));
                tv_may_be.setTextColor(Color.parseColor("#BDBDBD"));
                break;
            case 3:
                tv_going.setTextColor(Color.parseColor("#BDBDBD"));
                tv_not_going.setTextColor(Color.parseColor("#BDBDBD"));
                tv_may_be.setTextColor(Color.parseColor("#00bcd4"));
                break;
        }

        try{
            tv_total_going.setText(eventPOJO.getTotalEventInterestList().get(0).getTotalCount());
            tv_total_not_going.setText(eventPOJO.getTotalEventInterestList().get(2).getTotalCount());
            tv_total_may_be.setText(eventPOJO.getTotalEventInterestList().get(1).getTotalCount());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callEventInterestAPI(final EventPOJO eventPOJO, final String interest_type) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("event_id", eventPOJO.getEventId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("interest_type", interest_type));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("success")) {
                        switch (interest_type) {
                            case "1":
                                tv_going.setTextColor(Color.parseColor("#00bcd4"));
                                tv_not_going.setTextColor(Color.parseColor("#BDBDBD"));
                                tv_may_be.setTextColor(Color.parseColor("#BDBDBD"));
                                break;
                            case "2":
                                tv_going.setTextColor(Color.parseColor("#BDBDBD"));
                                tv_not_going.setTextColor(Color.parseColor("#00bcd4"));
                                tv_may_be.setTextColor(Color.parseColor("#BDBDBD"));
                                break;
                            case "3":
                                tv_going.setTextColor(Color.parseColor("#BDBDBD"));
                                tv_not_going.setTextColor(Color.parseColor("#BDBDBD"));
                                tv_may_be.setTextColor(Color.parseColor("#00bcd4"));
                                break;
                        }
                        EventPOJO eventPOJO1=new Gson().fromJson(jsonObject.optJSONObject("result").toString(),EventPOJO.class);
                        EventViewFragment.this.eventPOJO=eventPOJO1;
                        loadView();
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_EVENT_INTEREST_UPDATE", true).execute(WebServicesUrls.EVENT_INTEREST_UPDATE);
    }}
