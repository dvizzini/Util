package com.danielvizzini.util;

class Furniture implements KnapsackItem {
	
	private int volume;

	public Integer getWeight() {
		return this.volume;
	}
	
	public Double getProfit() {
		return Double.valueOf((double) this.volume);
	}
	
	Furniture(int volume) {
		this.volume = volume;
	}
	
}

