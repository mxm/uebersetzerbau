package parser.nodes;

import semantic.analysis.SymbolTable;


public class BOOL_TYPE extends Terminal {

	public BOOL_TYPE(String name) {
		super(name);
	}

	@Override
	public void run(SymbolTable table) {
		addAttribute("type");
		setAttribute("type", "boolean");
	}
}
