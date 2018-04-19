package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.ComplaintDetailActivity;
import com.ritvi.kaajneeti.activity.ViewComplaintActivity;
import com.ritvi.kaajneeti.fragment.analyze.ComplaintListFragment;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class ComplaintAnalyzeAdapter extends RecyclerView.Adapter<ComplaintAnalyzeAdapter.ViewHolder> {
    private List<ComplaintPOJO> items;
    Activity activity;
    Fragment fragment;
    boolean is_group;

    public ComplaintAnalyzeAdapter(Activity activity, Fragment fragment, List<ComplaintPOJO> items,boolean is_group) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
        this.is_group=is_group;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_complaint_analyze_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_analyze.setText(items.get(position).getComplaintSubject());
        holder.tv_id.setText("CID:-"+items.get(position).getComplaintUniqueId());

        if(is_group){
            if(!UtilityFunction.getUserProfilePOJO(items.get(position).getComplaintProfile().getUserProfileDetailPOJO().getUserInfoPOJO()).getUserProfileId().equals(UtilityFunction.getUserProfilePOJO(Constants.userInfoPOJO).getUserProfileId())){
                boolean is_accepted=false;
                boolean is_declined=false;
                for(OutGoingRequestPOJO complaintMemberPOJO:items.get(position).getComplaintMemberPOJOS()){
                    if(UtilityFunction.getUserProfilePOJO(complaintMemberPOJO.getUserProfileDetailPOJO().getUserInfoPOJO()).getUserProfileId().equals(UtilityFunction.getProfileID(Constants.userInfoPOJO))){
                        if(complaintMemberPOJO.getAcceptedYesNo().equals("1")){
                            is_accepted=true;
                        }else if(complaintMemberPOJO.getAcceptedYesNo().equals("-1")){
                            is_declined=true;
                        }
                    }
                }
                if(is_accepted){
                    holder.ll_acceptdecline.setVisibility(View.GONE);
                    holder.tv_accepted.setText("You accepted the complaint request");
                }else if(is_declined){
                    holder.ll_acceptdecline.setVisibility(View.GONE);
                    holder.tv_accepted.setText("You declined the complaint request");
                }else{
                    holder.ll_accepted.setVisibility(View.GONE);
                }
            }else{
                holder.ll_acceptdecline.setVisibility(View.GONE);
            }
        }else{
            holder.ll_acceptdecline.setVisibility(View.GONE);
        }

        holder.ll_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAcceptDeclintDialog("Do you want to decline the Complaint Request",0,items.get(position).getComplaintId());
            }
        });

        holder.ll_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAcceptDeclintDialog("Do you want to accept the Complaint Request",1,items.get(position).getComplaintId());
            }
        });

        holder.ll_analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ViewComplaintActivity.class);
                intent.putExtra("complaint",items.get(position));
                activity.startActivity(intent);
            }
        });

        holder.iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ComplaintDetailActivity.class);
                intent.putExtra("complaintPOJO",items.get(position));
                activity.startActivity(intent);
            }
        });
        holder.itemView.setTag(items.get(position));

    }

    public void showAcceptDeclintDialog(String message, final int type, final String complaint_id){
        //0 for decline and 1 for accept

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(type==1){
                    acceptComplaintRequest(complaint_id);
                }else{
                    cancelComplaintRequest(complaint_id);
                }
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void cancelComplaintRequest(String complaint_id){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",UtilityFunction.getProfileID(Constants.userInfoPOJO)));
        nameValuePairs.add(new BasicNameValuePair("complaint_id",complaint_id));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equals("success")){
                        if(fragment instanceof ComplaintListFragment){
                            ComplaintListFragment complaintListFragment= (ComplaintListFragment) fragment;
                            complaintListFragment.refresh();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_COMPLAINT_INVITATION",true).execute(WebServicesUrls.DELETE_COMPLAINT_INVITATION);
    }

    public void acceptComplaintRequest(String complaint_id){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",UtilityFunction.getProfileID(Constants.userInfoPOJO)));
        nameValuePairs.add(new BasicNameValuePair("complaint_id",complaint_id));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equals("success")){
                        if(fragment instanceof ComplaintListFragment){
                            ComplaintListFragment complaintListFragment= (ComplaintListFragment) fragment;
                            complaintListFragment.refresh();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_COMPLAINT_INVITATION",true).execute(WebServicesUrls.UPDATE_COMPLAINT_INVITATION);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_analyze,tv_id,tv_accepted;
        public LinearLayout ll_analyze;
        public LinearLayout ll_decline;
        public LinearLayout ll_accept;
        public LinearLayout ll_accepted;
        public LinearLayout ll_acceptdecline;
        public ImageView iv_info;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_analyze=itemView.findViewById(R.id.tv_analyze);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_accepted=itemView.findViewById(R.id.tv_accepted);
            ll_analyze=itemView.findViewById(R.id.ll_analyze);
            ll_accept=itemView.findViewById(R.id.ll_accept);
            ll_decline=itemView.findViewById(R.id.ll_decline);
            ll_accepted=itemView.findViewById(R.id.ll_accepted);
            ll_acceptdecline=itemView.findViewById(R.id.ll_acceptdecline);
            iv_info=itemView.findViewById(R.id.iv_info);
        }
    }
}
