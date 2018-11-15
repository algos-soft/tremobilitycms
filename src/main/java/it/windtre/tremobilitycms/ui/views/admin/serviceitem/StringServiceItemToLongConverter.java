package it.windtre.tremobilitycms.ui.views.admin.serviceitem;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class StringServiceItemToLongConverter implements Converter<String, Long> {

    @Override
    public Result<Long> convertToModel(String presentationValue, ValueContext valueContext) {
        try {
            //String[] arr = presentationValue.split("_");
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
