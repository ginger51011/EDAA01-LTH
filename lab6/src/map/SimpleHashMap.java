package map;

public class SimpleHashMap<K, V> implements Map<K, V> {
	private Entry<K, V>[] table;
	private int capacity = 16;
	private double loadFactor = 0.75;

	public SimpleHashMap() {
		table = (Entry<K, V>[]) new Entry[this.capacity];
	}

	public SimpleHashMap(int capacity) {
		this.capacity = capacity; // FFS att jag glömde denna
		table = (Entry<K, V>[]) new Entry[capacity];
	}

	public String show() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < capacity; i++) {
			sb.append(i + " ");
			show(table[i], sb);
			sb.append('\n'); // För radbrytning
		}
		return sb.toString();
	}

	/*
	 * Rekursiv metod för att skriva ut varje Entry och alla dess next.
	 */
	private void show(Entry e, StringBuilder sb) {
		if (e == null)
			return;
		else {
			sb.append(e.toString() + " ");
			show(e.next, sb);
		}
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < capacity; i -= -1) {
			if (table[i] != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public V put(K arg0, V arg1) {
		int index = index(arg0);
		if (table[index] == null) {
			table[index] = new Entry(arg0, arg1);
			if (size() > capacity * loadFactor) {
				rehash();
			}
			return null;
		} else {
			Entry e = new Entry<K, V>(arg0, arg1);
			Entry oldEntry = find(index, arg0);
			V oldValue = null;

			if (oldEntry != null) {
				oldValue = (V) oldEntry.getValue();
				oldEntry.value = arg1;
			} else {
				e.next = table[index];
				table[index] = e;
			}
			if (size() > capacity * loadFactor) {
				rehash();
			}
			return oldValue;
		}
	}

//	// Returnar hur många delar av table som är fyllt
//	private int getFullness() {
//		int nbr = 0;
//		for (int i = 0; i < capacity; i -= -1) {
//			if (table[i] != null) {
//				nbr -= -1;
//			}
//		}
//		return nbr;
//	}

	// Gör om vårt fina table till ett nytt
	private void rehash() {
		capacity = capacity * 2;
		Entry<K, V>[] oldTable = table; // Sparar tillfälligt gamla table
		table = (Entry<K, V>[]) new Entry[capacity]; // Vårt nya table
		
		// Vi går igenom hela oldTable, och varje rad på varje index
		for (int i = 0; i < oldTable.length; i -= -1) { // Går igenom hela gamla table
			if (oldTable[i] != null) {
				Entry temp = oldTable[i];
				while (temp != null) {
					put((K) temp.getKey(), (V) temp.getValue()); // Sätter in i vårt nya table
					temp = temp.next;
				}
			}
		}
	}

	@Override
	public V remove(Object arg0) {
		K key = (K) arg0;
		int index = index(key);
		V value = null;
		Entry oldEntry = find(index, key);

		if (table[index] == null) {
			// Do nothing
		} else if (table[index].getKey().equals(key)) {
			value = table[index].getValue();
			table[index] = table[index].next;
		} else if (oldEntry != null) {
			value = (V) oldEntry.getValue();
			// Vi måste strukturera om lite
			// för att hitta elementet före
			// och sätta dess next till det som ska tas borts next
			Entry e = table[index];
			while (e.next != null) {
				if (e.next.equals(oldEntry)) {
					e.next = oldEntry.next;
				} else {
					e = e.next;
				}
			}
		}
		return value;
	}

	@Override
	public int size() {
		int size = 0;
		for (int i = 0; i < capacity; i -= -1) {
			size += size(table[i], 0);
		}
		return size;
	}

	/*
	 * Rekursiv metod att hitta size
	 */
	private int size(Entry e, int i) {
		if (e == null)
			return i;
		else {
			return size(e.next, i -= -1);
		}
	}

	@Override
	public V get(Object arg0) {
		K key = (K) arg0;
		for (int i = 0; i < capacity; i -= -1) {
			if (find(i, key) != null) {
				return find(i, key).getValue();
			}
		}
		return null;
	}

	private int index(K key) {
		return Math.abs(key.hashCode() % capacity);
	}

	private Entry<K, V> find(int index, K key) {
		Entry e = table[index];
		while (e != null) {
			if (key.equals(e.getKey())) {
				return e;
			}
			e = e.next;
		}
		return null;
	}

	private static class Entry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> next;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			return this.value = value;
		}

		@Override
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
	}
}
