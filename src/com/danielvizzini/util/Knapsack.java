package com.danielvizzini.util;

import java.util.ArrayList;

/**
 * Extension of Stack that implements Knapsack algorithm on construction and allows total "weight" to be easily accessible.<br/>
 * Objects contained within Knapsack must implement the KnapsackItem interface.
 */
@SuppressWarnings("javadoc")
public class Knapsack<T extends KnapsackItem> extends ArrayList<T> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @return total weight of Knapsack
	 */
	public Integer getTotalWeight() {
		return this.totalWeight;
	}
	private Integer totalWeight = 0;

	/**
	 * Packs knapsack to maximize profits as specified in KnapsackItems<br/>
	 * KnapsackItems placed in the Knapsack are removed from the knapsackItems parameter.
	 * @param capacity capacity of knapsack (e.g. 150 for a 150 cubic-yard truck)
	 * @param knapsackItems list of items that implement the KnapsackItem interface to be packed into the knapsack (e.g. a List of Furniture objects)
	 */
	public Knapsack(Iterable<T> knapsackItems, int capacity) {
		pack(knapsackItems, capacity);
	}

	/**
	 * Packs knapsack to maximize profits as specified in KnapsackItems<br/>
	 * The knapsackItems parameter is left unchanged if the replenishItems boolean is true.
	 * @param capacity capacity of knapsack (e.g. 150 for a 150 cubic-yard truck)
	 * @param knapsackItems list of items that implement the KnapsackItem interface to be packed into the knapsack (e.g. a List of Furniture objects)
	 * @param replenishItems if true, knapsackItems parameter will remain unchanged, otherwise KnapsackItems placed in the Knapsack are removed from the knapsackItems parameter
	 */
	public Knapsack(Iterable<T> knapsackItems, int capacity, boolean replenishItems) {
		if (replenishItems) {			
			packAndReplenish(knapsackItems, capacity);			
		} else {
			pack(knapsackItems, capacity);			
		}
	}

	/**
	 * Packs knapsack where weights and profits are the same<br/>
	 * items and weights arrays are left in tack.
	 * @param <T>
	 * @param capacity capacity of knapsack (e.g. 150 for a 150 cubic-yard truck)
	 * @param items list of items packed into the knapsack (e.g. a List of Furniture objects)
	 * @param weights the weights, in the same units as capacity, of these objects.<br/>
	 * @return a boolean array of this is true if the corresponding item in items should be taken and false otherwise (e.g. a note on furniture stating "take" or trash)
	 */
	private boolean[] initialize(Iterable<T> knapsackItems, int capacity) {
		
		ArrayList<Double> profitsList = new ArrayList<Double>();
		ArrayList<Integer> weightsList = new ArrayList<Integer>();
		
		for (KnapsackItem knapsackItem : knapsackItems) {
			profitsList.add(knapsackItem.getProfit());
			weightsList.add(knapsackItem.getWeight());
		}
		
    	//number of items
		int numItems = profitsList.size();

    	//create parameters for private method
    	double[] profits = new double[numItems + 1];
    	int[] weights = new int[numItems + 1];
		
		int i = 0; //use iterator to save operations in non-indexed list, so i must instantiated outside loop
		for (Integer weight : weightsList) {
			profits[i + 1] = (double) weight;
			weights[i + 1] = weight;
			i++;
		}
		
		//see what items are taken from packed knapsack
		return packAndRemplenish(profits, weights, capacity);
				
	}
	
	/**
	 * internal method to pack knapsack while leaving knapsackItems parameter unchanged
	 * @param take a boolean array of this is true if the corresponding item in items should be taken and false otherwise (e.g. a note on furniture stating "take" or trash)
	 * @param knapsackItems list of items that implement the KnapsackItem interface to be packed into the knapsack (e.g. a List of Furniture objects)
	 */
	private void packAndReplenish(Iterable<T> knapsackItems, int capacity) {
		
		boolean[] take = initialize(knapsackItems, capacity);

		//populate itemsTaken
		int i = 0;//use iterator to save operations in non-indexed, so i must instantiated outside loop
		for (T knapsackItem : knapsackItems) {
			if (take[i]) {
				this.add(knapsackItem);
				this.totalWeight += knapsackItem.getWeight();
			}
			i++;
		}
	}
	
	/**
	 * internal method to pack knapsack while removing items packed from knapsackItems parameter
	 * @param take a boolean array of this is true if the corresponding item in items should be taken and false otherwise (e.g. a note on furniture stating "take" or trash)
	 * @param knapsackItems list of items that implement the KnapsackItem interface to be packed into the knapsack (e.g. a List of Furniture objects)
	 * @param rea boolean array of this is true if the corresponding item in items should be taken and false otherwise (e.g. a note on furniture stating "take" or trash)
	 */
	private void pack(Iterable<T> knapsackItems, int capacity) {

		boolean[] take = initialize(knapsackItems, capacity);

		//populate itemsTaken and mark the objects to be removed
		ArrayList<Integer> toBeRemoved = new ArrayList<Integer>();
		
		int i = 0;
		for (T knapsackItem : knapsackItems) {
			if (take[i]) {
				this.add(knapsackItem);
				this.totalWeight += knapsackItem.getWeight();
				toBeRemoved.add(i);
			}
			i++;
		}
		
		//remove items in knapsack from original arrays
		IterableUtil.removeIndices(knapsackItems, toBeRemoved);
		
	}
	
	/**
	 * Method modified from textbook that does the heavy lifting.<br/>
	 * Inputs and outputs are unsuitable for public consumption.<br/>
	 * Thank you Princeton: http://introcs.cs.princeton.edu/java/96optimization/Knapsack.java.html
	 */
    private boolean[] packAndRemplenish(double[] profits, int[] weights, int W) {
    	
    	int N = profits.length - 1;   // number of items
    	if (N != weights.length - 1) throw new IllegalArgumentException("weights and profits arrays must be of equal length");
    	
        // opt[n][w] = max profit of packing items 1..n with weight limit w
        // sol[n][w] = does opt solution to pack items 1..n with weight limit w include item n?
        double[][] opt = new double[N+1][W+1];
        boolean[][] sol = new boolean[N+1][W+1];

        //minimize iterations by dividing by greatest common divisor
        int gcd = MiscUtil.greatestCommonDivisor(weights);        
    	if (gcd > 0) W = gcd * (int) (W / gcd);
    	
        for (int n = 1; n <= N; n++) {
            for (int w = gcd; w <= W; w += gcd) {

                // don't take item n
                double option1 = opt[n-1][w];

                // take item n
                double option2 = Integer.MIN_VALUE;
                if (weights[n] <= w) {
                	option2 = profits[n] + opt[n-1][w-weights[n]];
                }

                // select better of two options
                opt[n][w] = Math.max(option1, option2);
                sol[n][w] = (option2 > option1);
            }
        }

        // determine which items to take
        boolean[] take = new boolean[N];
        for (int n = N, w = W; n > 0; n--) {
            if (sol[n][w]) { take[n-1] = true;  w = w - weights[n]; }
            else           { take[n-1] = false;                    }
        }
        
        return take;

    }
}