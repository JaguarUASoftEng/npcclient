package org.npc.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import org.npc.test.api.models.Product;
import org.npc.test.api.models.Transaction;
import java.util.UUID;

/**
 * Created by nfrancav on 11/17/2015.
 */
public class Test_Transaction_Summery extends AppCompatActivity {

    static final int ADD_PRODUCT = 0;
    Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_summary);
        this.transaction = new Transaction();

//        Dummy product initialization
//        Product p1 = new Product();
//        Product p2 = new Product();
//        p1.setId(UUID.randomUUID());
//        p1.setCount(3);
//        p1.setDescription("Vitamin X");
//        p1.setPrice(4.0);
//        p2.setId(UUID.randomUUID());
//        p2.setCount(6);
//        p2.setDescription("Supplement Y");
//        p2.setPrice(2.0);
//        this.transaction.addProduct(p1);
//        this.transaction.addProduct(p2);
    }

    public void AddProductButtonOnClick(View view) {
        Intent intent = new Intent(this, SearchProducts.class);
        intent.putExtra(this.getResources().getString(R.string.summary_search_transaction_extras_key),
                this.transaction);
        this.startActivityForResult(intent, Test_Transaction_Summery.ADD_PRODUCT);
    }

    public void MakePaymentButtonOnClick(View view) {
        this.startActivity(new Intent(this, CreateProduct.class));
    }

    private TextView getPriceTextView()
    {
        return (TextView) this.findViewById(R.id.PaymentTotalTextField);
    }
        
    public void CompleteTransactionButtonOnClick(View view)
    {
        //Intent intent = new Intent();
        //intent.putExtra(this.getResources().getString(R.string.transaction_extras_key), this.transaction);
        //this.startActivity(new Intent(this, SearchProducts.class));
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Test_Transaction_Summery.ADD_PRODUCT && resultCode == 0)
        {
            this.transaction = data.getParcelableExtra(this.getResources().getString(R.string.search_details_transaction_extras_key));
            this.getPriceTextView().append(Double.toString(this.transaction.getTotal()));
        }
    }
}
