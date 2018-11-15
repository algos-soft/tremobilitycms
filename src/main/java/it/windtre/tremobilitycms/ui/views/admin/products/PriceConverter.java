package it.windtre.tremobilitycms.ui.views.admin.products;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import it.windtre.tremobilitycms.ui.utils.FormattingUtils;
import it.windtre.tremobilitycms.ui.dataproviders.DataProviderUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import static it.windtre.tremobilitycms.ui.dataproviders.DataProviderUtil.convertIfNotNull;

class PriceConverter implements Converter<String, Integer> {

	private final DecimalFormat df = FormattingUtils.getUiPriceFormatter();

	@Override
	public Result<Integer> convertToModel(String presentationValue, ValueContext valueContext) {
		try {
			return Result.ok((int) Math.round(df.parse(presentationValue).doubleValue() * 100));
		} catch (ParseException e) {
			return Result.error("Invalid value");
		}
	}

	@Override
	public String convertToPresentation(Integer modelValue, ValueContext valueContext) {
		return DataProviderUtil.convertIfNotNull(modelValue, i -> df.format(BigDecimal.valueOf(i, 2)), () -> "");
	}
}