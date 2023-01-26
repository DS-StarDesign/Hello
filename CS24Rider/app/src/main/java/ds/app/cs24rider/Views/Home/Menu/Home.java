package ds.app.cs24rider.Views.Home.Menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ds.app.cs24rider.CallBack.Presenters.MyLocationUpdatePresenter;
import ds.app.cs24rider.R;
import ds.app.cs24rider.Views.Home.CenterZoomLayoutManager;
import ds.app.cs24rider.Views.Home.MainActivity;
import ds.app.cs24rider.Views.Home.OfferSliderAdapter;
import ds.app.cs24rider.Views.Map.MapsActivity;

public class Home extends Fragment {

    private View view;
    private List<String> colorList = new ArrayList<>();
    private OfferSliderAdapter adapter;
    private ViewPager2 rv;
    private CardView mapMoveBtn;
    private Handler handler = new Handler();
    private MyLocationUpdatePresenter mPresenter;

    public Home(MyLocationUpdatePresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    public static Home newInstance(MyLocationUpdatePresenter mPresenter) {
        return new Home(mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }

    private void init() {
        mapMoveBtn = view.findViewById(R.id.move_to_map_btn);
        rv = view.findViewById(R.id.offer_slider);
        adapter = new OfferSliderAdapter(dummyStrings());
        mapMoveBtn.setOnClickListener(view->{
            mPresenter.getTaskList();
        });
        setUpRecyclerView();
    }

    private List<String> dummyStrings() {
        colorList.add("#354045");
        colorList.add("#20995E");
        colorList.add("#76FF03");
        colorList.add("#E26D1B");
        colorList.add("#911717");
        colorList.add("#9C27B0");
        colorList.add("#FFC107");
        colorList.add("#01579B");
        return colorList;
    }

    private void setUpRecyclerView() {
        rv.setAdapter(adapter);
        rv.setClipToPadding(false);
        rv.setClipChildren(false);
        rv.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        rv.setOffscreenPageLimit(3);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        rv.setPageTransformer(transformer);

        rv.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(sliderRunnable);
                handler.postDelayed(sliderRunnable, 5000);
            }
        });
        rv.setCurrentItem(2);

    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            rv.setCurrentItem(rv.getCurrentItem() + 1);
        }
    };
}