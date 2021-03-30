package azmithabet.com.tam.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import azmithabet.com.tam.R;
import azmithabet.com.tam.adapter.CategoryAdapter;
import azmithabet.com.tam.adapter.ProductsAdapter;
import azmithabet.com.tam.databinding.ActivityMainBinding;
import azmithabet.com.tam.interfaces.OnGetData;
import azmithabet.com.tam.model.HomeResponse;
import azmithabet.com.tam.view_model.HomeViewModel;

import static azmithabet.com.tam.util.Constants.COREECT_RESPONSE_CODE;

public class HomeActivity extends BaseActivity implements OnGetData, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HomeActivity";
    private HomeViewModel homeViewModel;
    private ActivityMainBinding binding;
    private ProductsAdapter whatsNewAdaptor;
    private ProductsAdapter trendingAdaptor;
    private CategoryAdapter categoryAdapter;
    private boolean isHomeGet, isCategoryGet, isUserLogin = false;
    private HomeResponse homeResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hidStatusBar();

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        internetListener();
        Initialization();
        getData();
    }

    private void internetListener() {
        checkInternet(this, () -> Log.d(TAG, "onConnectionChange: "));
    }

    private void hidStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void Initialization() {
        homeViewModel = new ViewModelProvider(this)
                .get(HomeViewModel.class);
        checkLogin();
        binding.refresh.setOnRefreshListener(this);

    }

    private void checkLogin() {
        homeViewModel.isUserLogin(this).observe(this, isLogin ->
        {
            isUserLogin = isLogin;
            initRecyclers();
        });
    }

    private void initRecyclers() {
        //Category Recycler init
        LinearLayoutManager layoutManagerCategory =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
                        , false);
        binding.categoryRecycler.setLayoutManager(layoutManagerCategory);
        categoryAdapter = new CategoryAdapter(this, new ArrayList<>(), this::filterProducts);

        binding.categoryRecycler.setAdapter(categoryAdapter);

        //Whats the new Recycler init
        LinearLayoutManager layoutManagerWhatsNew =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                        false);
        binding.newRecycler.setLayoutManager(layoutManagerWhatsNew);
        whatsNewAdaptor = new ProductsAdapter(this, isUserLogin, new ArrayList<>());
        binding.newRecycler.setAdapter(whatsNewAdaptor);
        binding.indicatorTheNew.attachToRecyclerView(binding.newRecycler);

        //Trending Recycler init
        LinearLayoutManager layoutManagerTrending =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                        false);
        binding.trendingRecycler.setLayoutManager(layoutManagerTrending);
        trendingAdaptor = new ProductsAdapter(this, isUserLogin, new ArrayList<>());
        binding.trendingRecycler.setAdapter(trendingAdaptor);
        binding.indicatorTrending.attachToRecyclerView(binding.trendingRecycler);
    }

    private void filterProducts(String categoryID) {
        filterTrendingProducts(categoryID);
        filterNewProducts(categoryID);
    }

    private void filterNewProducts(String categoryID) {
        homeViewModel.getProductsByCategory(categoryID,
                homeResponse.getWhatsNew())
                .observe(this, products ->
                {
                    whatsNewAdaptor.setDataList(products);
                    if (products.size() > 0) {
                        binding.noNew.setVisibility(View.GONE);
                    } else {
                        binding.noNew.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void filterTrendingProducts(String categoryID) {
        homeViewModel.getProductsByCategory(categoryID,
                homeResponse.getTrending())
                .observe(this, products -> {
                    trendingAdaptor.setDataList(products);

                    if (products.size() > 0) {
                        binding.noTrending.setVisibility(View.GONE);
                    } else {
                        binding.noTrending.setVisibility(View.VISIBLE);
                    }
                });
    }


    private void getCategories() {
        homeViewModel.getCategory()
                .observe(this, categoryList -> {
                    isCategoryGet = true;
                    onDataGet();
                    if (categoryList!=null)
                       categoryAdapter.setDataList(categoryList);
                });
    }


    private void getHomeData() {
        homeViewModel.getHome()
                .observe(this, homeResponse -> {
                    this.homeResponse = homeResponse;
                    checkHomeResult();
                });
    }

    private void checkHomeResult() {
        isHomeGet = true;
        onDataGet();
        if (homeResponse!=null) {
            if (homeResponse.getCode() == COREECT_RESPONSE_CODE) {
                checkProducts();
            } else {
                showToast(homeResponse.getMsg());
            }
        }
        else{
            showToast(getString(R.string.no_internet));
        }
    }

    private void checkProducts() {
        whatsNewAdaptor.setDataList(homeResponse.getWhatsNew());
        trendingAdaptor.setDataList(homeResponse.getTrending());

        if (homeResponse.getWhatsNew().size() > 0) {
            binding.noNew.setVisibility(View.GONE);
        } else {
            binding.noNew.setVisibility(View.VISIBLE);
        }


        if (homeResponse.getTrending().size() > 0) {
            binding.noTrending.setVisibility(View.GONE);
        } else {
            binding.noTrending.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDataGet() {
        if (isCategoryGet && isHomeGet) {
            hideDialog();
        }
    }

    @Override
    public void getData() {
        isHomeGet = false;
        isCategoryGet = false;
        showProgressDialog(null, getString(R.string.loading_home));
        getCategories();
        getHomeData();
    }


    @Override
    public void onRefresh() {
        getData();
        binding.refresh.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindListener();
    }

    @Override
    protected void onDestroy() {
        unbindListener();
        super.onDestroy();
    }
}