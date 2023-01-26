package ds.app.cs24rider.CallBack;

public interface MyLocationUpdate {
    void onResponse(boolean status, String message);
    void onTaskListResponse(boolean status, String message);
}
