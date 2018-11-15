package it.windtre.tremobilitycms.backend.data;

import it.windtre.tremobilitycms.backend.data.entity.Workspace;

import java.util.LinkedHashMap;
import java.util.List;

public class DashboardData {

	private DeliveryStats deliveryStats;
	private List<Number> deliveriesThisMonth;
	private List<Number> deliveriesThisYear;
	private Number[][] salesPerMonth;
	private LinkedHashMap<Workspace, Integer> productDeliveries;

	public DeliveryStats getDeliveryStats() {
		return deliveryStats;
	}

	public void setDeliveryStats(DeliveryStats deliveryStats) {
		this.deliveryStats = deliveryStats;
	}

	public List<Number> getDeliveriesThisMonth() {
		return deliveriesThisMonth;
	}

	public void setDeliveriesThisMonth(List<Number> deliveriesThisMonth) {
		this.deliveriesThisMonth = deliveriesThisMonth;
	}

	public List<Number> getDeliveriesThisYear() {
		return deliveriesThisYear;
	}

	public void setDeliveriesThisYear(List<Number> deliveriesThisYear) {
		this.deliveriesThisYear = deliveriesThisYear;
	}

	public void setSalesPerMonth(Number[][] salesPerMonth) {
		this.salesPerMonth = salesPerMonth;
	}

	public Number[] getSalesPerMonth(int i) {
		return salesPerMonth[i];
	}

	public LinkedHashMap<Workspace, Integer> getProductDeliveries() {
		return productDeliveries;
	}

	public void setProductDeliveries(LinkedHashMap<Workspace, Integer> productDeliveries) {
		this.productDeliveries = productDeliveries;
	}

}
