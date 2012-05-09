package semantic.analysis;

import java.util.HashMap;

import lombok.Getter;

/**
 * @author Christian Cikryt
 */
public class SymbolTable {
	
	@Getter
	private SymbolTable parent; 

	private HashMap<String, Object> hashMap;

	public SymbolTable() {
		hashMap = new HashMap<String, Object>();
	}
	
	public SymbolTable(SymbolTable parent){
		this();
		this.parent = parent;
	}

	/**
	 * Updates the entry associated with name. If it is not contained yet it
	 * gets added, otherwise the current value will be overridden.
	 * 
	 * @param name
	 * @param entry
	 */
	public void updateEntry(String name, Object value) throws IllegalStateException {
		if (!hashMap.containsKey(name))
			throw new IllegalStateException("No such symbol: " + name);
		
		hashMap.put(name, value);
	}
	
	public void insertEntry(String name, Object value) throws IllegalStateException {
		if (hashMap.containsKey(name))
			throw new IllegalStateException("Symbol already defined: " + name);
		
		hashMap.put(name, value);
	}

	/**
	 * Find value for a specific key
	 * @param name Key
	 * @return Value, may be null
	 */
	public Object lookup(String name) {
		return hashMap.get(name);
	}
}
