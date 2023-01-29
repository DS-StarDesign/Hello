package ds.app.cs24rider.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chaos.view.PinView;

import ds.app.cs24rider.CallBack.Presenters.MapDirectionPresenter;
import ds.app.cs24rider.R;

public class OTPConfirmationDialog extends Dialog {
    private Dialog dialog;

    public OTPConfirmationDialog(@NonNull Context context) {
        super(context);
    }

    public Dialog show(boolean cancelable, MapDirectionPresenter.View mPresenter){
        dialog = new CallingDialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.otp_confirmation_dialog, null);
        Button cancel = view.findViewById(R.id.task_cancel);
        Button accept = view.findViewById(R.id.task_accept);
        PinView pinView = view.findViewById(R.id.pin_view);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        accept.setOnClickListener(view1 -> {
            if(mPresenter != null && pinView.getText().toString().length() == 6){
                mPresenter.onOtpSubmit(pinView.getText().toString());
            }else{
                Toast.makeText(getContext(), "Please enter valid OTP.", Toast.LENGTH_LONG).show();
            }
        });
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.black_trans)));
        dialog.show();
        return dialog;
    }

    public void dismissNow() {
        dialog.dismiss();
    }
}
