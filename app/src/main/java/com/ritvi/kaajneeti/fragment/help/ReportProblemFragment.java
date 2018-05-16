package com.ritvi.kaajneeti.fragment.help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.erikagtierrez.multiple_media_picker.Gallery;
import com.ritvi.kaajneeti.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportProblemFragment extends Fragment {

    private static final int OPEN_MEDIA_PICKER = 1;

    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.iv_attach1)
    ImageView iv_attach1;
    @BindView(R.id.iv_attach2)
    ImageView iv_attach2;
    @BindView(R.id.iv_attach3)
    ImageView iv_attach3;

    private final String attach1 = "attach1";
    private final String attach2 = "attach2";
    private final String attach3 = "attach3";

    private String attach_type = attach1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_report_problem, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        iv_attach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attach_type = attach1;
                selectMedia();
            }
        });
        iv_attach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attach_type = attach2;
                selectMedia();
            }
        });
        iv_attach3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attach_type = attach3;
                selectMedia();
            }
        });
    }


    public void selectMedia() {
        Intent intent = new Intent(getActivity(), Gallery.class);
        intent.putExtra("title", "Select media");
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode", 2);
        intent.putExtra("maxSelection", 1); // Optional
        startActivityForResult(intent, OPEN_MEDIA_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> selectionResult = data.getStringArrayListExtra("result");
                String path = selectionResult.get(0);
                setImage(path);
            }
        }
    }

    public void setImage(String path) {
        ImageView imageView;
        if (attach_type.equalsIgnoreCase(attach1)) {
            imageView = iv_attach1;
        } else if (attach_type.equalsIgnoreCase(attach2)) {
            imageView = iv_attach2;
        } else if (attach_type.equalsIgnoreCase(attach3)) {
            imageView = iv_attach3;
        } else {
            imageView = null;
        }

        if (imageView != null) {
            Glide.with(getActivity().getApplicationContext())
                    .load(path)
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(imageView);
        }
    }
}
