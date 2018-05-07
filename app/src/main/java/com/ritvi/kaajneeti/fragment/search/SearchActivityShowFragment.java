package com.ritvi.kaajneeti.fragment.search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.FilterAdapter;
import com.ritvi.kaajneeti.adapter.HomeFeedAdapter;
import com.ritvi.kaajneeti.adapter.SentRequestAdapter;
import com.ritvi.kaajneeti.pojo.AllSearchPOJO;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;
import com.ritvi.kaajneeti.pojo.search.SearchFilterPOJO;
import com.ritvi.kaajneeti.pojo.search.SearchLocationPOJO;
import com.ritvi.kaajneeti.pojo.search.SearchQualificationPOJO;
import com.ritvi.kaajneeti.pojo.search.SearchWorkPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.pojo.userdetail.WorkPOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class SearchActivityShowFragment extends Fragment {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.rv_search)
    RecyclerView rv_search;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.iv_filters)
    ImageView iv_filters;
    @BindView(R.id.btn_results)
    Button btn_results;
    @BindView(R.id.check_friends_of_friends)
    CheckBox check_friends_of_friends;
    @BindView(R.id.tv_selected_city)
    TextView tv_selected_city;
    @BindView(R.id.iv_close_city)
    ImageView iv_close_city;
    @BindView(R.id.tv_selected_education)
    TextView tv_selected_education;
    @BindView(R.id.iv_close_education)
    ImageView iv_close_education;
    @BindView(R.id.tv_selected_company)
    TextView tv_selected_company;
    @BindView(R.id.iv_close_work)
    ImageView iv_close_work;
    @BindView(R.id.ll_city)
    LinearLayout ll_city;
    @BindView(R.id.ll_education)
    LinearLayout ll_education;
    @BindView(R.id.ll_work)
    LinearLayout ll_work;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;
    @BindView(R.id.tv_reset)
    TextView tv_reset;

    List<FeedPOJO> feedPOJOS = new ArrayList<>();
    String search_in;
    String search_text;

    public SearchActivityShowFragment(String search_in, String search_text, List<FeedPOJO> feedPOJOS) {
        this.search_in = search_in;
        this.search_text = search_text;
        if (feedPOJOS != null) {
            this.feedPOJOS.addAll(feedPOJOS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_all_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setRVdata(List<FeedPOJO> feedPOJOS) {
        this.feedPOJOS.clear();
        this.feedPOJOS.addAll(feedPOJOS);
        this.sentRequestAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_search.setText(search_text);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                callSearchAPI(et_search.getText().toString());
            }
        });
        attachAdapter();
        notifydatasetChanged(feedPOJOS);
        iv_filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingUpPanelLayout != null &&
                        (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                } else {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }
            }
        });

        ll_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCityDialog();
            }
        });

        iv_close_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tv_selected_city.getText().toString().equalsIgnoreCase("anywhere")) {
                    selectedLocationFilter = "";
                    tv_selected_city.setText("AnyWhere");
                    iv_close_city.setVisibility(View.GONE);
                }
            }
        });
        ll_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEducationDialog();
            }
        });

        iv_close_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tv_selected_education.getText().toString().equalsIgnoreCase("Any school")) {
                    selectedQualificationFilter = "";
                    tv_selected_education.setText("Any school");
                    iv_close_education.setVisibility(View.GONE);
                }
            }
        });

        ll_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWorkDialog();
            }
        });

        iv_close_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tv_selected_company.getText().toString().equalsIgnoreCase("Any company")) {
                    selectedWorkFilter = "";
                    tv_selected_company.setText("Any company");
                    iv_close_work.setVisibility(View.GONE);
                }
            }
        });
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_close_work.callOnClick();
                iv_close_education.callOnClick();
                iv_close_city.callOnClick();
            }
        });

        btn_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        callFiltersAPI();
    }

    SearchFilterPOJO searchFilterPOJO;

    public void notifydatasetChanged(List<FeedPOJO> feedPOJOS) {
        userProfilePOJOS.clear();
        for (FeedPOJO feedPOJO : feedPOJOS) {
            userProfilePOJOS.add(feedPOJO.getProfiledata());
        }
        sentRequestAdapter.notifyDataSetChanged();
    }

    public void callFiltersAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        new WebServiceBaseResponse<SearchFilterPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<SearchFilterPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<SearchFilterPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        searchFilterPOJO = responsePOJO.getResult();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SearchFilterPOJO.class, "SEARCH_FILTER_POJO", false).execute(WebServicesUrls.SEARCH_FILTERS);
    }


    Dialog workDialog;

    public void showWorkDialog() {
        workDialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        workDialog.setCancelable(true);
        workDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        workDialog.setContentView(R.layout.dialog_show_work);
        workDialog.show();
        workDialog.setCancelable(true);
        Window window = workDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        RecyclerView rv_education = workDialog.findViewById(R.id.rv_education);
        ImageView iv_search_close = workDialog.findViewById(R.id.iv_search_close);
        FrameLayout frame_search = workDialog.findViewById(R.id.frame_search);

        iv_search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workDialog.dismiss();
            }
        });

        if (searchFilterPOJO != null && searchFilterPOJO.getSearchQualificationPOJOS() != null) {
            List<String> workStringList = new ArrayList<>();
            for (WorkPOJO searchWorkPOJO : searchFilterPOJO.getWorkPOJOS()) {
                workStringList.add(searchWorkPOJO.getWorkPosition() + " , " + searchWorkPOJO.getWorkLocation());
            }

            FilterAdapter filterAdapter = new FilterAdapter(getActivity(), SearchActivityShowFragment.this, workStringList, Constants.SEARCH_WORK, selectedWorkFilter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv_education.setHasFixedSize(true);
            rv_education.setAdapter(filterAdapter);
            rv_education.setLayoutManager(linearLayoutManager);
            rv_education.setItemAnimator(new DefaultItemAnimator());
        }

    }


    Dialog educationDialog;

    public void showEducationDialog() {
        educationDialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        educationDialog.setCancelable(true);
        educationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        educationDialog.setContentView(R.layout.dialog_show_education);
        educationDialog.show();
        educationDialog.setCancelable(true);
        Window window = educationDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        RecyclerView rv_education = educationDialog.findViewById(R.id.rv_education);
        ImageView iv_search_close = educationDialog.findViewById(R.id.iv_search_close);
        FrameLayout frame_search = educationDialog.findViewById(R.id.frame_search);

        iv_search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationDialog.dismiss();
            }
        });

        if (searchFilterPOJO != null && searchFilterPOJO.getSearchQualificationPOJOS() != null) {
            List<String> educationStringList = new ArrayList<>();
            for (SearchQualificationPOJO searchQualificationPOJO : searchFilterPOJO.getSearchQualificationPOJOS()) {
                educationStringList.add(searchQualificationPOJO.getQualification());
            }

            FilterAdapter filterAdapter = new FilterAdapter(getActivity(), SearchActivityShowFragment.this, educationStringList, Constants.SEARCH_QUALIFICATION, selectedQualificationFilter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv_education.setHasFixedSize(true);
            rv_education.setAdapter(filterAdapter);
            rv_education.setLayoutManager(linearLayoutManager);
            rv_education.setItemAnimator(new DefaultItemAnimator());
        }

    }


    String selectedLocationFilter = "";
    String selectedWorkFilter = "";
    String selectedQualificationFilter = "";

    public void selectCity(String selected, String type) {
        if (type.equalsIgnoreCase(Constants.SEARCH_LOCATION)) {
            this.selectedLocationFilter = selected;
            if (cityDialog != null && cityDialog.isShowing()) {
                cityDialog.dismiss();
            }
            tv_selected_city.setText(this.selectedLocationFilter);
            iv_close_city.setVisibility(View.VISIBLE);
        } else if (type.equalsIgnoreCase(Constants.SEARCH_QUALIFICATION)) {
            this.selectedQualificationFilter = selected;
            if (educationDialog != null && educationDialog.isShowing()) {
                educationDialog.dismiss();
            }
            tv_selected_education.setText(this.selectedQualificationFilter);
            iv_close_education.setVisibility(View.VISIBLE);
        } else if (type.equalsIgnoreCase(Constants.SEARCH_WORK)) {
            this.selectedWorkFilter = selected;
            if (workDialog != null && workDialog.isShowing()) {
                workDialog.dismiss();
            }
            tv_selected_company.setText(this.selectedWorkFilter);
            iv_close_work.setVisibility(View.VISIBLE);
        }


    }

    Dialog cityDialog;

    public void showCityDialog() {
        cityDialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        cityDialog.setCancelable(true);
        cityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cityDialog.setContentView(R.layout.dialog_show_city_filter);
        cityDialog.show();
        cityDialog.setCancelable(true);
        Window window = cityDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        FrameLayout frame_search = cityDialog.findViewById(R.id.frame_search);
        RecyclerView rv_cities = cityDialog.findViewById(R.id.rv_cities);
        ImageView iv_search_close = cityDialog.findViewById(R.id.iv_search_close);

        frame_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TagUtils.getTag(), "frame clickable");
                findPlace();
            }
        });

        iv_search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityDialog.dismiss();
            }
        });

        if (searchFilterPOJO != null) {
            List<String> searchLocations = new ArrayList<>();
            for (SearchLocationPOJO searchLocationPOJO : searchFilterPOJO.getSearchLocationPOJOS()) {
                searchLocations.add(searchLocationPOJO.getCity() + " , " + searchLocationPOJO.getCountry());
            }
            FilterAdapter filterAdapter = new FilterAdapter(getActivity(), SearchActivityShowFragment.this, searchLocations, Constants.SEARCH_LOCATION, selectedLocationFilter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv_cities.setHasFixedSize(true);
            rv_cities.setAdapter(filterAdapter);
            rv_cities.setLayoutManager(linearLayoutManager);
            rv_cities.setItemAnimator(new DefaultItemAnimator());
        }
    }

    public void findPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }


    AllSearchPOJO allSearchPOJO;

    public void callSearchAPI(String key) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("q", key));
        nameValuePairs.add(new BasicNameValuePair("start", "0"));
        nameValuePairs.add(new BasicNameValuePair("end", "10"));
        nameValuePairs.add(new BasicNameValuePair("search_in", search_in));
        new WebServiceBaseResponse<AllSearchPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<AllSearchPOJO>() {

            @Override
            public void onGetMsg(ResponsePOJO<AllSearchPOJO> responsePOJO) {
                feedPOJOS.clear();
                if (responsePOJO.isSuccess()) {
//                    switch (search_in) {
//                        case Constants.SEARCH_IN_COMPLAINT:
//                            feedPOJOS.addAll(responsePOJO.getResult().getComplaintFeeds());
//                            break;
//                        case Constants.SEARCH_IN_EVENT:
//                            feedPOJOS.addAll(responsePOJO.getResult().getEventFeeds());
//                            break;
//                        case Constants.SEARCH_IN_PEOPLE:
                    feedPOJOS.addAll(responsePOJO.getResult().getProfileFeeds());
//                            break;
//                        case Constants.SEARCH_IN_POLL:
//                            feedPOJOS.addAll(responsePOJO.getResult().getPollFeeds());
//                            break;
//                        case Constants.SEARCH_IN_POST:
//                            feedPOJOS.addAll(responsePOJO.getResult().getPostFeeds());
//                            break;
//                        case Constants.SEARCH_IN_SUGGESTION:
//                            feedPOJOS.addAll(responsePOJO.getResult().getSuggestionFeeds());
//                            break;
//                    }
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                }
                notifydatasetChanged(feedPOJOS);
//                sentRequestAdapter.notifyDataSetChanged();
            }
        }, AllSearchPOJO.class, "ALL_SEARCH_API", false).execute(WebServicesUrls.ALL_SEARCH_API);
    }

    SentRequestAdapter sentRequestAdapter;
    List<UserProfilePOJO> userProfilePOJOS = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_search.setLayoutManager(linearLayoutManager);
        sentRequestAdapter = new SentRequestAdapter(getActivity(), this, userProfilePOJOS);
        rv_search.setHasFixedSize(true);
        rv_search.setAdapter(sentRequestAdapter);
        rv_search.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i(TagUtils.getTag(), "Place: " + place.getName());
                selectCity((String) place.getName(), Constants.SEARCH_LOCATION);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TagUtils.getTag(), status.getStatusMessage());

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
