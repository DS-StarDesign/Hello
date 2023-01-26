package ds.app.cs24rider.Querys;

import static ds.app.cs24rider.Utils.Utils.decodePoly;

import android.content.Context;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import ds.app.cs24rider.CallBack.InterActors.MapDirectionInterActor;
import ds.app.cs24rider.Models.Maps.MapDirection;
import ds.app.cs24rider.Models.Maps.MapDirectionResponse;
import ds.app.cs24rider.Models.Maps.Routes;
import ds.app.cs24rider.Requests.MapService;
import ds.app.cs24rider.Utils.RestClient;
import ds.app.cs24rider.Utils.Threading.AbstractInteractor;
import ds.app.cs24rider.Utils.Threading.Executor;
import ds.app.cs24rider.Utils.Threading.MainThread;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapQuery extends AbstractInteractor {

    private MapService mCall;
    private String destination, origin, mode, key;
    private MapDirectionInterActor callBack;

    public MapQuery(Executor threadExecutor, MainThread mainThread, Context mContext, MapDirectionInterActor callBack, String destination, String origin, String mode, String key) {
        super(threadExecutor, mainThread, mContext);

        this.mCall = RestClient.getService(mContext, MapService.class, false);
        this.callBack = callBack;
        this.destination = destination;
        this.origin = origin;
        this.mode = mode;
        this.key = key;
    }

    @Override
    public void run() {
        mCall.getDirections(destination, origin, mode, key).enqueue(new Callback<MapDirectionResponse>() {
            @Override
            public void onResponse(Call<MapDirectionResponse> call, Response<MapDirectionResponse> response) {
                if(response.isSuccessful()){

                    if (response.body().getStatus().equals("OK")) {
                        MapDirection mapDirection = new MapDirection();
                        List<Routes> jRoutes = response.body().getRoutes();
                        ArrayList<LatLng> allPoints = new ArrayList<>();
                        ArrayList points = new ArrayList();

                        for(int i=0;i<jRoutes.size();i++){
                            for(int j=0;j<jRoutes.get(i).getLegs().size();j++){
                                for(int k=0;k<jRoutes.get(i).getLegs().get(j).getSteps().size();k++){
                                    String polyline = jRoutes.get(i).getLegs().get(j).getSteps().get(k).getPolyline().getPoints();
                                    List list = decodePoly(polyline);
                                    for(int l=0;l <list.size();l++){
                                        points.add(new LatLng(((LatLng)list.get(l)).latitude, ((LatLng)list.get(l)).longitude));
                                    }
                                }
                                allPoints.addAll(points);
                            }
                        }
                        mapDirection.setLatitudePickup(Double.parseDouble(response.body().getRoutes().get(0).getLegs().get(0).getStartLocation().getLat()));
                        mapDirection.setLongitudePickup(Double.parseDouble(response.body().getRoutes().get(0).getLegs().get(0).getStartLocation().getLng()));
                        mapDirection.setLatitudeDestination(Double.parseDouble(response.body().getRoutes().get(0).getLegs().get(0).getEndLocation().getLat()));
                        mapDirection.setLongitudeDestination(Double.parseDouble(response.body().getRoutes().get(0).getLegs().get(0).getEndLocation().getLng()));
                        mapDirection.setPoints(allPoints);
                        mMainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(mapDirection);
                            }
                        });
                    }else{
                        callBack.onFailure(response.message());
                    }
                }else{
                    callBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<MapDirectionResponse> call, Throwable t) {
                callBack.onFailure(t.getMessage());
            }
        });
    }
}
