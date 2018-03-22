package com.ritvi.kaajneeti.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.poll.PollPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreatePollActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.ll_options)
    LinearLayout ll_options;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.btn_create)
    Button btn_create;
    @BindView(R.id.et_question)
    EditText et_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        inflateAns();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateAns();
            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPoll();
            }
        });
    }

    public void createPoll() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePojo.getCitizenProfilePOJO().getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("poll_question", et_question.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("event_description", ""));
        nameValuePairs.add(new BasicNameValuePair("poll_privacy", "1"));
        nameValuePairs.add(new BasicNameValuePair("valid_from_date", ""));
        nameValuePairs.add(new BasicNameValuePair("valid_end_date", ""));

        List<String> pollAns = getAllAns();
        for (int i = 0; i < pollAns.size(); i++) {
            nameValuePairs.add(new BasicNameValuePair("poll_answer[" + i + "]", pollAns.get(i)));
        }

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                ResponsePOJO<PollPOJO> responsePOJO = new Gson().fromJson(response, new TypeToken<ResponsePOJO<PollPOJO>>() {
                }.getType());
                if (responsePOJO.getStatus().equals("success")) {
                    finish();
                }
                ToastClass.showShortToast(getApplicationContext(), responsePOJO.getMessage());
            }
        }, "CREATE_POLL", true).execute(WebServicesUrls.SAVE_MY_POLL);
    }

    public List<String> getAllAns() {
        List<String> pollAnsList = new ArrayList<>();
        try {
            for (int i = 0; i < ll_options.getChildCount(); i++) {
                String text = ((EditText) ((LinearLayout) ((LinearLayout) ll_options.getChildAt(i)).getChildAt(0)).getChildAt(0)).getText().toString();
                Log.d(TagUtils.getTag(), "text:-" + text);
                pollAnsList.add(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pollAnsList;
    }

    public void inflateAns() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.inflate_poll_edits, null);

        ImageView iv_close = view.findViewById(R.id.iv_close);
        EditText et_poll_ans = view.findViewById(R.id.et_poll_ans);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewClose) {
                ll_options.removeView(view);
            }
        });
        ll_options.addView(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
