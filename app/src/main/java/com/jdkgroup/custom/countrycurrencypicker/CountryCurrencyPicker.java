package com.jdkgroup.custom.countrycurrencypicker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jdkgroup.countrycurrencypicker.R;
import com.jdkgroup.custom.countrycurrencypicker.Listener.CountryAndCurrenciesPickerListener;
import com.jdkgroup.custom.countrycurrencypicker.Listener.CountryPickerListener;
import com.jdkgroup.custom.countrycurrencypicker.Listener.CurrencyAndCountriesPickerListener;
import com.jdkgroup.custom.countrycurrencypicker.Listener.CurrencyPickerListener;

import java.util.ArrayList;

public class CountryCurrencyPicker extends DialogFragment {
    private final static String logTAG = CountryCurrencyPicker.class.getName() + ".";
    public static final String DIALOG_NAME = CountryCurrencyPicker.class.getName();

    //region Member
    private View myView;

    private Object mListener;

    private EditText txtSearch;
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CountryCurrencyAdapter mAdapter;

    private Boolean mShowSubTitle = true;
    private Boolean mShowCodeOrCurrency = true;

    private FilterListAsync filterListAsync;

    private String dialogTitle;

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }
    //endregion

    //region Constructor
    public CountryCurrencyPicker() {
    }

    public static CountryCurrencyPicker newInstance(@NonNull Object listener) {
        CountryCurrencyPicker picker = new CountryCurrencyPicker();
        picker.mListener = listener;

        return picker;
    }

    public static CountryCurrencyPicker newInstance(@NonNull Boolean showSubtitle, @NonNull Boolean showCodeOrCurrency, @NonNull Object listener) {
        CountryCurrencyPicker picker = new CountryCurrencyPicker();
        picker.mListener = listener;

        picker.mShowSubTitle = showSubtitle;
        picker.mShowCodeOrCurrency = showCodeOrCurrency;

        return picker;
    }
    //endregion

    //region onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (dialogTitle != null) {
            this.setStyle(STYLE_NORMAL, R.style.countryCurrencyPicker_dialog);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.countrycurrencypicker_fragment, container, false);

        if (getDialog() != null && dialogTitle != null) {
            this.getDialog().setTitle(dialogTitle);
        }

        return myView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtSearch = myView.findViewById(R.id.txt_search);
        progressBar = myView.findViewById(R.id.progressbar);
        mRecyclerView = myView.findViewById(R.id.recycler);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        EventsListener();
    }
    //endregion

    //region Events
    @Override
    public void onStart() {
        super.onStart();
        getData(null);
    }

    private void EventsListener() {
        mRecyclerView.setOnTouchListener((v, event) -> {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);
            return false;
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtSearch.hasFocus()) {
                    getData(editable.toString());
                }
            }
        });
    }
    //endregion

    //region Functions
    private void getData(String filterString) {
        if (filterListAsync == null) {
            filterListAsync = (FilterListAsync) new FilterListAsync().execute(filterString);
        } else {
            filterListAsync.cancel(true);
            filterListAsync = (FilterListAsync) new FilterListAsync().execute(filterString);
        }
    }

    private void setRecyclerView(ArrayList<Country> countryList, ArrayList<Currency> currencyList) {
        if (countryList == null && currencyList == null) {
            mAdapter = new CountryCurrencyAdapter(new ArrayList<>(), new ArrayList<>(), mListener, mShowSubTitle, mShowCodeOrCurrency);
        } else {
            mAdapter = new CountryCurrencyAdapter(countryList, currencyList, mListener, mShowSubTitle, mShowCodeOrCurrency);
        }
        mRecyclerView.setAdapter(mAdapter);
    }
    //endregion

    private class FilterListAsync extends AsyncTask<String, Void, Void> {
        private ArrayList<Country> mCountryList = null;
        private ArrayList<Currency> mCurrencyList = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            for (String filterString : strings) {

                if (mListener instanceof CountryPickerListener) {
                    mCountryList = Country.listAll(getActivity(), filterString);
                } else if (mListener instanceof CountryAndCurrenciesPickerListener) {
                    mCountryList = Country.listAllWithCurrencies(getActivity(), filterString);
                } else if (mListener instanceof CurrencyPickerListener) {
                    mCurrencyList = Currency.listAll(getActivity(), filterString);
                } else if (mListener instanceof CurrencyAndCountriesPickerListener) {
                    mCurrencyList = Currency.listAllWithCountries(getActivity(), filterString);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setRecyclerView(mCountryList, mCurrencyList);
            progressBar.setVisibility(View.GONE);
        }
    }
}
