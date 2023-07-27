package com.example.selfmadepizza;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class OrdersActivity extends AppCompatActivity {

    private void reload() {
        String[] orders = Database.load(this);

        ViewGroup container = findViewById(R.id.container);

        container.removeAllViews();

        if (orders.length == 0) {
            TextView textView = new TextView(this);
            textView.setText("Немає замовлень");
            container.addView(textView);
        }

        for (int i = 0; i < orders.length; i++) {
            String content = orders[i].replace('|', '\n');
            OrderFragment orderFragment = new OrderFragment(i+1, content);

            getSupportFragmentManager().beginTransaction()
                    .add(container.getId(), orderFragment)
                    .commit();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        reload();

        Button wipe = findViewById(R.id.wipe);
        wipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Database.clear(context);
                reload();
            }
        });
    }
}