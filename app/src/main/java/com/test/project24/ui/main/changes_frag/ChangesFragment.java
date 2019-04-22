package com.test.project24.ui.main.changes_frag;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.test.project24.BR;
import com.test.project24.R;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.databinding.FragmentChangesBinding;
import com.test.project24.ui.base.BaseFragment;
import com.test.project24.ui.main.changes_frag.adapter.ChangesAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ChangesFragment extends BaseFragment<FragmentChangesBinding, ChangesViewModel>
        implements HasSupportFragmentInjector, ChangesNavigator {


    private static String SELECTED_LANG = "en";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    ChangesAdapter adapter;

    private boolean startDateClicked;

    private String startDateMs = "";
    private String endDateMs = "";


    public static ChangesFragment newInstance() {

        Bundle args = new Bundle();
        ChangesFragment fragment = new ChangesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        getViewModel().setViewNavigator(this);
    }

    @Override
    public ChangesViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ChangesViewModel.class);
    }

    private void setAdapter() {
        getViewDataBinding().rvChanges.setAdapter(adapter);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_changes;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onChangesReceived(List<MovieDetail> list) {
        if (!list.isEmpty()) {
            getViewModel().getMovieDetail(list.get(0).getId());
        }
    }

    @Override
    public void onStartDateClicked() {
        startDateClicked = true;
        showDatePickerDialog();
    }

    @Override
    public void onEndDateClicked() {
        startDateClicked = false;
        showDatePickerDialog();
    }

    @Override
    public void onFetchClicked() {
        getViewModel().getChangesList(SELECTED_LANG, startDateMs, endDateMs, 1);
    }

    @Override
    public void onRetryClicked() {
        getViewModel().getChangesList(SELECTED_LANG, startDateMs, endDateMs, 1);
    }

    @Override
    public void onErrorReceived(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat date = new SimpleDateFormat(myFormat, Locale.US);

        if (startDateClicked) {
            startDateMs = date.format(calendar.getTime());
            Log.e("Time", startDateMs + " ===> startDateMs");
            getViewModel().startDate.set(startDateMs);
        } else {
            endDateMs = date.format(calendar.getTime());
            Log.e("Time", endDateMs + " ===> endDateMs");
            getViewModel().endDate.set(endDateMs);
        }
    };

    private void showDatePickerDialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {

            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(getActivity(), dateSetListener, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }
}
