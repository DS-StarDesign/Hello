package ds.app.cs24rider.CallBack.Presenters;

import ds.app.cs24rider.Models.Maps.MapDirection;

public interface MapDirectionPresenter {
    interface View {
        void onSuccess(MapDirection response);
        void onFailed(String error);
    }
    void loadDirections(String destination, String origin, String mode, String key);
}
