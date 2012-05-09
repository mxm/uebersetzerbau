package parser.nodes;

import semantic.analysis.SymbolTable;


public class assign extends Tree {

	public assign(String name) {
		super(name);
	}

	@Override
	public void run(SymbolTable table) {
		printTree();
		
	}
}
