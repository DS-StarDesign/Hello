package ds.app.cs24rider.CallBack;

import ds.app.cs24rider.Models.Maps.MapDirection;

public interface RouteSetupCallback {
    void onLoad(MapDirection response);
    void onStartRide(int position);
    void movieCamera(int position);

    void contact(String call, String mobile);
}

