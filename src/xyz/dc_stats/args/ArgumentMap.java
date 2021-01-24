package xyz.dc_stats.args;

import java.util.Arrays;

public class ArgumentMap {
	
	private Node[] nodes = new Node[0];
	public ArgumentMap(String[] args) {
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
	public ArgumentMap(String[] args, String[] defaults) {
		this(args);
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
				if(getNode(current).getDefault()!=null) {
					currentV = getNode(current);
					currentV.setDefault(Arrays.copyOfRange(currentV.getDefault(), 0, currentV.getDefault().length+1));
					currentV.getDefault()[currentV.getDefault().length-1]=s;
				}else {
					getNode(current).setDefault(new String[] {s});
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
		return s==null?null:getStringArray(key)[0];
		
	}
	public String[] getStringArray(String key) {
			Node node = getNode(key.toLowerCase());
			if(node!=null && node.getValueOrDefault() !=null)return node.getValueOrDefault();
			return null;
	}
	public double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}
	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}
	public long getLong(String key) {
			return Long.parseLong(getString(key));
	}
	public class Node{
		
		String key;
		String[] value;
		String[] defaults;
		
		public Node(String key) {
			this.key = key;
		}
		private void setValue(String[] value) {
			this.value = value;
		}
		public String[] getValue() {
			return value;
		}
		private void setDefault(String[] value) {
			this.defaults = value;
		}
		public String[] getDefault() {
			return defaults;
		}
		public String getKey() {
			return key;
		}
		public String[] getValueOrDefault(){
			return value==null&&defaults!=null?defaults:value;
		}
	}
}
