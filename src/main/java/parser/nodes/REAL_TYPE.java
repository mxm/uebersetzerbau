package parser.nodes;

import semantic.analysis.SymbolTable;


public class REAL_TYPE extends Terminal {

	public REAL_TYPE(String name) {
		super(name);
	}

	@Override
	public void run(SymbolTable table) {
		addAttribute("type");
		setAttribute("type", "double");
	}
}
