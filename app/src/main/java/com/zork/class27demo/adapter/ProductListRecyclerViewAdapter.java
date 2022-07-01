package com.zork.class27demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;
import com.zork.class27demo.R;
import com.zork.class27demo.activities.HomeActivity;
import com.zork.class27demo.activities.OrderFormActivity;
import com.zork.class27demo.models.Product;

import java.util.List;


// (You'll need to change it in the methods below also)
public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.ProductListViewHolder> {


    List<Task> tasks;

    Context callingActivity;


    public ProductListRecyclerViewAdapter(List<Task> _tasks, Context _callingActivity) {
        this.tasks = _tasks;
        this.callingActivity = _callingActivity;
    }

    @NonNull
    @Override

    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View productFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product_list, parent, false);

        return new ProductListViewHolder(productFragment);
    }

    @Override

    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        Task task = tasks.get(position);
        TextView productNameTextView = holder.itemView.findViewById(R.id.fragmentProductNameTextView);
        TextView productDateTextView = holder.itemView.findViewById(R.id.fragmentProductDateTextView);
        TextView productCategoryTextView = holder.itemView.findViewById(R.id.fragmentProductCategoryTextView);

        productNameTextView.setText(position + ". " + task.getTitle());
        productDateTextView.setText(task.getBody());
        productCategoryTextView.setText(task.getStatus());


        View productViewHolder = holder.itemView;
        productViewHolder.setOnClickListener(v ->{
            Intent goToOrderFormIntent = new Intent(callingActivity, OrderFormActivity.class);
            goToOrderFormIntent.putExtra(HomeActivity.PRODUCT_NAME_EXTRA_TAG, task.getTitle());
            callingActivity.startActivity(goToOrderFormIntent);
        });
    }

    @Override
    public int getItemCount() {

//        return 100;

        return tasks.size();
    }


    public static class ProductListViewHolder extends RecyclerView.ViewHolder{
        public ProductListViewHolder(View fragmentItemView) {
            super(fragmentItemView);
        }
    }
}
