package xyz.dc_stats.args;

import java.util.Arrays;

public class ArgumentParser {
	
	private Node[] nodes = new Node[0];
	public ArgumentParser(String[] args) {
		String current = "";
		Node currentV;
		for(String s : args) {
			if(s.startsWith("--")) {
				current = s.substring(2).toLowerCase();
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
	public boolean isKey(String key) {
		return getNode(key)!=null;
	}
	private Node getNode(String key) {
		for(Node node : nodes)if(node.getKey().equals(key))return node;
		return null;
	}
	public String getString(String key){
		String[] s = getStringArray(key);
		return s==null?null:s[0];
		
	}

	public String getString(String key, String def){
		String s = getString(key);
		return s==null?def:s;
	}
	public String[] getStringArray(String key) {
			Node node = getNode(key.toLowerCase());
			if(node!=null)return node.getValue();
			return null;
	}
	public String[] getStringArray(String key, String...def) {
		String[] s = getStringArray(key);
		return s==null?def:s;
	}
	public double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}
	public double getDouble(String key, double def) {
		String s = getString(key);
		return s==null?def:Double.parseDouble(s);
	}
	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}
	public int getInt(String key,int def) {
		String s = getString(key);
		return s==null?def:Integer.parseInt(s);
	}
	public long getLong(String key) {
			return Long.parseLong(getString(key));
	}
	public long getLong(String key,long def) {
		String s = getString(key);
		return s==null?def:Long.parseLong(s);
	}
	public class Node{
		
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
}
