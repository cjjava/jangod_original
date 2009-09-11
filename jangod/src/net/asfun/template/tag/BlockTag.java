package net.asfun.template.tag;

import java.util.ArrayList;
import java.util.List;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.JangodCompiler;
import net.asfun.template.compile.Node;
import net.asfun.template.compile.Tag;
import net.asfun.template.util.ListOrderedMap;
//import net.asfun.template.util.HelperStringTokenizer;

/**
 * {% block name %}
 * @author anysome
 *
 */

public class BlockTag implements Tag{
	
	private static final String BLOCKNAMES = "'BLK\"NAMES";

	@SuppressWarnings("unchecked")
	@Override
	public String compile(List<Node> carries, String helpers, JangodCompiler compiler)
			throws CompilerException {
		//init
//		String[] helper = new HelperStringTokenizer(helpers).allTokens();
//		if( helper.length != 1) {
//			throw new CompilerException("extends tag expects 1 helper >>> " + helper.length);
//		}
		String blockName = helpers;
		//check block name is unique
		List<String> blockNames = (List<String>) compiler.fetchRuntimeScope(BLOCKNAMES ,1);
		if ( blockNames == null ) {
			blockNames = new ArrayList<String>();
		}
		if ( blockNames.contains(blockName) ) {
			throw new CompilerException("Can't redefine the block with name >>> " + blockName);
		} else {
			blockNames.add(blockName);
			compiler.assignRuntimeScope(BLOCKNAMES, blockNames, 1);
		}
		Object isChild = compiler.fetchRuntimeScope(JangodCompiler.CHILD_FLAG, 1);
		if ( isChild != null ) {
			ListOrderedMap blockList = (ListOrderedMap) compiler.fetchEngineScope(JangodCompiler.BLOCK_LIST);
			//check block was defined in parent
			if ( ! blockList.containsKey(blockName) ) {
				throw new CompilerException("Dosen't define block in extends parent with name >>> " + blockName);
			}
			//cover parent block content with child's.
			blockList.put(blockName, getBlockContent(carries, compiler));
			return "";
		}
		Object isParent = compiler.fetchRuntimeScope(JangodCompiler.PARENT_FLAG, 1);
		if ( isParent != null) {
			//save block content to engine, and return identify
			ListOrderedMap blockList = (ListOrderedMap) compiler.fetchEngineScope(JangodCompiler.BLOCK_LIST);
			blockList.put(blockName, getBlockContent(carries, compiler));
			return JangodCompiler.SEMI_BLOCK + blockName;
		}
		return getBlockContent(carries, compiler);
	}
	
	private String getBlockContent(List<Node> carries, JangodCompiler compiler) throws CompilerException {
		StringBuffer sb = new StringBuffer();
		for(Node node : carries) {
			sb.append(node.render(compiler));
		}
		return sb.toString();
	}

	@Override
	public String getEndTagName() {
		return "endblock";
	}

	@Override
	public String getName() {
		return "block";
	}

}
