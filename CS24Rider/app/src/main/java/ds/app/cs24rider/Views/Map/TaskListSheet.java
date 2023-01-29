package ds.app.cs24rider.Views.Map;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.List;

import ds.app.cs24rider.CallBack.RouteSetupCallback;
import ds.app.cs24rider.Models.Maps.MapDirection;
import ds.app.cs24rider.Models.Tasks.TasksModel;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Utils.PrefManager;
import ds.app.cs24rider.Views.Adapter.TaskListAdapter;

public class TaskListSheet extends BottomSheetDialogFragment {

    private View view;
    private LinearLayout layout;
    private RecyclerView mRecycler;
    private TaskListAdapter mAdapter;
    private RouteSetupCallback mCallBack;
    private TextView taskCount;
    private PrefManager mPref;

    private Button accept, cancel;

    private List<TasksModel> items;
    private List<MapDirection> mapDirections;

    public TaskListSheet(RouteSetupCallback mCallBack, List<TasksModel> items, List<MapDirection> mapDirections) {
        this.items = items;
        this.mapDirections = mapDirections;
        this.mCallBack = mCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.task_list_sheet, container, false);

        init();

        return view;
    }

    private void init(){
        taskCount = view.findViewById(R.id.sheet_task_count);
        accept = view.findViewById(R.id.task_accept);
        cancel = view.findViewById(R.id.task_cancel);
        layout = view.findViewById(R.id.btn_view);
        mRecycler = view.findViewById(R.id.viewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRecycler.setScrollIndicators(View.SCROLL_INDICATOR_BOTTOM);
        }
        mRecycler.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mPref = PrefManager.getInstance(getContext());
        taskCount.setText("You have got ".concat(String.valueOf(items.size())).concat(" new delivery"));
        if(items.size() > 0){
            layout.setVisibility(mPref.getString(PrefManager.RUNNING_TASK_INVOICE_ID).equalsIgnoreCase(items.get(0).getInId()) ? View.GONE : View.VISIBLE);
            //layout.setVisibility(mPref.getString(PrefManager.CANCEL_TASK_INVOICE_ID).equalsIgnoreCase(items.get(0).getInId()) ? View.GONE : View.VISIBLE);
        }
        accept.setOnClickListener(view1 -> {
            layout.setVisibility(View.GONE);
            if(items.size() > 0){
                mPref.putString(PrefManager.RUNNING_TASK_INVOICE_ID, items.get(0).getInId());
                mAdapter.notifyDataSetChanged();
            }
        });

        cancel.setOnClickListener(view1 -> {
            layout.setVisibility(View.GONE);
            if(items.size() > 0){
                mPref.putString(PrefManager.CANCEL_TASK_INVOICE_ID, items.get(0).getInId());
                mAdapter.notifyDataSetChanged();
            }
        });

        mAdapter = new TaskListAdapter(getActivity(), items, mapDirections, mCallBack);
        mRecycler.setAdapter(mAdapter);

    }
}
