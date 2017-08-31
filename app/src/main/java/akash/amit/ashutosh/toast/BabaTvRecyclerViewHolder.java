package akash.amit.ashutosh.toast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by AKASH on 8/31/2017.
 */
public class BabaTvRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView iv1;
    public TextView tvTitle;
    TextView tvDate;
    TextView tvDescription;
    private List<ImageUpload> imgList;
    Context context;
    public BabaTvRecyclerViewHolder(View itemView,List<ImageUpload> imgList) {
        super(itemView);

        this.imgList = imgList;
        context=itemView.getContext();
        itemView.setOnClickListener(this);
        iv1= (ImageView) itemView.findViewById(R.id.imageview_1);
        tvTitle= (TextView) itemView.findViewById(R.id.textview_1);
        tvDate= (TextView) itemView.findViewById(R.id.textview_2);
        tvDescription= (TextView) itemView.findViewById(R.id.textview_3);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/t.ttf");
        tvTitle.setTypeface(typeface);
        Typeface typeface2 = Typeface.createFromAsset(context.getAssets(), "fonts/f.ttf");
        tvDescription.setTypeface(typeface2);
        Typeface typeface3 = Typeface.createFromAsset(context.getAssets(), "fonts/y.ttf");
        tvDate.setTypeface(typeface3);
    }
    @Override
    public void onClick(View view) {
        int position =getAdapterPosition();
        ImageUpload currentImg=imgList.get(position);
        Toast.makeText(view.getContext(), "Clicked  Position = " + currentImg, Toast.LENGTH_SHORT).show();
        Intent browserIntent = new Intent(context,webView.class);
        browserIntent.putExtra("name", currentImg.link);
        context.startActivity(browserIntent);

    }
}
