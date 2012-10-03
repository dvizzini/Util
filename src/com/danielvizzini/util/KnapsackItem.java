package com.danielvizzini.util;

/**
 * Interface to standardize how pertinent information is ascertained to solve Knapsack problems
 */
public interface KnapsackItem {
	
	/**
	 * @return the profit of the knapsack item (e.g. the value of a jug of olive oil)
	 */
	public abstract Double getProfit();

	/**
	 * @return the weight of the knapsack item (e.g. the weight of a jug of olive oil)
	 */
	public abstract Integer getWeight();

}
