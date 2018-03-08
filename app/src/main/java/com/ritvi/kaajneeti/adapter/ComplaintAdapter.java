package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Pref;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.pojo.complaint.ComplaintPOJO;
import com.ritvi.kaajneeti.pojo.complaint.VoterComplaintPOJO;
import com.ritvi.kaajneeti.webservice.AdapterWebService;
import com.ritvi.kaajneeti.webservice.MsgPassInterface;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {
    private List<VoterComplaintPOJO> items;
    Activity activity;
    Fragment fragment;

    public ComplaintAdapter(Activity activity, Fragment fragment, List<VoterComplaintPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_complaints, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_date.setText(items.get(position).getC_added_on_dt());
        holder.tv_description.setText(items.get(position).getC_description());
        holder.tv_subject.setText(items.get(position).getC_subject());
        holder.tv_complaint_id.setText(items.get(position).getComplaint_id());

        holder.ll_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callComplaintAPI(items.get(position));
            }
        });


        holder.itemView.setTag(items.get(position));
    }

    public void callComplaintAPI(VoterComplaintPOJO complaintPOJO){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","COMPLAINT_VIEW"));
        nameValuePairs.add(new BasicNameValuePair("c_profile_id", Pref.GetUserProfile(activity.getApplicationContext()).getCitizenId()));
        nameValuePairs.add(new BasicNameValuePair("complaint_id",complaintPOJO.getComplaint_id()));

        new AdapterWebService(activity, nameValuePairs, true, new MsgPassInterface() {
            @Override
            public void onMsgPassed(String response) {
                Log.d(TagUtils.getTag(),"api called:-"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equals("success")){
                        Gson gson=new Gson();
                        ComplaintPOJO complaintPOJO1=gson.fromJson(jsonObject.optJSONObject("complaints").toString(),ComplaintPOJO.class);
                        showComplaintDialog(complaintPOJO1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).executeApi(WebServicesUrls.USER_ADMIN_PROCESS);
    }

    public void showComplaintDialog(ComplaintPOJO complaintPOJO){
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_complaint);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setTitle("");
        dialog.show();
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_complaint_title=dialog.findViewById(R.id.tv_complaint_title);
        TextView tv_complaint_id=dialog.findViewById(R.id.tv_complaint_id);
        TextView tv_name=dialog.findViewById(R.id.tv_name);
        TextView tv_father_name=dialog.findViewById(R.id.tv_father_name);
        TextView tv_mobile=dialog.findViewById(R.id.tv_mobile);
        TextView tv_email=dialog.findViewById(R.id.tv_email);
        TextView tv_aadhar_number=dialog.findViewById(R.id.tv_aadhar_number);
        TextView tv_district=dialog.findViewById(R.id.tv_district);
        TextView tv_subject=dialog.findViewById(R.id.tv_subject);
        TextView tv_description=dialog.findViewById(R.id.tv_description);
        TextView tv_date_time=dialog.findViewById(R.id.tv_date_time);
        ImageView iv_close=dialog.findViewById(R.id.iv_close);

        tv_complaint_title.setText(complaintPOJO.getComplaintId());
        tv_complaint_id.setText(complaintPOJO.getComplaintId());
        tv_name.setText(complaintPOJO.getcName());
        tv_father_name.setText(complaintPOJO.getcFatherName());
        tv_mobile.setText(complaintPOJO.getcMobile());
        tv_email.setText(complaintPOJO.getcEmail());
        tv_aadhar_number.setText(complaintPOJO.getcAadhaarNumber());
        tv_district.setText(complaintPOJO.getcDistrict());
        tv_subject.setText(complaintPOJO.getcSubject());
        tv_description.setText(complaintPOJO.getcDescription());
        tv_date_time.setText(complaintPOJO.getcAddedOnDt());

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_subject;
        public TextView tv_date;
        public TextView tv_description;
        public TextView tv_complaint_id;
        public LinearLayout ll_complaint;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_subject = (TextView) itemView.findViewById(R.id.tv_subject);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_complaint_id = (TextView) itemView.findViewById(R.id.tv_complaint_id);
            ll_complaint = (LinearLayout) itemView.findViewById(R.id.ll_complaint);
        }
    }
}
