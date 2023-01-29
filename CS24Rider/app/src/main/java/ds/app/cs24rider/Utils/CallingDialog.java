package ds.app.cs24rider.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import ds.app.cs24rider.CallBack.Presenters.MyLocationUpdatePresenter;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Views.Map.TaskListSheet;

public class CallingDialog extends Dialog {

    private Dialog dialog;
    private MediaPlayer mp;
    private MyLocationUpdatePresenter mPresenter;
    private Context context;

    public CallingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CallingDialog(@NonNull Context context, MyLocationUpdatePresenter mPresenter) {
        super(context);
        this.mPresenter = mPresenter;
        this.context = context;
    }

    private void init_ringtone() {
        this.mp = MediaPlayer.create(getContext(), R.raw.ringtoons);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                init_ringtone();
            }
        });
    }

    public Dialog show(boolean cancelable, String pickup){
        dialog = new CallingDialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.call_screen, null);
        Button cancel = view.findViewById(R.id.task_cancel);
        Button accept = view.findViewById(R.id.task_accept);
        View circle1 = view.findViewById(R.id.soundRipple2);
        View circle2 = view.findViewById(R.id.soundRipple3);
        TextView pickupAddress = view.findViewById(R.id.pickup_address);
        pickupAddress.setText(pickup);

        Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
        Animation pulse2 = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);

        pulse.setRepeatCount(Animation.INFINITE);
        pulse2.setRepeatCount(Animation.INFINITE);
        pulse2.setRepeatMode(Animation.REVERSE);

        circle1.startAnimation(pulse);
        circle2.startAnimation(pulse2);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                mp.reset();
                dialog.dismiss();
            }
        });
        accept.setOnClickListener(view1 -> {
            mp.stop();
            mp.reset();
            dialog.dismiss();
            if(mPresenter != null){
                mPresenter.getTaskList();
            }
        });
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.black_trans)));
        dialog.show();
        init_ringtone();
        return dialog;
    }
}
