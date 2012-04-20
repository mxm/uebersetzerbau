package parser;

import java.util.ArrayList;

public class NonTerminal implements SyntaxTree {
	
	private ArrayList<SyntaxTree> children = new ArrayList<SyntaxTree>();
	private String name;
	private ArrayList<Attribute> attributes;
	
	/**
	 * Creates a new empty node for non-terminals.
	 * @param name
	 */
	public NonTerminal(String name){
		this.name = name;
		attributes = new ArrayList<Attribute>();
	}	

	@Override
	public void addTree(SyntaxTree tree) {
		children.add(tree);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getChildrenCount() {
		return children.size();
	}

	@Override
	public SyntaxTree getChildren(int i) {
		return children.get(i);
	}

	@Override
	public void removeTree(int i) {
		children.remove(i);
	}

	@Override
	public Attribute getAttribute(String name) {
		for(Attribute attr : attributes){
			if(attr.getName().equals(name)){
				return attr;
			}
		}
		return null;
	}

	@Override
	public boolean setAttribute(String name, String value) {
		for(Attribute attr : attributes){
			if(attr.getName().equals(name)){
				attr.setValue(value);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addAttribute(String name) {
		if(getAttribute(name) == null){
			attributes.add(new Attribute(name));
			return true;
		} 
		return false;
	}
	
}