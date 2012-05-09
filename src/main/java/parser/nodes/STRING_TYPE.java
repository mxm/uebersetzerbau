package parser.nodes;

import semantic.analysis.SymbolTable;


public class STRING_TYPE extends Terminal {

	public STRING_TYPE(String name) {
		super(name);
	}

	@Override
	public void run(SymbolTable table) {
		addAttribute("type");
		setAttribute("type", "String");
	}
}
