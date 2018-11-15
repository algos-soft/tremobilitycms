package it.windtre.tremobilitycms.ui.views.admin.products;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

/**
 * Created by vcasari on 11/09/18.
 */
public class IntegerConverter implements Converter<String, Integer> {
    @Override
    public Result<Integer> convertToModel(String presentationValue, ValueContext valueContext) {
        try {
            return Result.ok(Integer.valueOf(presentationValue));
        } catch (Exception e) {
            return Result.error("Invalid value");
        }
    }

    @Override
    public String convertToPresentation(Integer modelValue, ValueContext valueContext) {
        if(modelValue != null)
            return String.valueOf(modelValue);
        else
            return "500";
    }
}