package com.abu.utilsmodule;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtil {
    private Context mContext;
    private ProgressDialog dialog = null;

    public ProgressDialogUtil(Context mContext) {
        this.mContext = mContext;
    }

    //-----显示ProgressDialog
    public void showProgress(String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(mContext, ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);//设置点击不消失
        }
        if (dialog.isShowing()) {
            dialog.setMessage(message);
        } else {
            dialog.setMessage(message);
            dialog.show();
        }
    }


    //------取消ProgressDialog
    public void removeProgress() {
        if (dialog == null) {
            return;
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
