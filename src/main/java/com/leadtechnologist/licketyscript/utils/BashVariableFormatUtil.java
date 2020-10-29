package com.leadtechnologist.licketyscript.utils;

import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.text.CaseUtils;

/**
 * @author leitz@mikeleitz.com
 */
public class BashVariableFormatUtil {
    public static String formatBashVariableNameCamelCase(String variableName) {
        return formatBashVariableNameCamelCase(variableName, false);
    }

    public static String formatBashVariableNameCamelCase(String variableName, Boolean capitalizeFirstLetter) {
        String returnValue;

        if (StringUtils.isNotBlank(variableName)) {
            returnValue = CaseUtils.toCamelCase(variableName, capitalizeFirstLetter);
        } else if (variableName == null) {
            returnValue = "";
        } else {
            returnValue = variableName;
        }

        return returnValue;
    }
}
