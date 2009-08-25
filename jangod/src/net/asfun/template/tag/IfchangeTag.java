package net.asfun.template.tag;

import java.util.List;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.JangodCompiler;
import net.asfun.template.compile.Node;
import net.asfun.template.compile.Tag;

public class IfchangeTag implements Tag{
	
	private static final String LASTKEY = "'IF\"CHG";
	

	@Override
	public String compile(List<Node> carries, String helpers, JangodCompiler compiler)
			throws CompilerException {
		boolean isChange = true;
		Object older = compiler.fetchRuntimeScope(LASTKEY);
		String var = helpers;
		Object test = compiler.resolveVariable(var);
		compiler.assignRuntimeScope(LASTKEY, test);
		if ( older == null ) {
			if ( test == null ) {
				isChange = false;
			}
		} else if ( older.equals(test) ) {
			isChange = false;
		}
		if ( isChange ) {
			StringBuffer sb = new StringBuffer();
			for(Node node : carries) {
				sb.append(node.render(compiler));
			}
			return sb.toString();
		}
		return "";
	}

	@Override
	public String getEndTagName() {
		return "endif";
	}

	@Override
	public String getName() {
		return "ifchange";
	}

}