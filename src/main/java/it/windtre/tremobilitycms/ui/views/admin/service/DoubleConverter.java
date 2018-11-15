package it.windtre.tremobilitycms.ui.views.admin.service;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class DoubleConverter implements Converter<String, Double> {

    @Override
    public Result<Double> convertToModel(String presentationValue, ValueContext valueContext) {
        try {
            return Result.ok(Double.valueOf(presentationValue));
        } catch (Exception e) {
            return Result.error("Invalid value");
        }
    }

    @Override
    public String convertToPresentation(Double modelValue, ValueContext valueContext) {
        if(modelValue != null)
            return String.valueOf(modelValue);
        else
            return "500";
    }

}
