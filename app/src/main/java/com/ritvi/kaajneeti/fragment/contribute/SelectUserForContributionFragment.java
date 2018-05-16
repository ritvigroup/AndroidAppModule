package com.ritvi.kaajneeti.fragment.contribute;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.zxing.Result;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.CustomAutoCompleteAdapter;
import com.ritvi.kaajneeti.adapter.CustomContributeUserAdapter;
import com.ritvi.kaajneeti.fragment.search.AllSearchFragment;
import com.ritvi.kaajneeti.pojo.AllSearchPOJO;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;
import com.ritvi.kaajneeti.pojo.profile.FullProfilePOJO;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SelectUserForContributionFragment extends Fragment implements ZXingScannerView.ResultHandler{

    @BindView(R.id.act_user)
    AutoCompleteTextView act_user;
    @BindView(R.id.btn_proceed)
    Button btn_proceed;
    @BindView(R.id.zxing_barcode_scanner)
    ZXingScannerView zxing_barcode_scanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_select_user_for_contribution,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        act_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchUser(act_user.getText().toString());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        zxing_barcode_scanner.setResultHandler(this); // Register ourselves as a handler for scan results.
        zxing_barcode_scanner.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        zxing_barcode_scanner.stopCamera();
    }

    AllSearchPOJO allSearchPOJO;
    public void searchUser(String key){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("q",key));
        nameValuePairs.add(new BasicNameValuePair("start","0"));
        nameValuePairs.add(new BasicNameValuePair("end","10"));
        nameValuePairs.add(new BasicNameValuePair("search_in","people"));

        new WebServiceBaseResponse<AllSearchPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<AllSearchPOJO>() {

            @Override
            public void onGetMsg(ResponsePOJO<AllSearchPOJO> responsePOJO) {
                if (responsePOJO.isSuccess()) {
                    SelectUserForContributionFragment.this.allSearchPOJO = responsePOJO.getResult();
                    populateUser(responsePOJO.getResult().getProfileFeeds());
                }
            }
        }, AllSearchPOJO.class, "ALL_SEARCH_API", false).execute(WebServicesUrls.ALL_SEARCH_API);
    }
    CustomContributeUserAdapter adapter = null;
    public void populateUser(List<FeedPOJO> feedPOJOS){
        final List<UserProfilePOJO> userProfilePOJOS=new ArrayList<>();
        for(FeedPOJO feedPOJO:feedPOJOS) {
             userProfilePOJOS.add(feedPOJO.getProfiledata());
        }
        adapter = new CustomContributeUserAdapter(getActivity(), (ArrayList<UserProfilePOJO>) userProfilePOJOS);
        act_user.setAdapter(adapter);
        act_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.replaceFragmentinFrameHome(new ContributeAmountFragment(userProfilePOJOS.get(i)),"ContributeAmountFragment");
                }
            }
        });
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v(TagUtils.getTag(), rawResult.getText()); // Prints scan results

        try{
            String profile_id=rawResult.getText().split(",")[1];
            getAllProfileData(profile_id);
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.v(TagUtils.getTag(), rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        zxing_barcode_scanner.resumeCameraPreview(this);
    }

    public void getAllProfileData(String profile_id) {
        Log.d(TagUtils.getTag(), "profile refresh called");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", profile_id));
        new WebServiceBaseResponse<FullProfilePOJO>(nameValuePairs, getActivity(), new ResponseCallBack<FullProfilePOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<FullProfilePOJO> responsePOJO) {
                if (responsePOJO.isSuccess()) {
                    if(getActivity() instanceof HomeActivity){
                        HomeActivity homeActivity= (HomeActivity) getActivity();
                        homeActivity.replaceFragmentinFrameHome(new ContributeAmountFragment(responsePOJO.getResult().getProfilePOJO()),"ContributeAmountFragment");
                    }
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                }
            }
        }, FullProfilePOJO.class, "CALL_PROFILE_API", true).execute(WebServicesUrls.FULL_PROFILE_URL);
    }
}
