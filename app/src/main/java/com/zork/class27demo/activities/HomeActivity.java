package com.zork.class27demo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.zork.class27demo.R;
import com.zork.class27demo.adapter.ProductListRecyclerViewAdapter;


import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    public static final String TAG = "homeActivity";
    public static final String PRODUCT_NAME_EXTRA_TAG = "productName";
    SharedPreferences preferences;

    public static final String DATABASE_NAME = "zork_task_master"; // needs to be the same everywhere in our app!
    ProductListRecyclerViewAdapter adapter;
    List<Task> tasks = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Task newBask = Task.builder()
                .title("Test Title")
                .body("This is a test boddy")
                .status("Complete")
                .build();
        Amplify.API.mutate(
                ModelMutation.create(newBask), // making a GraphQL request to the cloud
                successResponse -> Log.i(TAG, "ProductListActivity.onCreate(): made a product successfully"), // success callback
                failureResponse -> Log.i(TAG, "ProductListActivity.onCreate(): failed with this response: " + failureResponse) // failure callback
        );

        tasks = new ArrayList<>();

        setUpSettingsImageView();
        setUpOrderButton();
        setUpProductListRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // get my nickname!
        String userNickname = preferences.getString(UserSettingsActivity.USER_NICKNAME_TAG, "No Nickname");
        // set my nickname to the view
        TextView userNicknameText = findViewById(R.id.homeNickname);
        userNicknameText.setText(userNickname);

//        products.clear();

        adapter.notifyDataSetChanged();
    }

    private void setUpSettingsImageView(){
        ImageView userSettingsImageView = (ImageView) findViewById(R.id.userSettingsLink);
        userSettingsImageView.setOnClickListener(v -> {
            Intent goToUserSettingsIntent = new Intent(HomeActivity.this, UserSettingsActivity.class);
            startActivity(goToUserSettingsIntent);
        });
    }

    private void setUpOrderButton(){
        // Grab the order button
        Button orderButton = findViewById(R.id.homeOrderButton);
        // setup onClick listener
        orderButton.setOnClickListener(v -> {
        // Setup intent to go to ORderFOrmActivity
            Intent goToAddAProductActivity = new Intent(HomeActivity.this, AddProductActivity.class);
        // start activity
            startActivity(goToAddAProductActivity);
        });
    }

    // TODO: Step 1-1: Add a RecyclerView to the Activity in the WSYIWYG editor
    // TODO: Step 1-2: Grab the RecyclerView
    private void setUpProductListRecyclerView(){
        RecyclerView productListRecyclerView = findViewById(R.id.productListRecyclerView);
        // TODO: Step 1-3: Set the layout manager of the RecyclerView to a LinearLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        // TODO: Step1-3: set the layout manager
        productListRecyclerView.setLayoutManager(layoutManager);

        // TODO: Step 2-2: Make some data items
//

        // TODO: Step 1-5: Create and attach the RecyclerView.Adapter
        // TODO: Step 2-3: Hand in data items(products array)
        // TODO: Step: 3-2 Give context to our adapter
        adapter = new ProductListRecyclerViewAdapter(tasks, this);
        // TODO Step: 1-5: set the adapter recyclerview
        productListRecyclerView.setAdapter(adapter);

    }
}