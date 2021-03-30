package azmithabet.com.tam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import azmithabet.com.tam.R;
import azmithabet.com.tam.databinding.ProductItemBinding;
import azmithabet.com.tam.model.Product;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private final Context context;
    private final boolean isUserLogin;
    private List<Product> products;
    private LayoutInflater layoutInflater;

    public ProductsAdapter(List<Product> products,
                           Context context, boolean isUserLogin) {
        this.products = products;
        this.isUserLogin = isUserLogin;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ProductItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.product_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Product product = products.get(position);
        Picasso.get().load(product.getImage())
                .placeholder(context.getResources().getDrawable(R.drawable.logo_main))
                .error(context.getResources().getDrawable(R.drawable.ic_error))
                .into(holder.binding.img);
        holder.binding.setProduct(product);
        if (!isUserLogin)
            holder.binding.favImg.setVisibility(View.GONE);
        else
            holder.binding.favImg.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ProductItemBinding binding;

        MyViewHolder(final ProductItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }


}


