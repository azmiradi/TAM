package azmithabet.com.tam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import azmithabet.com.tam.interfaces.OnCategoryClick;
import azmithabet.com.tam.R;
import azmithabet.com.tam.databinding.CategoryItemBinding;
import azmithabet.com.tam.model.Category;

public class CategoryAdapter extends BaseAdapter {

    CategoryItemBinding binding;
    OnCategoryClick onCategoryClick;
    public CategoryAdapter(Context context, List<Category> arrayList,
                           OnCategoryClick onCategoryClick) {
        super(context);
        dataList = arrayList;
        this.onCategoryClick=onCategoryClick;
       }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        binding  = DataBindingUtil.inflate( LayoutInflater.from(viewGroup.getContext())
                , R.layout.category_item, viewGroup, false) ;
        return new MyViewHolder (binding.getRoot());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHold(final int position, Object itemView) {
        Category category= (Category) dataList.get(position);

        Picasso.get().load(category.getImage())
                .placeholder(BASE_CONTEXT.getResources().getDrawable(R.drawable.logo_main))
                .error(BASE_CONTEXT.getResources().getDrawable(R.drawable.ic_error))
                .into(binding.img);

        binding.img.setOnClickListener(v -> onCategoryClick.onCategoryClick(category.getId()));
    }
}