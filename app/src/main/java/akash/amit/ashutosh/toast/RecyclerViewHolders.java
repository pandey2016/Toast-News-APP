package akash.amit.ashutosh.toast;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView iv1;
    public TextView tvTitle;
    TextView tvDate;
    TextView tvDescription;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);


         iv1= (ImageView) itemView.findViewById(R.id.imageview_1);
         tvTitle= (TextView) itemView.findViewById(R.id.textview_1);
         tvDate= (TextView) itemView.findViewById(R.id.textview_2);
         tvDescription= (TextView) itemView.findViewById(R.id.textview_3);


        //countryName = (TextView)itemView.findViewById(R.id.country_name);
        //countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}