package com.ooftf.widget.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ooftf.arch.frame.mvvm.activity.BaseActivity;
import com.ooftf.sliding.SlidingLayout;
import com.ooftf.widget.R;

/**
 * @author ooftf
 */
@Route(path = "/widget/activity/slidingRecycleView")
public class SlidingRecycleViewActivity extends BaseActivity {
    RecyclerView recyclerView;
    SlidingLayout drawer;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_recycle_view);
        recyclerView = findViewById(R.id.recycler_view);
        drawer = findViewById(R.id.drawer);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> drawer.smoothTurn());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                TextView textView = new TextView(SlidingRecycleViewActivity.this);
                return new RecyclerView.ViewHolder(textView) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ((TextView) holder.itemView).setText(String.valueOf(position));
            }

            @Override
            public int getItemCount() {
                return 50;
            }
        });
    }
}
