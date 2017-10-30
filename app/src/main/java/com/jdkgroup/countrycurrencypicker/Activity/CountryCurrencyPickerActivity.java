package com.jdkgroup.countrycurrencypicker.Activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jdkgroup.countrycurrencypicker.R;
import com.jdkgroup.custom.countrycurrencypicker.Country;
import com.jdkgroup.custom.countrycurrencypicker.CountryCurrencyPicker;
import com.jdkgroup.custom.countrycurrencypicker.Currency;
import com.jdkgroup.custom.countrycurrencypicker.Listener.CountryAndCurrenciesPickerListener;
import com.jdkgroup.custom.countrycurrencypicker.Listener.CountryPickerListener;
import com.jdkgroup.custom.countrycurrencypicker.Listener.CurrencyAndCountriesPickerListener;
import com.jdkgroup.custom.countrycurrencypicker.Listener.CurrencyPickerListener;

public class CountryCurrencyPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_currency_picker);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getItemId() == R.id.action_country) {
            getSupportActionBar().setTitle(R.string.menu_country);

            CountryCurrencyPicker pickerFragment = CountryCurrencyPicker.newInstance((CountryPickerListener) country -> Toast.makeText(CountryCurrencyPickerActivity.this,
                    String.format("name: %s\ncode: %s", country.getName(), country.getCode()), Toast.LENGTH_SHORT).show());

            getSupportFragmentManager().beginTransaction().replace(R.id.container, pickerFragment).commit();

        } else if (id == R.id.action_country_dialog) {
            CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(new CountryPickerListener() {
                @Override
                public void onSelect(Country country) {
                    Toast.makeText(CountryCurrencyPickerActivity.this,
                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
                            , Toast.LENGTH_SHORT).show();

                    DialogFragment dialogFragment =
                            (DialogFragment) getSupportFragmentManager().findFragmentByTag(CountryCurrencyPicker.DIALOG_NAME);
                    dialogFragment.dismiss();
                }
            });

            pickerDialog.setDialogTitle(getString(R.string.country_dialog_title));
            pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

        } else if (id == R.id.action_country_currency) {
            getSupportActionBar().setTitle(R.string.menu_country_currency);

            CountryCurrencyPicker pickerFragment = CountryCurrencyPicker.newInstance((CountryAndCurrenciesPickerListener) (country, currency) -> Toast.makeText(CountryCurrencyPickerActivity.this,
                    String.format("name: %s\ncurrencySymbol: %s", country.getName(), currency.getSymbol())
                    , Toast.LENGTH_SHORT).show());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, pickerFragment).commit();

        } else if (id == R.id.action_country_currency_dialog) {
            CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(new CountryAndCurrenciesPickerListener() {
                @Override
                public void onSelect(Country country, Currency currency) {
                    Toast.makeText(CountryCurrencyPickerActivity.this,
                            String.format("name: %s\ncurrencySymbol: %s", country.getName(), currency.getSymbol())
                            , Toast.LENGTH_SHORT).show();

                    DialogFragment dialogFragment =
                            (DialogFragment) getSupportFragmentManager().findFragmentByTag(CountryCurrencyPicker.DIALOG_NAME);
                    dialogFragment.dismiss();
                }
            });

            pickerDialog.setDialogTitle(getString(R.string.country_currency_dialog_title));
            pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

        } else if (id == R.id.action_currency) {
            getSupportActionBar().setTitle(R.string.menu_currency);

            CountryCurrencyPicker pickerFragment = CountryCurrencyPicker.newInstance((CurrencyPickerListener) currency -> Toast.makeText(CountryCurrencyPickerActivity.this,
                    String.format("name: %s\nsymbol: %s", currency.getName(), currency.getName())
                    , Toast.LENGTH_SHORT).show());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, pickerFragment).commit();

        } else if (id == R.id.action_currency_dialog) {
            CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance((CurrencyPickerListener) currency -> {
                Toast.makeText(CountryCurrencyPickerActivity.this,
                        String.format("name: %s\nsymbol: %s", currency.getName(), currency.getName())
                        , Toast.LENGTH_SHORT).show();

                DialogFragment dialogFragment =
                        (DialogFragment) getSupportFragmentManager().findFragmentByTag(CountryCurrencyPicker.DIALOG_NAME);
                dialogFragment.dismiss();
            });

            pickerDialog.setDialogTitle(getString(R.string.currency_dialog_title));
            pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

        } else if (id == R.id.action_currency_countries) {
            getSupportActionBar().setTitle(R.string.menu_currency_countries);

            CountryCurrencyPicker pickerFragment = CountryCurrencyPicker.newInstance((CurrencyAndCountriesPickerListener) (currency, countries, countriesNames) -> Toast.makeText(CountryCurrencyPickerActivity.this,
                    String.format("name: %s\ncurrencySymbol: %s\ncountries: %s", currency.getName(), currency.getSymbol(), TextUtils.join(", ", countriesNames))
                    , Toast.LENGTH_SHORT).show());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, pickerFragment).commit();

        } else if (id == R.id.action_currency_countries_dialog) {
            CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance((CurrencyAndCountriesPickerListener) (currency, countries, countriesNames) -> {
                Toast.makeText(CountryCurrencyPickerActivity.this,
                        String.format("name: %s\ncurrencySymbol: %s\ncountries: %s", currency.getName(), currency.getSymbol(), TextUtils.join(", ", countriesNames))
                        , Toast.LENGTH_SHORT).show();

                DialogFragment dialogFragment =
                        (DialogFragment) getSupportFragmentManager().findFragmentByTag(CountryCurrencyPicker.DIALOG_NAME);
                dialogFragment.dismiss();
            });

            pickerDialog.setDialogTitle(getString(R.string.currency_countries_dialog_title));
            pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);
        }

        return super.onOptionsItemSelected(item);
    }
}
