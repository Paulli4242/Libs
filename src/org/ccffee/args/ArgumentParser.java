package org.ccffee.args;

import java.util.Arrays;
import java.util.function.*;

/**
 *
 * This class can be used to handle program arguments easier.
 *
 */
public class ArgumentParser {
	
	private Node[] nodes = new Node[0];

	/**
	 *
	 * Creates ArgumentParser.
	 *
	 * @param args arguments to parse
	 */
	public ArgumentParser(String[] args) {
		String current = "";
		Node currentV;
		for(String s : args) {
			if(s.startsWith("-")) {
				current = s.substring(1).toLowerCase();
				if(!isKey(current)) {
					nodes = Arrays.copyOfRange(nodes, 0, nodes.length+1);
					nodes[nodes.length-1]=new Node(current);
				}
			}else if(!current.isEmpty()) {
				if(getNode(current).getValue()!=null) {
					currentV = getNode(current);
					currentV.setValue(Arrays.copyOfRange(currentV.getValue(), 0, currentV.getValue().length+1));
					currentV.getValue()[currentV.getValue().length-1]=s;
				}else {
					getNode(current).setValue(new String[] {s});
				}
			}
		}
	}

	/**
	 *
	 * Checks if a key exists.
	 *
	 * @param key the checked key.
	 * @return true if the key exists, false otherwise.
	 */
	public boolean isKey(String key) {
		return getNode(key)!=null;
	}
	private Node getNode(String key) {
		for(Node node : nodes)if(node.getKey().equals(key))return node;
		return null;
	}

	/**
	 *
	 * Gets a string from a matching key.
	 *
	 * @param key the key where it gets the string.
	 * @return a string from a matching key, null if the key does not exist.
	 */
	public String getString(String key){
		String[] s = getStringArray(key);
		return s==null?null:s[0];
	}
	/**
	 *
	 * Gets a string from a matching key or a default value.
	 *
	 * @param key the key where it gets the string.
	 * @param def the default value returned if key does not exist.
	 * @return a string from a matching key, def if the key does not exist.
	 */
	public String getString(String key, String def){
		String s = getString(key);
		return s==null?def:s;
	}
	/**
	 *
	 * Gets a string from a matching key or a default value.
	 *
	 * @param key the key where it gets the string.
	 * @param def the default value returned if key does not exist.
	 * @return a string from a matching key, def if the key does not exist.
	 */
	public String getString(String key, Supplier<String> def){
		String s = getString(key);
		return s==null?def.get():s;
	}
	/**
	 *
	 * Gets a string array from a matching key.
	 *
	 * @param key the key where it gets the string array.
	 * @return a string array from a matching key or null if the key does not exist.
	 */
	public String[] getStringArray(String key) {
			Node node = getNode(key.toLowerCase());
			if(node!=null)return node.getValue();
			return null;
	}
	/**
	 *
	 * Gets a string array from a matching key or a default value.
	 *
	 * @param key the key where it gets the string array.
	 * @param def the default value returned if key does not exist.
	 * @return a string array from a matching key, def if the key does not exist.
	 */
	public String[] getStringArray(String key, String...def) {
		String[] s = getStringArray(key);
		return s==null?def:s;
	}
	/**
	 *
	 * Gets a string array from a matching key or a default value.
	 *
	 * @param key the key where it gets the string array.
	 * @param def the default value returned if key does not exist.
	 * @return a string array from a matching key, def if the key does not exist.
	 */
	public String[] getStringArray(String key, Supplier<String[]> def) {
		String[] s = getStringArray(key);
		return s==null?def.get():s;
	}
	/**
	 *
	 * Gets a double from a matching key.
	 *
	 * @param key the key where it gets the double.
	 * @return a double from a matching key, null if the key does not exist.
	 */
	public double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}
	/**
	 *
	 * Gets a double from a matching key or a default value.
	 *
	 * @param key the key where it gets the string.
	 * @param def the default value returned if key does not exist.
	 * @return a double from a matching key, def if the key does not exist.
	 */
	public double getDouble(String key, double def) {
		String s = getString(key);
		return s==null?def:Double.parseDouble(s);
	}
	/**
	 *
	 * Gets a double from a matching key or a default value.
	 *
	 * @param key the key where it gets the string.
	 * @param def the default value returned if key does not exist.
	 * @return a double from a matching key, def if the key does not exist.
	 */
	public double getDouble(String key, DoubleSupplier def) {
		String s = getString(key);
		return s==null?def.getAsDouble():Double.parseDouble(s);
	}
	/**
	 *
	 * Gets a int from a matching key.
	 *
	 * @param key the key where it gets the int.
	 * @return a int from a matching key, null if the key does not exist.
	 */
	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}
	/**
	 *
	 * Gets a int from a matching key or a default value.
	 *
	 * @param key the key where it gets the string.
	 * @param def the default value returned if key does not exist.
	 * @return a int from a matching key, def if the key does not exist.
	 */
	public int getInt(String key,int def) {
		String s = getString(key);
		return s==null?def:Integer.parseInt(s);
	}
	/**
	 *
	 * Gets a int from a matching key or a default value.
	 *
	 * @param key the key where it gets the string.
	 * @param def the default value returned if key does not exist.
	 * @return a int from a matching key, def if the key does not exist.
	 */
	public int getInt(String key, IntSupplier def) {
		String s = getString(key);
		return s==null?def.getAsInt():Integer.parseInt(s);
	}
	/**
	 *
	 * Gets a long from a matching key.
	 *
	 * @param key the key where it gets the long.
	 * @return a long from a matching key, null if the key does not exist.
	 */
	public long getLong(String key) {
			return Long.parseLong(getString(key));
	}
	/**
	 *
	 * Gets a long from a matching key or a default value.
	 *
	 * @param key the key where it gets the long.
	 * @param def the default value returned if key does not exist.
	 * @return a long from a matching key, def if the key does not exist.
	 *
	 */
	public long getLong(String key,long def) {
		String s = getString(key);
		return s==null?def:Long.parseLong(s);
	}
	/**
	 *
	 * Gets a long from a matching key or a default value.
	 *
	 * @param key the key where it gets the long.
	 * @param def the default value returned if key does not exist.
	 * @return a long from a matching key, def if the key does not exist.
	 *
	 */
	public long getLong(String key, LongSupplier def) {
		String s = getString(key);
		return s==null?def.getAsLong():Long.parseLong(s);
	}
	private class Node{
		
		String key;
		String[] value;
		
		public Node(String key) {
			this.key = key;
		}
		private void setValue(String[] value) {
			this.value = value;
		}
		public String[] getValue() {
			return value;
		}
		public String getKey() {
			return key;
		}
	}

	public <T> T getFirst(String[] keys, Function<String,T> get){
		T current = null;
		for(String k : keys)if((current = get.apply(k))!=null)break;
		return current;
	}
	public <T> T getFirst(String[] keys, Function<String,T> get, T def) {
		T current = getFirst(keys,get);
		return current==null?def:current;
	}
	public <T> T getFirst(String[] keys, Function<String,T> get, Supplier<T> def) {
		T current = getFirst(keys,get);
		return current==null?def.get():current;
	}
}
