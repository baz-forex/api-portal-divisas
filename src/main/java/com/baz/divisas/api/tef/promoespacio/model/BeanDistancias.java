package com.baz.divisas.api.tef.promoespacio.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanDistancias {

	private List<Double> coordinates;

	public List<Double> getCoordinates() {
		if(coordinates != null) {
			return new ArrayList<>(coordinates);
		}
		else {
			return Collections.emptyList();
		}
	}

	public void setCoordinates(List<Double> coordinates) {
		if (coordinates != null && !coordinates.isEmpty()) {
			this.coordinates = new ArrayList<>();
			this.coordinates.addAll(coordinates);
		}
	}
}
