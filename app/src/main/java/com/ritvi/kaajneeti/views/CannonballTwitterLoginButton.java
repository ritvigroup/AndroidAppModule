package com.ritvi.kaajneeti.views;

import android.content.Context;
import android.util.AttributeSet;

import com.ritvi.kaajneeti.R;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class CannonballTwitterLoginButton extends TwitterLoginButton {
    public CannonballTwitterLoginButton(Context context) {
        super(context);
        init();
    }

    public CannonballTwitterLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CannonballTwitterLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (isInEditMode()){
            return;
        }
        setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable
                .ic_twitter), null, null, null);
        setBackgroundResource(R.drawable.ic_twitter);
        setTextSize(20);
        setPadding(30, 0, 10, 0);
        setTextColor(getResources().getColor(R.color.tw__blue_default));
//        setTypeface(App.getInstance().getTypeface());
    }
}