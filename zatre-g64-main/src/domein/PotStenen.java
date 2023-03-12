package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PotStenen {
	List<Integer> stenen = new ArrayList<Integer>();

	public PotStenen() {
		setStenen();
	}

	/**
	 * Maakt de juiste stenen aan en shuffeled ze zodat ze in een random volgorde
	 * komen te staan
	 */
	private void setStenen() {
		for (int i = 1; i < 7; i++) {
			for (int j = 0; j < 20; j++) {
				stenen.add(i);
			}
		}
		stenen.add(1);
		Collections.shuffle(stenen);
//		stenen.add(6);
//		stenen.add(6);
//		stenen.add(6);
//		stenen.add(6);
//		stenen.add(6);
//		stenen.add(6);
//		stenen.add(6);
	}

	/**
	 * Returned de pot stenen als List van Integers
	 * 
	 * @return
	 */
	public List<Integer> getStenen() {
		return stenen;
	}

	/**
	 * Returned true als de lijst leeg is en false als de lijst niet leeg is
	 * 
	 * @return
	 */
	public boolean isLijstLeeg() {
		return stenen.isEmpty();
	}
}
