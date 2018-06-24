package com.dailytodo.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class BaseFragment extends Fragment {

    ProgressDialog dialog;

    public BaseFragment() {

    }

    protected void cancelDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    protected void showDialog() {
        showDialog("Loading...");
    }

    protected void showDialog(String msg) {
        if (dialog == null) {
            dialog = ProgressDialog.show(getActivity(), "", msg, true);
        } else {
            if (!(dialog.isShowing())) {
                dialog = ProgressDialog.show(getActivity(), "", msg, true);
            }
        }
    }

    public static void hideKeyBoard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
