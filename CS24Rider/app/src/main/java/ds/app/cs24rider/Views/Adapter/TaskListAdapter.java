package ds.app.cs24rider.Views.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> implements MapDirectionPresenter.View{

    private Context mContext;
    private List<TasksModel> items;
    private MapDirectionPresenter mPresenter;
    private RouteSetupCallback mCallBack;
    private String MAP_KEY;
    public TaskListAdapter(Context mContext, List<TasksModel> items, RouteSetupCallback mCallBack) {
        this.mContext = mContext;
        this.items = items;
        this.mCallBack = mCallBack;
        this.mPresenter = new MapDirectionBusiness(mContext, this);
        this.MAP_KEY = mContext.getResources().getString(R.string.MAP_API_KEY);
    }

    @NonNull
    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.ViewHolder holder, int position) {
        String destination = items.get(position).getStartLat().concat(", ").concat(items.get(position).getStartLong());
        String origin = items.get(position).getLatitude().concat(", ").concat(items.get(position).getLongitude());
        mPresenter.loadDirections(destination, origin, "driving", MAP_KEY);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onSuccess(MapDirection response) {
        mCallBack.onLoad(response);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
