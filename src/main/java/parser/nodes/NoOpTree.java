package parser.nodes;

import semantic.analysis.SymbolTable;

public class NoOpTree extends Tree {
	
	public NoOpTree(String name) {
		super(name);
	}

	@Override
	public void run(SymbolTable table) {
	}
}
