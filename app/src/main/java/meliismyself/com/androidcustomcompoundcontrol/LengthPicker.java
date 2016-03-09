package meliismyself.com.androidcustomcompoundcontrol;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Meli Oktavia on 3/8/2016.
 */
public class LengthPicker extends LinearLayout {
    private static final String KEY_SUPER_STATE = "superState";
    private static final String KEY_NUM_INCHES = "numInches";
    private View mPlusButton;
    private TextView mTextView;
    private View mMinusButton;

    private int mNumInches = 0;

    // Use for Java
    public LengthPicker(Context context) {
        super(context);
        init();
    }

    // Use for XML
    public LengthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            mNumInches = bundle.getInt(KEY_NUM_INCHES);
            super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE));
        }else {
            super.onRestoreInstanceState(state);
        }
        updateControls();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        bundle.putInt(KEY_NUM_INCHES, mNumInches);
        return bundle;
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.length_picker, this);
        mPlusButton = findViewById(R.id.plus_button);
        mTextView = (TextView) findViewById(R.id.textTv);
        mMinusButton = findViewById(R.id.minus_button);


        updateControls();

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.plus_button:
                        mNumInches++;
                        updateControls();
                        break;
                    case R.id.minus_button:
                        mNumInches--;
                        updateControls();
                        break;
                }

            }
        };

        mPlusButton.setOnClickListener(listener);
        mMinusButton.setOnClickListener(listener);

        setOrientation(LinearLayout.HORIZONTAL);
    }

    private void updateControls() {
        int feet = mNumInches / 12;
        int inches = mNumInches % 12;

        String text = String.format("%d' %d\"", feet, inches);
        if (feet == 0) {
            text = String.format("%d\"", inches);
        } else {
            if (inches == 0) {
                text = String.format("%d\'", feet);
            }
        }
        mTextView.setText(text);
        mMinusButton.setEnabled(mNumInches > 0);
    }

}
