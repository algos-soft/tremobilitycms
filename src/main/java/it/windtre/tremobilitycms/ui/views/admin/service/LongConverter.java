package it.windtre.tremobilitycms.ui.views.admin.service;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class LongConverter implements Converter<String, Long> {

    @Override
    public Result<Long> convertToModel(String presentationValue, ValueContext valueContext) {
        try {
            return Result.ok(Long.valueOf(presentationValue));
        } catch (Exception e) {
            return Result.error("Invalid value");
        }
    }

    @Override
    public String convertToPresentation(Long modelValue, ValueContext valueContext) {
        if(modelValue != null)
            return String.valueOf(modelValue);
        else
            return "";
    }

}
