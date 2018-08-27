package com.yyt.secondkill.validator;

import com.yyt.secondkill.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    public boolean isValid(String str, ConstraintValidatorContext context) {
        if (required) {
            return ValidatorUtil.isMobile(str);
        } else {
            if (StringUtils.isEmpty(str)) {
                return true;
            } else {
                return ValidatorUtil.isMobile(str);
            }
        }
    }

}
