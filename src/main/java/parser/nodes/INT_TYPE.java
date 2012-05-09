package parser.nodes;

import semantic.analysis.SymbolTable;


public class INT_TYPE extends Terminal {

	public INT_TYPE(String name) {
		super(name);
	}

	@Override
	public void run(SymbolTable table) {
		addAttribute("type");
		setAttribute("type", "int");
	}
}
