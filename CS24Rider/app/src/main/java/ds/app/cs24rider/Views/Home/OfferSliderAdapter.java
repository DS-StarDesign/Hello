package ds.app.cs24rider.Views.Home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ds.app.cs24rider.R;

public class OfferSliderAdapter extends RecyclerView.Adapter<OfferSliderAdapter.ViewHolder> {

    private List<String> colorList;

    public OfferSliderAdapter(List<String> colorList) {
        this.colorList = colorList;
    }

    @NonNull
    @Override
    public OfferSliderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OfferSliderAdapter.ViewHolder holder, int position) {
        holder.ivProductImg.setBackgroundColor(Color.parseColor(colorList.get(position)));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivProductImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImg = itemView.findViewById(R.id.ivProductImg);
        }
    }
}
