package azmithabet.com.tam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.islamkhsh.CardSliderAdapter;
import com.squareup.picasso.Picasso;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import azmithabet.com.tam.interfaces.OnCategoryClick;
import azmithabet.com.tam.R;
import azmithabet.com.tam.databinding.CategoryItemBinding;
import azmithabet.com.tam.model.Category;
import io.reactivex.annotations.NonNull;

public class CategoryAdapter extends CardSliderAdapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private LayoutInflater layoutInflater;
    private final OnCategoryClick onCategoryClick;
    private final Context context;
    public CategoryAdapter(List<Category> categories,Context context,OnCategoryClick onCategoryClick){
        this.categories = categories;
        this.context=context;
        this.onCategoryClick=onCategoryClick;
    }

    @Override
    public int getItemCount(){
        return categories.size();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CategoryItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.category_item, parent, false);
        return new CategoryViewHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void bindVH(@NonNull CategoryViewHolder holder, int position) {
        Category category=categories.get(position);
        Picasso.get().load(category.getImage())
                .placeholder(context.getResources().getDrawable(R.drawable.logo_main))
                .error(context.getResources().getDrawable(R.drawable.ic_error))
                .into(holder.binding.img);

        holder.binding.img.setOnClickListener(v -> onCategoryClick.onCategoryClick(category.getId()));
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final CategoryItemBinding binding;
        public CategoryViewHolder(CategoryItemBinding itemBinding){
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}