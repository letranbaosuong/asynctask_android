package com.eztech.search;

import java.util.Locale;

class Formatted {
    public static String getFormatted(int input) {
        StringBuilder formatted_value = new StringBuilder();
        boolean isNavigate = input < 0;
        input = Math.abs(input);
        while (input > 999) {
            int du = input % 1000;
            input = input / 1000;
            formatted_value.insert(0, String.format(Locale.getDefault(), ".%,03d", du));
        }
        if(isNavigate){
            formatted_value.insert(0, String.format(Locale.getDefault(), "-%,d", input));
        } else {
            formatted_value.insert(0, String.format(Locale.getDefault(), "%,d", input));
        }
        return String.format(Locale.getDefault(), "%s", formatted_value.toString());
    }

    public static Integer getSplit (String input) {
        StringBuilder output = new StringBuilder();
        if(input.contains(".")) {
            String [] inputSplit = input.split("\\.");
            for (String s : inputSplit) {
                output.append(s);
            }
            return Integer.parseInt(output.toString());
        }
        else
            return 0;
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }
}
