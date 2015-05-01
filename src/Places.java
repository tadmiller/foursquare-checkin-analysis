/***********************************************************************
/* @authors Katya Handler, Larisa Sidorovich, Robyn Silber, Tad Miller *
/***********************************************************************
/* A Places object contains a list of venues, or Place objects.
/* Originally, the Places object was only supposed to contain the
/* list of Place objects, however, it seemed practical to add
/* methods for sorting and searching efficiently through the list
/* of Place objects, so methods were added to do so.
/**********************************************************************/

import java.util.ArrayList;

public class Places
{
	ArrayList<Place> places;
	
	/**
	 * Constructor. Creates an ArrayList of all of our venues to analyze.
	 */
	public Places() // There is a built in method to do this with the Foursquare API, however, it gives a JSONObject error when ran.
	{
		// All of our venues.
		places = new ArrayList<Place>();
		places.add(new Place("4d94f61a7cfc1456720922f6", "El Centro")); //el centro
		places.add(new Place("4a469855f964a5202aa91fe3", "Founding Farmers")); //founding farmers
		places.add(new Place("4f7a3deee4b0747c653b4a52", "Farmer's Fisher Bakers")); //Farmers Fishers Bakers
		places.add(new Place("4a3d95e3f964a5207ba21fe3", "Bourbon Steaks")); //Bourbon steaks
		places.add(new Place("49d1b87af964a520955b1fe3", "Tonic")); //Tonic
		places.add(new Place("4e0cd9921f6e9300e4ea777c", "Circa at Foggy Bottom")); //Circa at Foggy Bottom
		places.add(new Place("4a416e1bf964a5204ba51fe3", "Café Tu-O-Tu")); //Café Tu-O-Tu
		places.add(new Place("4e17196a1495f6691bf85931", "Sweetgreen")); //sweetgreen
		places.add(new Place("4e239cb8628469a5742c71c6", "Roti Mediterranean Grill")); //Roti Mediterranean Grill
		places.add(new Place("4e83a5009adf2d8426cd2501", "Burger Tap and Shake")); //Burger Tap and Shake
		places.add(new Place("4e7fabb24690db1d41b6c283", "District Commons")); //District Commons
		places.add(new Place("4da5cbc80cb6d75e29ff4038", "Devon and Blakely")); //Devon and Blakely
		places.add(new Place("4a32d53bf964a5209f9a1fe3", "Circle Bistro")); //Circle Bistro
		places.add(new Place("5254376e11d2ba0578347300", "Chipotle")); //Chipotle
		places.add(new Place("40be6a00f964a520c7001fe3", "Foggy Bottom Pub")); //Foggy Bottom Pub
		places.add(new Place("4b0c108af964a520533623e3", "Notti Bianchi")); //Notti Bianchi
		places.add(new Place("4e440a03b3ad4263a203ac1e", "Bobby's Burger Place")); //Bobby’s Burger Place
		places.add(new Place("4adf1acaf964a520997721e3", "Birch and Barley")); //Birch and Barley
		places.add(new Place("4cf92916de096dcbcdf18c79", "Cities Restaurant and Lounge")); //Cities Restaurant and Lounge
		places.add(new Place("4dd596b2d4c05d5096bcb643", "Jack Rose Dining Saloon")); //Jack Rose Dining Saloon
		places.add(new Place("3fd66200f964a52075f11ee3", "Brass Monkey")); //Brass Monkey
		places.add(new Place("4c06fc128b4520a15b518697", "Eden")); //Eden
		places.add(new Place("4a5b53eaf964a52003bb1fe3", "Grand Central")); //Grand Central
		places.add(new Place("51004c0de4b08abe47e24225", "Griphon")); //Griphon
		places.add(new Place("503532358aca10f34944e063", "Huxley")); //Huxley
		places.add(new Place("4e76b8cbfa7605970194e367", "Boundary Stone")); //Boundary Stone
		places.add(new Place("52955610498e0ada34641be1", "Fainting Goat")); //Fainting Goat
		places.add(new Place("4dffe7a1e4cd609e49af579e", "The Codmother")); //The Codmother	
		places.add(new Place("50bfd553e4b0b88ccdb1dfc4", "Heist")); //Heist
		places.add(new Place("4ba21ba0f964a52033dc37e3", "Barcode")); //Barcode
		places.add(new Place("40b3de00f964a5201c001fe3", "Local 16")); //Local 16
		places.add(new Place("40b13b00f964a5206ff71ee3", "Eighteen Street Lounge")); //Eighteen Street Lounge
		
		sortByName(0, places.size() - 1);
		
	}
	
	/**
	 * Getter method, returns the list of venues (Place objects).
	 * 
	 * @return An ArrayList of Place objects.
	 */
	public ArrayList<Place> getPlaces()
	{
		return places;
	}
	
	/**
	 * This method uses Binary Search to search through our ArrayList of Place objects,
	 * and returns the Place object corresponding to the key we are searching for.
	 * 
	 * @param key The name of the Place Object we are searching for.
	 * @param min The minimum element in the ArrayList. Required for Binary Search.
	 * @param max The maximum element in the ArrayList. Required for Binary Search.
	 * @return The Place object corresponding to the key (venue name) we are searching for.
	 */
	public Place search(String key, int min, int max)
	{
		int mid = (min + max) / 2;
		
		if (getPlaces().get(mid).getName().equals(key))
			return getPlaces().get(mid);
		else if (min > max)
			return null;
		else
		{
			if (isGreater(key, getPlaces().get(mid).getName()))
				return search(key, min, mid - 1);
			else
				return search(key, mid + 1, max);
		}
	}
	
	/**
	 * This method uses Merge Sort to sort a list of Place objects by the number of check ins.
	 * 
	 * @param min The minimum index, used by Merge Sort.
	 * @param max The maximum index, used by Merge Sort.
	 */
	public void sortByCheckins(int min, int max)
	{
		if (min == max)
			return;
		else
		{
			int mid = (min + max) / 2;
			sortByCheckins(min, mid);
			sortByCheckins(mid + 1, max);
			
			Merge(min, mid + 1, max + 1);
		}
	}
	
	/**
	 * Merges the ArrayList of Places, by the number of check ins, from the start to end indices.
	 * 
	 * @param start The index of the left iterator.
	 * @param mid The index of the right iterator.
	 * @param end The end of the Array (or where we need to merge from).
	 */
	public void Merge(int start, int mid, int end)
	{
		ArrayList<Place> tempArr = new ArrayList<Place>();
		int i = start;
		int j = mid;
		int k = 0;
		
		while (i < mid && j < end)
		{
			if (getPlaces().get(i).getTotalCheckIns() > getPlaces().get(j).getTotalCheckIns())
			{
				tempArr.add(getPlaces().get(i));
				i++;
			}
			else
			{
				tempArr.add(getPlaces().get(j));
				j++;
			}
		}
		while (i < mid)
		{
			tempArr.add(getPlaces().get(i));
			i++;
		}
		while (j < end)
		{
			tempArr.add(getPlaces().get(j));
			j++;
		}
		
		while (start < end)
		{
			getPlaces().set(start, tempArr.get(k));
			start++;
			k++;
		}
	}
	
	/**
	 * This method uses Quick Sort to sort the ArrayList of Place objects by name.
	 * 
	 * @param min The minimum index to sort at.
	 * @param max The maximum index, or the end of where we are sorting to.
	 */
	public void sortByName(int min, int max)
	{
		if (max - min <= 0)
			return;
		else
		{
			int i = partition(min, max);
			sortByName(min, i - 1);
			sortByName(i + 1, max);
		}
	}

	/**
	 * This method returns true if the ASCII value of the first String is less than the second String.
	 * 
	 * @param a The first String.
	 * @param b The second String.
	 * @return True if a < b (which is kind of like a > b since a would be sorted first in the list).
	 */
	public boolean isGreater(String a, String b) // Returns true if the ASCII value of String a < b
	{	// (which would mean a would be sorted first since ASCII(a) = 97 where ASCII(b) = 98). 
		// ASCII source: http://www.asciitable.com/index/asciifull.gif
		int length = a.length();
		if (b.length() < length)
			length = b.length();
		
		for (int i = 0; i < length; i++) // Go through each character, try to find where the ASCII value of a < the ASCII value of b.
		{
			if (Integer.valueOf(a.charAt(i)) < Integer.valueOf(b.charAt(i)))
				return true;
			else if (Integer.valueOf(a.charAt(i)) != Integer.valueOf(b.charAt(i)))
				break;
		}
		
		return false;
	}
	
	/**
	 * The partition method for the Quick Sort algorithm.
	 * 
	 * @param min The left index.
	 * @param max The right index + 1
	 * @return The index of where our pivot is in the ArrayList.
	 */
	public int partition(int min, int max)
	{
		int left = min;
		int right = max - 1;
		Place pivot = getPlaces().get(max);
		
		while (true)
		{
			while (left < getPlaces().size() && isGreater(getPlaces().get(left).getName(), pivot.getName()))
				left++;
			while (right > 0 && isGreater(pivot.getName(), getPlaces().get(right).getName()))
				right--;

			if (left >= right)
				break;
			else
				swap(left, right);
		}
		swap(left, max);
		return left;
	}
	
	/**
	 * Swaps the Place objects at i and j in the ArrayList of Place objects.
	 * 
	 * @param i Element in the ArrayList of Place objects to swap.
	 * @param j Second element in the ArrayList of Place objects to swap.
	 */
	public void swap(int i, int j)
	{
		Place temp = getPlaces().get(i);
		getPlaces().set(i, getPlaces().get(j));
		getPlaces().set(j, temp);
	}
}
