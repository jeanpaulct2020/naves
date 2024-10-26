package com.jeancot.naves.util;

import com.jeancot.naves.model.MedioVisual;

public class Util {

    public static boolean isValidMedioVisual(String value) {
        for (MedioVisual medio : MedioVisual.values()) {
            if (medio.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
