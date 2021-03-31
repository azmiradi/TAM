package azmithabet.com.tam.view_model;

import android.content.Context;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import azmithabet.com.tam.model.Category;
import azmithabet.com.tam.model.HomeBaseResponse;
import azmithabet.com.tam.model.Product;
import azmithabet.com.tam.repository.HomeRepository;

public class HomeViewModel extends ViewModel {

    private final HomeRepository homeRepository =new HomeRepository();

    public LiveData<List<Category>> getCategory() {
        return homeRepository.getCategory();
    }

    public LiveData<HomeBaseResponse> getHome() {
        return homeRepository.getHome();
    }

    public LiveData<List<Product>> getProductsByCategory(String categoryID,
                                                        List<Product> products) {
        return homeRepository.getProductsByCategory(categoryID,products);
    }
    public LiveData<Boolean> isUserLogin(Context context) {
        return homeRepository.isUserLogin(context);
    }

    @Override
    protected void onCleared() {
        homeRepository.compositeDisposable.clear();
        super.onCleared();
    }
}