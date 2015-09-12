package org.npc.test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.npc.test.R;
import org.npc.test.api.models.Product;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            view = inflater.inflate(R.layout.product_list_view_item, parent, false);
        }

        Product product = this.getItem(position);
        if (product != null) {
            TextView lookupCodeTextView = (TextView) view.findViewById(R.id.product_list_item_lookup_code_text_view);
            if (lookupCodeTextView != null) {
                lookupCodeTextView.setText(product.getLookupCode());
            }

            TextView quantityTextView = (TextView) view.findViewById(R.id.product_list_item_quantity_text_view);
            if (quantityTextView != null) {
                quantityTextView.setText(Integer.toString(product.getCount()));
            }
        }

        return view;
    }

    public ProductListAdapter(Context context, List<Product> products) {
        super(context, R.layout.product_list_view_item, products);
    }
}
