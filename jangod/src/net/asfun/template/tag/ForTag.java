package net.asfun.template.tag;

import java.util.List;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.JangodCompiler;
import net.asfun.template.compile.Tag;
import net.asfun.template.util.HelperStringTokenizer;
import net.asfun.template.util.ObjectIterator;
import net.asfun.template.compile.Node;

/**
 * {% for a in b %}		{% for a in b reversed%}
 * @author fangchq
 *
 */
public class ForTag implements Tag {

	private String item;
	private String items;
	private boolean isReverse = false;

	@Override
	public String compile(List<Node> carries, JangodCompiler compiler) throws CompilerException {
		List<Object> it = ObjectIterator.toList(compiler.resolveVariable(items), isReverse);
//		if ( it.size() == 0 ) {
//			return "";
//		}
		int level = compiler.getLevel() + 1;
		ForLoop loop = (ForLoop) compiler.fetchRuntimeScope("loop", level);
		if ( loop == null ) {
			loop = new ForLoop(it.size());
			compiler.assignRuntimeScope("loop", loop, level);
		}
		StringBuffer buff = new StringBuffer();
		for(Object obj : it) {
			//set item variable
			compiler.assignRuntimeScope(item, obj, level);
			for(Node node : carries) {
				buff.append(node.render(compiler));
			}
			//change loop variable
			loop.next();
		}
		return buff.toString();
	}

	/**
	 * @param helpers 
	 * 		like  for [ cat in cats ]
	 * @throws CompilerException 
	 */
	@Override
	public void initialize(String helpers) throws CompilerException {
		String[] helper = new HelperStringTokenizer(helpers).allTokens();
		switch(helper.length) {
			case 4 :
				isReverse = true;
			case 3 :
				item = helper[0];
				items = helper[2];
				break;
			default :
				throw new CompilerException("for tag expects 3 or 4 helpers:" + helpers);
		}
	}

	@Override
	public String getEndTagName() {
		return "endfor";
	}

	@Override
	public String getTagName() {
		return "for";
	}

}
