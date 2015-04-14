package com.zdw.tools.util;

import java.text.DecimalFormat;

public class Utils {

    public static String format(int value) {
		return new DecimalFormat("00").format(value);
	}
}
