package com.ritvi.kaajneeti.fragment.promotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.erikagtierrez.multiple_media_picker.Gallery;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.MediaListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleAttachmentFragment extends Fragment{

    static final int OPEN_MEDIA_PICKER = 1;

    @BindView(R.id.tv_attach)
    TextView tv_attach;
    @BindView(R.id.rv_attachments)
    RecyclerView rv_attachments;
    @BindView(R.id.btn_create)
    Button btn_create;

    List<String> mediaFiles=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_title_attach,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachMediaAdapter();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tv_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMedia();
            }
        });
    }

    public void selectMedia() {
        Intent intent = new Intent(getActivity(), Gallery.class);
        intent.putExtra("title", "Select media");
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode", 1);
        intent.putExtra("maxSelection", 5 - mediaFiles.size()); // Optional
        startActivityForResult(intent, OPEN_MEDIA_PICKER);
    }


    MediaListAdapter mediaListAdapter;

    public void attachMediaAdapter() {
        mediaListAdapter = new MediaListAdapter(getActivity(), this, mediaFiles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_attachments.setHasFixedSize(true);
        rv_attachments.setAdapter(mediaListAdapter);
        rv_attachments.setLayoutManager(linearLayoutManager);
        rv_attachments.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> selectionResult = data.getStringArrayListExtra("result");
                mediaFiles.addAll(selectionResult);
                mediaListAdapter.notifyDataSetChanged();
            }
        }
    }
}
