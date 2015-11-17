package org.npc.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.npc.test.api.models.Transaction;

/**
 * Created by nfrancav on 11/17/2015.
 */
public class Test_Transaction_Summery extends AppCompatActivity {

    Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_Summary);
    }

    public void AddProductButtonOnClick(View view) {
        this.startActivity(new Intent(this, ProductsList.class));
    }

    public void MakePaymentButtonOnClick(View view) {
        this.startActivity(new Intent(this, CreateProduct.class));
    }

    private TextView getPriceTextView()
    {
        return (TextView) this.findViewById(R.id.PaymentTotalTextField);
    }

    public void CompleteTransactionButtonOnClick(View view){
        this.startActivity(new Intent(this, SearchProducts.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.transaction = data.getParcelableExtra("org.npc.test.TransactionExtrasKey");
        this.getPriceTextView().append(Double.toString(transaction.getPrice()));
    }
}
