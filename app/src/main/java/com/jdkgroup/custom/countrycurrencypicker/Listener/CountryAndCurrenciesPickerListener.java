package com.jdkgroup.custom.countrycurrencypicker.Listener;

import com.jdkgroup.custom.countrycurrencypicker.Country;
import com.jdkgroup.custom.countrycurrencypicker.Currency;

public interface CountryAndCurrenciesPickerListener {
    void onSelect(Country country, Currency currency);
}
