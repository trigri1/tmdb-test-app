package com.test.project24.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.test.project24.data.IDataManager;
import com.test.project24.utils.CommonUtils;
import com.test.project24.utils.locale.AppLocale;
import com.test.project24.utils.locale.LocaleContextWrapper;

import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by Gohar Ali on 30/10/2017.
 */

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements BaseView {


    @Inject
    IDataManager dataManager;

    private ProgressDialog progressBar;


    DB viewDataBinding;
    VM viewModel;


    @Override
    protected void attachBaseContext(Context newBase) {

        String lang = AppLocale.getLang(newBase);

        Context context;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            context = newBase;
            AppLocale.changeLanguage(context, lang);
        } else {
            Locale myLocale = new Locale(lang);
//            Locale myLocale = new Locale("ar");
            context = LocaleContextWrapper.wrap(newBase, myLocale);
        }

        super.attachBaseContext(context);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        performDataBinding();
    }


    public abstract void initActivity();

    private void performDataBinding() {

        //bind view with xml, getLayoutId() is overridden in the view class (Activity)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());

        //a generic viewModel class that is initialized on the basis of class given by View and getModelView is
        //overridden in view class (Activity)
        viewModel = viewModel == null ? getViewModel() : viewModel;

        //ViewModel id that is used in the xml, to make it generic we use this method to attach
        //current ViewModel(for view class) with viewModel variable in the binding that is declared in xml
        viewDataBinding.setVariable(getBindingVariable(), viewModel);

        //to run any pending bindings this should be called on UI thread
        viewDataBinding.executePendingBindings();


    }


    public DB getViewDataBinding() {
        return viewDataBinding;
    }

    @Override
    public void showLoading() {

        hideLoading();
        progressBar = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (progressBar != null && progressBar.isShowing()) {
            progressBar.cancel();
        }
    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public Snackbar showSnackBar(String message, int duration) {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup)
                findViewById(android.R.id.content)).getChildAt(0);

        return CommonUtils.showSnackBar(viewGroup, message, duration);

    }

    @Override
    public Snackbar showSnackBar(int message, int duration) {
        return showSnackBar(getString(message), duration);
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public IDataManager getDataManager() {
        return dataManager;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract VM getViewModel();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();


}
