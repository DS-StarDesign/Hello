package ds.app.cs24rider.Views.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ds.app.cs24rider.Businesses.Maps.MapDirectionBusiness;
import ds.app.cs24rider.CallBack.Presenters.MapDirectionPresenter;
import ds.app.cs24rider.CallBack.RouteSetupCallback;
import ds.app.cs24rider.Models.Maps.MapDirection;
import ds.app.cs24rider.Models.Maps.TaskItem;
import ds.app.cs24rider.Models.Tasks.TasksModel;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Utils.Constance;
import ds.app.cs24rider.Utils.PrefManager;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private Context mContext;
    private List<TasksModel> items;
    private RouteSetupCallback mCallBack;

    private List<MapDirection> mapDirections;
    public TaskListAdapter(Context mContext, List<TasksModel> items, List<MapDirection> mapDirections, RouteSetupCallback mCallBack) {
        this.mContext = mContext;
        this.items = items;
        this.mapDirections = mapDirections;
        this.mCallBack = mCallBack;
    }

    @NonNull
    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.ViewHolder holder, int position) {
        if(PrefManager.getInstance(mContext).getString(PrefManager.RUNNING_TASK_INVOICE_ID) == null || PrefManager.getInstance(mContext).getString(PrefManager.RUNNING_TASK_INVOICE_ID).isEmpty() || items.get(position).getActivity() != 1){
            holder.btn_view.setVisibility(View.GONE);
        }else{
            holder.btn_view.setVisibility(View.VISIBLE);
        }
        holder.pickup.setText(items.get(position).getPickupAddress());
        holder.destination.setText(items.get(position).getAddress());
        holder.rcvName.setText(items.get(position).getRcvName());
        holder.btn_view.setOnClickListener(view -> {
            mCallBack.onStartRide(position);
        });
        holder.callBtn.setOnClickListener(view -> {
            mCallBack.contact(Constance.CALL, items.get(position).getRcvMobile());
        });
        holder.msgBtn.setOnClickListener(view -> {
            mCallBack.contact(Constance.MSG, items.get(position).getRcvMobile());
        });
        mCallBack.movieCamera(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button btn_view;
        private TextView rcvName, pickup, destination;
        private ImageView callBtn, msgBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_view = itemView.findViewById(R.id.task_accept);
            rcvName = itemView.findViewById(R.id.receiver_name);
            pickup = itemView.findViewById(R.id.pickup_address);
            destination = itemView.findViewById(R.id.destination_address);
            callBtn = itemView.findViewById(R.id.call_btn);
            msgBtn = itemView.findViewById(R.id.msg_btn);
        }
    }
}
