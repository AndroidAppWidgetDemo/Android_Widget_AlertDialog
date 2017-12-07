package com.xiaxl.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v4.widget.Space;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


/**
 * 弹框
 */
public class TipsDialog {

    public static class Builder implements View.OnClickListener {
        private DialogParams params;
        private AlertDialog mDialog;

        public Builder(Context context) {
            params = new DialogParams();
            params.context = context;
        }

        public Builder setTitle(@StringRes int textId) {
            setTitle(params.context.getString(textId));
            return this;
        }

        public Builder setTitle(CharSequence title) {
            params.title = title;
            return this;
        }

        public Builder setMessage(@StringRes int textId) {
            setMessage(params.context.getString(textId));
            return this;
        }

        public Builder setMessage(CharSequence message) {
            params.message = message;
            return this;
        }

        public Builder setPositiveButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            return setPositiveButton(params.context.getString(textId), listener);
        }

        public Builder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
            params.positiveText = text;
            params.positiveClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            return setNegativeButton(params.context.getString(textId), listener);
        }

        public Builder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
            params.negativeText = text;
            params.negativeClickListener = listener;
            return this;
        }

        public Builder setCancelAble(boolean cancelAble) {
            params.cancelAble = cancelAble;
            return this;
        }

        public AlertDialog create() {
            View view = LayoutInflater.from(params.context).inflate(R.layout.layout_dialog, null);
            TextView titleTv = (TextView) view.findViewById(R.id.title_tv);
            TextView messageTv = (TextView) view.findViewById(R.id.message_tv);
            Space marginSpace = (Space) view.findViewById(R.id.margin_space);
            TextView positiveBtn = (TextView) view.findViewById(R.id.positive_btn);
            TextView negativeBtn = (TextView) view.findViewById(R.id.negative_btn);

            //没有标题或者内容时，隐藏中间分割线
            if (params.title == null || params.message == null) {
                marginSpace.setVisibility(View.GONE);
            }

            if (params.title == null)
                titleTv.setVisibility(View.GONE);
            else
                titleTv.setText(params.title);

            if (params.message == null)
                messageTv.setVisibility(View.GONE);
            else
                messageTv.setText(params.message);


            if (params.positiveText == null)
                positiveBtn.setVisibility(View.GONE);
            else
                positiveBtn.setText(params.positiveText);

            if (params.negativeText == null)
                negativeBtn.setVisibility(View.GONE);
            else
                negativeBtn.setText(params.negativeText);
            //
            positiveBtn.setOnClickListener(this);
            negativeBtn.setOnClickListener(this);

            mDialog = new AlertDialog.Builder(params.context, R.style.xiaxl_AlertDialog).setView(view).create();
            mDialog.setCancelable(params.cancelAble);
            return mDialog;
        }

        public AlertDialog show() {
            AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.positive_btn) {
                if (params.positiveClickListener != null)
                    params.positiveClickListener.onClick(mDialog, DialogInterface.BUTTON_POSITIVE);
            } else if (v.getId() == R.id.negative_btn) {
                if (params.negativeClickListener != null)
                    params.negativeClickListener.onClick(mDialog, DialogInterface.BUTTON_NEGATIVE);
            }
        }
    }

    private static class DialogParams {
        public Context context;

        public boolean cancelAble = true;
        public CharSequence title;
        public CharSequence message;
        public CharSequence positiveText;
        public CharSequence negativeText;
        public DialogInterface.OnClickListener positiveClickListener;
        public DialogInterface.OnClickListener negativeClickListener;
    }
}
