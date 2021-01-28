/*
 *  Copyright (c) 2020, Michael Leitz
 *  <p/>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p/>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p/>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.leadtechnologist.licketyscript.bash.domain;

import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * This enum is responsible for tracking all the validations available in the generator.
 *
 * Each validation has an id value that the client needs to pass in for the appropriate input option. Every validation
 * also tracks its template that generates it.
 *
 * Every validation goes into the script as a utility function even if it's not used by any of the input options.
 *
 * In the resulting script, regex validations use a common regex validator function and their regex value is
 * defined as a constant. The also have a function that can be called with a value argument that validates the value.
 *
 * e.g. readonly UNSIGNED_INTEGER="^[0-9]{1,10}$"
 *
 * function validateUnsignedInteger() {
 *     // calls (delegates to) validateRegex with UNSIGNED_INTEGER and $1
 *     // returns the value from the validateRegex function.
 * }
 *
 * Regex validations are one type of validation. The other type uses a bash function, which
 * will be defined in the specified string template.
 *
 * @author leitz@mikeleitz.com
 */
@AllArgsConstructor
@Getter
public enum ValidationEnum {
    EMAIL(10, 13,false, "Email validation", "", "META-INF/resources/bash/lickety-script/validation/bash-validation-email-regex.stg", ".validateEmailValue"),
    DATE(6, 12,false, "Date validation dd/mm/yyy", "", "META-INF/resources/bash/lickety-script/validation/bash-validation-date-regex.stg", ".validateDateValue"),
    SIGNED_INTEGER(1, 2, false, "Signed integer", "", "META-INF/resources/bash/lickety-script/validation/bash-validation-integer-signed-regex.stg", ".validateSignedIntegerValue"),
    UNSIGNED_INTEGER(19, 3, false, "Unsigned integer", "", "META-INF/resources/bash/lickety-script/validation/bash-validation-integer-unsigned-regex.stg", ".validateUnsignedIntegerValue"),
    BOOLEAN(2, 4, false, "Boolean", "", "META-INF/resources/bash/lickety-script/validation/bash-validation-boolean-regex.stg", ".validateBooleanValue"),
    TIMESTAMP_ISO(7, 14,false, "Timestamp in ISO format", "", "META-INF/resources/bash/lickety-script/validation/bash-validation-timestamp-iso-regex.stg", ".validateTimestampIsoValue"),
    URL(9, 11,false, "URL", "Validates http or https urls.", "META-INF/resources/bash/lickety-script/validation/bash-validation-url-regex.stg", ".validateUrlValue"),
    IPV4(11, 10,false, "URL", "Validates an ipv4 address", "META-INF/resources/bash/lickety-script/validation/bash-validation-ipv4-regex.stg", ".validateIpv4Value"),
    VALUE_REQUIRED(20, 1, false, "Value required", "Value must be not null and not empty. Checks to make sure there is at least one non-whitespace anywhere in the input.", "META-INF/resources/bash/lickety-script/validation/bash-validation-required-regex.stg", ".validateRequiredValue"),
    ALPHANUMERIC(21, 5, false, "Alpha-numeric", "Alpha-numeric value. Any letter or number is accepted.", "META-INF/resources/bash/lickety-script/validation/bash-validation-alphanumeric-regex.stg", ".validateAlphanumericValue"),
    GREATER_THAN(14, 9,false, "Greater than", "Tests if an integer is greater than a specified value.", "META-INF/resources/bash/lickety-script/validation/bash-validation-integer-greater-than-logic.stg", ".validateIntegerGreaterThanEqual"),
    GREATER_THAN_EQUAL(15, 8,false, "Greater than or equal", "Tests if an integer is greater than or equal a specified value.", "META-INF/resources/bash/lickety-script/validation/bash-validation-integer-greater-than-equal-logic.stg", ".validateIntegerGreaterThanEqual"),
    LESS_THAN(16, 7, false, "Less than", "Tests if an integer is less than a specified value.", "META-INF/resources/bash/lickety-script/validation/bash-validation-integer-less-than-logic.stg", ".validateIntegerLessThan"),
    LESS_THAN_EQUAL(17, 6, false, "Less than or equal", "Tests if an integer is less than or equal a specified value.", "META-INF/resources/bash/lickety-script/validation/bash-validation-integer-less-than-equal-logic.stg", ".validateIntegerLessThanEqual"),
    CUSTOM_REGEX(13, 15,true, "Custom regex", "This is a regex value supplied by the user.", "META-INF/resources/bash/lickety-script/validation/bash-validation-custom-regex.stg", ""),
    STRING(4, 16,false, "String", "This value is a string.", "", ""),
    // TODO Phase 2
    //    IPV6(12, "Ipv6 address", "Validates an ipv6 address", "com/mikeleitz/sidekick/bash/validation/bash-validation-ipv6-regex.stg", "validateIpv6Value"),
    //    SIGNED_REAL(3, "Signed real` number", "", "com/mikeleitz/sidekick/bash/validation/bash-validation-real-signed-regex.stg", "validateSignedRealValue"),
    //    UNSIGNED_REAL(18, "Unsigned real number", "", "com/mikeleitz/sidekick/bash/validation/bash-validation-real-unsigned-regex.stg", "validateUnsignedRealValue"),
    //    TIMESTAMP_ONE_TRUE(99, "Timestamp in the one true format", "Will match the date and time down to either the seconds level of detail or the milliseconds level of detail. For example it matches both 2020-02-08 12:23:60,123 and 2020-02-08 12:23:60", "com/mikeleitz/sidekick/bash/validation/bash-validation-timestamp-one-true-regex.stg", "validateTimestampOneTrueValue"),
    //    CURRENCY(5, "Currency", "", "", ""),
    //    ENUMERATED(8, "Enumerated type", "", "", ""),
    ;

    private @NonNull Integer id;
    // This is the order the validations will be performed in. The lower the number the earlier the validation will be done.
    private @NonNull Integer order;
    private @NonNull @Accessors(fluent = true) Boolean requiresSpecialTemplateHandling;
    private @NonNull String validationName;
    private @NonNull String validationDescription;
    private @NonNull String stringTemplate;
    private @NonNull String functionName;

    public static Optional<ValidationEnum> getById(Integer id) {
        Optional<ValidationEnum> returnValue = null;

        returnValue = Arrays.stream(ValidationEnum.values()).filter(r -> r.getId() == id).findFirst();

        return returnValue;
    }
}
