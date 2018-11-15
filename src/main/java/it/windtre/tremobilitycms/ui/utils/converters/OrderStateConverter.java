package it.windtre.tremobilitycms.ui.utils.converters;

import com.vaadin.flow.templatemodel.ModelEncoder;
import it.windtre.tremobilitycms.backend.data.OrderState;
import it.windtre.tremobilitycms.ui.dataproviders.DataProviderUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static it.windtre.tremobilitycms.ui.dataproviders.DataProviderUtil.convertIfNotNull;

public class OrderStateConverter implements ModelEncoder<OrderState, String> {

	private Map<String, OrderState> values;

	public OrderStateConverter() {
		values = Arrays.stream(OrderState.values())
				.collect(Collectors.toMap(OrderState::toString, Function.identity()));
	}

	@Override
	public OrderState decode(String presentationValue) {
		return DataProviderUtil.convertIfNotNull(presentationValue, values::get);
	}

	@Override
	public String encode(OrderState modelValue) {
		return DataProviderUtil.convertIfNotNull(modelValue, OrderState::toString);
	}

}
