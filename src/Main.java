import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	public TreeMap<Long, ArrayList<Long>> disMap = new TreeMap<>();
	public TreeMap<Long, Long> clickNumbers = new TreeMap<>();
	public String newTitle;
	public int kMostSimilar;

	public static void main(String[] args) {
		Main main = new Main();
		main.getSystemIn();

		JsonReader jr = new JsonReader();

		String urlAllAds = "https://devakademi.sahibinden.com/api/ads/findAll";
		main.getAds(jr, main.newTitle, urlAllAds);
		TreeMap<Long, ArrayList<Long>> simMap = main.disMap;
		TreeMap<Long, Long> clickNumbers = main.clickNumbers;
		String urlAllClickStats = "https://devakademi.sahibinden.com/api/stats/findAllByEventType?eventType=CLICK";
		main.getAllClickNumberofAllAdds(jr, urlAllClickStats);

		main.estimateClickNumber(main.kMostSimilar);

	}

	private void getSystemIn() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter The Encoded Title : ");
		newTitle = sc.nextLine();
		// String newTitle = "dq044dc 1b6b3066 22abjkej4 1lq8a8qf07";
		System.out.println("Enter KNN(nearest neighbor) number : ");
		kMostSimilar = Integer.valueOf(sc.nextLine());
		// int kMostSimilar = 2;
		sc.close();

	}

	public void getAds(JsonReader jr, String newTitle, String url) {

		JSONArray js;
		try {
			js = getJSONArray(url, jr);

			int s = js.length();
			// System.out.println(s);
			for (int i = 0; i < s; i++) {
				JSONObject adv = js.getJSONObject(i);
				long sim = compare(adv.getString("title"), newTitle);
				addToMap(sim, Integer.valueOf(adv.get("id").toString()));

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void addToMap(long sim, long id) {
		clickNumbers.put(id, (long) 0);
		if (disMap.containsKey(sim)) {
			disMap.get(sim).add(id);
		} else {
			// System.out.println(id);

			ArrayList<Long> idArray = new ArrayList<Long>();
			idArray.add(id);
			disMap.put(sim, idArray);
		}
	}

	private long compare(String adTitle, String newTitle) {
		return LevenshteinDistance.computeLevenshteinDistance(adTitle, newTitle);
	}

	private JSONArray getJSONArray(String url, JsonReader jr) {
		JSONArray js = null;
		try {
			js = jr.readJsonFromUrl(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return js;
	}

	public void getAllClickNumberofAllAdds(JsonReader jr, String url) {
		JSONArray js;
		try {
			js = getJSONArray(url, jr);
			int s = js.length();
			for (int i = 0; i < s; i++) {
				JSONObject adv = js.getJSONObject(i);

				long id = Long.valueOf(adv.get("adId").toString());
				// System.out.println(id);
				long clickNumber = clickNumbers.get(id);
				clickNumbers.put(id, ++clickNumber);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/*
	 * To reach the given "k" number visit arrays from head to rear
	 * Divide the result array w as 2 halves, and get average number of each part
	 */
	private void estimateClickNumber(int kMostSimilar) {
		ArrayList<Long> mostSimilarIds = new ArrayList<>();
		double halfd = kMostSimilar / 2.0;
		long half1 = (long) Math.ceil(halfd);
		long half2 = kMostSimilar - half1;
		long click1 = 0;
		long click2 = 0;
		int filled = 0;
		int i = 0;

		for (long dis : disMap.keySet()) {
			ArrayList<Long> idArr = disMap.get(dis);

			for (long id : idArr) {
				if (filled < kMostSimilar) {

					mostSimilarIds.add(id);

					filled++;
					if (i < half1) {
						click1 += clickNumbers.get(id);
					} else {

						click2 += clickNumbers.get(id);
					}

				}
				i++;
			}

		}
		System.out.println("Estimated Click Number :");
		if (half2 == 0) {
			System.out.println(click1);
		} else {
			long appr1 = Math.round(click1 / half1);
			long appr2 = Math.round(click2 / half2);
			if (appr1 < appr2) {
				System.out.println(appr1 + " - " + appr2);
			} else {
				System.out.println(appr2 + " - " + appr1);
			}

		}
		System.out.println(mostSimilarIds.toString());

	}

}
