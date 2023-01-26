package ds.app.cs24rider.Views.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ds.app.cs24rider.CallBack.RouteSetupCallback;
import ds.app.cs24rider.Models.Maps.EndLocation;
import ds.app.cs24rider.Models.Maps.TaskItem;
import ds.app.cs24rider.Models.Tasks.TasksModel;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Views.Adapter.TaskListAdapter;

public class TaskListSheet extends BottomSheetDialogFragment {

    private View view;
    private RecyclerView mRecycler;
    private TaskListAdapter mAdapter;
    private RouteSetupCallback mCallBack;

    private List<TasksModel> items = new ArrayList<>();
    private String tasks;

    public TaskListSheet(RouteSetupCallback mCallBack, String tasks) {
        this.tasks = tasks;
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

        items.clear();
        mRecycler = view.findViewById(R.id.task_list_recycler);
        mRecycler.setHasFixedSize(false);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        try {
            JSONArray array = new JSONArray(tasks);
            for (int i=0; i<array.length(); i++){
                items.add(new TasksModel().prepare(array.getJSONObject(0)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter = new TaskListAdapter(getActivity(), items, mCallBack);
        mRecycler.setAdapter(mAdapter);

    }
}
