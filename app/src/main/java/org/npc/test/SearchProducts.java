package org.npc.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class SearchProducts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
    }

    public void searchProductButtonOnClick(View view)
    {
        Intent intent = new Intent(this, ProductDetails.class);
        EditText searchBox = (EditText) this.findViewById(R.id.editText);
        String query = searchBox.getText().toString();
        this.startActivity(intent.putExtra("org.npc.test.ProductIdExtrasKey", query));
    }
}
