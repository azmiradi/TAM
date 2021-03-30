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
import azmithabet.com.tam.R;
import azmithabet.com.tam.databinding.ProductItemBinding;
import azmithabet.com.tam.model.Category;
import azmithabet.com.tam.model.Product;

public class ProductsAdapter extends BaseAdapter {

   private ProductItemBinding binding;
   private final boolean isUserLogin;
    public ProductsAdapter(Context context, boolean isUserLogin,List<Category> arrayList) {
        super(context);
        dataList = arrayList;
        this.isUserLogin=isUserLogin;
       }

    @NonNull
    @Override
    public BaseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        binding  = DataBindingUtil.inflate( LayoutInflater.from(viewGroup.getContext())
                , R.layout.product_item, viewGroup, false) ;
        return new BaseAdapter.MyViewHolder (binding.getRoot());
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHold(final int position, Object itemView) {
        Product product= (Product) dataList.get(position);
        Picasso.get().load(product.getImage())
                .placeholder(BASE_CONTEXT.getResources().getDrawable(R.drawable.logo_main))
                .error(BASE_CONTEXT.getResources().getDrawable(R.drawable.ic_error))
                .into(binding.img);
        binding.price.setText(product.getCurrency()+"  "+product.getPrice());
        binding.shopName.setText(product.getShopName());
        binding.productName.setText(product.getName());
        binding.ratingVal.setText(product.getRate());

        if (!isUserLogin)
            binding.favImg.setVisibility(View.GONE);
        else
            binding.favImg.setVisibility(View.VISIBLE);

    }
}