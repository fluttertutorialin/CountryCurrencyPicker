package com.jdkgroup.custom.countrycurrencypicker;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import java.text.Normalizer;

public class Helper {

    @NonNull
    @DrawableRes
    public static Integer getFlagDrawableId(String code, Context context) {
        String drawableName = "flag_" + code.toLowerCase();
        return context.getResources()
                .getIdentifier(drawableName, "drawable", context.getPackageName());
    }

    public static String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}
