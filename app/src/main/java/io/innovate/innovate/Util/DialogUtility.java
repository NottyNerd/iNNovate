package io.innovate.innovate.Util;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.innovate.innovate.R;

/**
 * Created by Beloved Egbedion, beloved.egbedion@gmail.com on 23/02/2017.
 */

public class DialogUtility {

    private Activity mDialogUniversalInfoActivity;
    private Dialog mDialog;

    private TextView mDialogHeader;
    private TextView mDialogText;
    private TextView mDialogOKButton;
    private ImageView mDialogImage;

    public DialogUtility(Activity mDialogUniversalActivity) {
        this.mDialogUniversalInfoActivity = mDialogUniversalActivity;
    }

    public DialogUtility(MyFirebaseMessagingService myFirebaseMessagingService) {
        this.mDialogUniversalInfoActivity = (Activity) myFirebaseMessagingService.getApplicationContext();
    }

    public void showDialog(String message, String title) {
        if (mDialog == null) {
            mDialog = new Dialog(mDialogUniversalInfoActivity, R.style.CustomDialogTheme);
        }
        mDialog.setContentView(R.layout.dialog_message);
        mDialog.setCancelable(true);
        mDialog.show();

        mDialogHeader = (TextView) mDialog.findViewById(R.id.dialog_title);
        mDialogHeader.setText(title);
        mDialogText = (TextView) mDialog.findViewById(R.id.dialog_text);
        mDialogText.setText(message);
        mDialogOKButton = (TextView) mDialog.findViewById(R.id.dialog_ok);
        mDialogImage = (ImageView) mDialog.findViewById(R.id.dialog_image);

        initDialogButtons();
    }

    private void initDialogButtons() {


        mDialogOKButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }
}
