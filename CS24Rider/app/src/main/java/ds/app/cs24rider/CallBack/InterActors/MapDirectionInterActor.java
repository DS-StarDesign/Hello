package ds.app.cs24rider.CallBack.InterActors;

import ds.app.cs24rider.Models.Maps.MapDirection;

public interface MapDirectionInterActor {
    void onSuccess(MapDirection response);
    void onFailure(String error);
}
