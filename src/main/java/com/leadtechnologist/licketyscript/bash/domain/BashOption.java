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

import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.leadtechnologist.licketyscript.base.application.ApplicationInput;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
@Data
@Builder
@Setter(AccessLevel.NONE)
@JsonDeserialize(using = BashOptionDeserializer.class)
public class BashOption implements ApplicationInput {
    public static BashOption VERBOSE = BashOption.builder().shortName('v').longName("verbose").longNameBashFriendly(BashOption.makeVariableNameAcceptableToBash("verbose")).optionHasValue(false).helpText("verbose operation").build();
    public static BashOption HELP = BashOption.builder().shortName('h').longName("help").longNameBashFriendly(BashOption.makeVariableNameAcceptableToBash("help")).optionHasValue(false).helpText("give this help list").build();

    private Integer id;
    private Character shortName;
    @NonNull private String longName;

    @Builder.Default private Boolean optionHasValue = true;
    private String defaultValue;
    @Builder.Default @NonNull private String helpText = "";
    @Singular private List<BashValidation> bashValidations;
    private String longNameBashFriendly;
    private Boolean inputRequired;      //true if the user must give some input for this option. false otherwise.

    @Override
    public String getVariableName() {
        return createVariableName(this);
    }

    @Override
    public String getIsSetVariableName() {
        return createVariableSetName(this);
    }

    /**
     * true if this bash input needs a value to be useful. This doesn't mean the the bash option
     * is required, it just means the option has a user input associated with it.
     *
     * Returns true if for example,
     *   -f ./my-file.txt
     *
     * This return value doesn't specify if the option is required for the script to run however.
     * That's done via a required validation.
     *
     * @return
     */
    public Boolean optionHasValue() {
        return optionHasValue;
    }

    public Boolean optionUsesValidation(ValidationEnum validationEnum) {
        Boolean returnValue = false;

        returnValue = getValidation(validationEnum).isPresent();

        return returnValue;
    }

    public Optional<BashValidation> getValidation(ValidationEnum validationEnum) {
        Optional<BashValidation> returnValue = Optional.empty();

        if (CollectionUtils.isNotEmpty(this.getBashValidations())) {
            Optional<BashValidation> foundValidationOption = this.getBashValidations().stream()
                    .filter(v -> v.getValidationEnum() == validationEnum)
                    .findFirst();

            returnValue = foundValidationOption;
        }

        return returnValue;
    }

    private String createVariableName(BashOption bashOption) {
        return makeVariableNameAcceptableToBash(bashOption.getLongName()) + "_ARG";
    }

    private String createVariableSetName(BashOption bashOption) {
        return makeVariableNameAcceptableToBash(bashOption.getLongName()) + "_OPTION_CHOSEN";
    }

    public static String makeVariableNameAcceptableToBash(String variableName) {
        String returnValue = null;

        if (Character.isDigit(variableName.charAt(0))) {
            variableName = "_" + variableName;
        }

        returnValue = variableName.toUpperCase();

        return returnValue;
    }
}
