package com.jdkgroup.custom.countrycurrencypicker.Listener;

import com.jdkgroup.custom.countrycurrencypicker.Country;
import com.jdkgroup.custom.countrycurrencypicker.Currency;

import java.util.ArrayList;

public interface CurrencyAndCountriesPickerListener {
    void onSelect(Currency currency, ArrayList<Country> countries, ArrayList<String> countriesNames);
}
