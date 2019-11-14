package com.nav.tagger.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.EditText;

import com.nav.tagger.R;


/**
 * Created by Fujitsu on 04-08-2016.
 */
@SuppressLint("AppCompatCustomView")
public class RegularEditText extends EditText {

    public RegularEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
       // this.setHintTextColor(ContextCompat.getColor(context, R.color.edittextColor));
        init();
    }

    public RegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegularEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Lato-Regular.ttf");
        setTypeface(tf);
    }
}
