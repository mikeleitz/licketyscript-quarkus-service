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
import org.apache.commons.lang3.tuple.Pair;

public class BashValidationFactory {

    public BashValidation createBashValidation(Integer id, List<Pair<String, String>> args) throws ValidationNotFoundException {
        BashValidation returnValue = null;

        Optional<ValidationEnum> validationEnumOptional = ValidationEnum.getById(id);

        if (validationEnumOptional.isPresent()) {
            ValidationEnum validationEnum = validationEnumOptional.get();
            returnValue = BashValidation.builder().id(id).name(validationEnum.getValidationName()).args(args).validationEnum(validationEnum).build();
        } else {
            throw new ValidationNotFoundException(String.format("Validation with id [%d] can't be found. Unable to create the requested code.", id));
        }

        return returnValue;
    }

}
