package org.geektimes.projects.user.validator.bean.validation;

import org.apache.commons.lang.StringUtils;
import org.geektimes.projects.user.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidAnnotationValidator implements ConstraintValidator<UserValid, User> {

    private int idRange;
    private int minLenOfPassword;
    private int maxLenOfPassword;
    private String phoneFormat;


    public void initialize(UserValid annotation) {
        this.idRange = annotation.idRange();
        this.minLenOfPassword = annotation.minLenOfPassword();
        this.maxLenOfPassword = annotation.maxLenOfPassword();
        this.phoneFormat = annotation.phoneFormat();
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        // 获取模板信息
//        context.getDefaultConstraintMessageTemplate();
        System.out.println("[UserValidAnnotationValidator] user = " + value);

        if (StringUtils.isBlank(value.getPassword()) ||
                StringUtils.isBlank(value.getPhoneNumber())) return false;

        if (value.getId() != null && value.getId() < idRange) return false;

        int len = value.getPassword().length();
        if (len < minLenOfPassword || len > maxLenOfPassword) return false;
        return value.getPhoneNumber().matches(this.phoneFormat);
    }
}
