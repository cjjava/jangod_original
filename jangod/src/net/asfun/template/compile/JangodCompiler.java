package net.asfun.template.compile;

import java.util.List;

import javax.script.ScriptContext;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.ListOrderedMap;

import static net.asfun.template.util.logging.JangodLogger;
import net.asfun.template.Configuration;
import net.asfun.template.bin.FloorBindings;
import net.asfun.template.parse.JangodParser;
import net.asfun.template.util.Variable;

public class JangodCompiler {
	
	public static final String CHILD_FLAG = "'IS\"CHILD";
	public static final String PARENT_FLAG = "'IS\"PARENT";
	public static final String INSERT_FLAG = "'IS\"INSERT";
	public static final String SEMI_RENDER = "'SEMI\"FORMAL";
	public static final String BLOCK_LIST = "'BLK\"LIST";
	public static final String SEMI_BLOCK = "<K2C9OL7B>";
	
	private int level = 1;
	private FloorBindings runtime;
	private ScriptContext context;
	private Configuration config;
	
	public JangodCompiler(ScriptContext scontext) {
		context = scontext;
		runtime = new FloorBindings();
		initialize();
	}
	
	private JangodCompiler() {}
	
	private void initialize() {
		config = (Configuration) context.getAttribute(Configuration.CONFIG_VAR, ScriptContext.GLOBAL_SCOPE);
		if ( config == null ) {
			config = Configuration.getDefaultConfig();
		}
	}
	
	public Configuration getConfig() {
		return config;
	}
	
	public JangodCompiler copy() {
		JangodCompiler compiler = new JangodCompiler();
		compiler.context = context;
		compiler.runtime = runtime.copy();
		compiler.config = config;
		return compiler;
	}
	
	public String render(JangodParser parser) throws CompilerException {
		List<Node> nodes = NodeList.makeList(parser, null, 1);
		StringBuffer buff = new StringBuffer();
		for(Node node : nodes) {
			buff.append(node.render(this));
		}
		if ( runtime.get(CHILD_FLAG, 1) != null && 
				runtime.get(INSERT_FLAG, 1) == null) {
			StringBuilder sb = new StringBuilder(context.getAttribute(SEMI_RENDER).toString());
			//replace the block identify with block content
			ListOrderedMap blockList = (ListOrderedMap) fetchEngineScope(BLOCK_LIST);
			MapIterator mi = blockList.mapIterator();
			int index;
			String replace;
			while( mi.hasNext() ) {
				mi.next();
				replace = SEMI_BLOCK + mi.getKey();
				while ( (index = sb.indexOf(replace)) > 0 ) {
					sb.delete(index, index + replace.length());
					sb.insert(index, mi.getValue());
				}
			}
			return sb.toString();
		}
		return buff.toString();
	}

	public Object retraceVariable(String variable) {
		Variable var = new Variable(variable);
		String varName = var.getName();
		//find from runtime(tree scope) > engine > global
		Object obj = runtime.get(varName, level);
		int lvl = level;
		while( obj == null && lvl > 1) {
			obj = runtime.get(varName, --lvl);
		}
		if ( obj == null ) {
			obj = context.getAttribute(varName);
		}
		if ( obj == null ) {
			if( "now".equals(variable) ) {
				return new java.util.Date();
			}
		}
		if ( obj != null ) {
			obj = var.resolve(obj);
		} else {
			JangodLogger.fine("Can't resolve variable >>> " + variable);
		}
		return obj;
	}
	
	public String resolveString(String variable) {
		if ( variable.startsWith("\"") || variable.startsWith("'") ) {
			return variable.substring(1, variable.length()-1);
		} else {
			Object val = retraceVariable(variable);
			if ( val == null ) return variable;
			return val.toString();
		}	
	}
	
	public Object resolveObject(String variable) {
		if ( variable.startsWith("\"") || variable.startsWith("'") ) {
			return variable.substring(1, variable.length()-1);
		} else {
			Object val = retraceVariable(variable);
			if ( val == null ) return variable;
			return val;
		}	
	}
	
	/**
	 * save variable to runtime tree scope space
	 * @param name
	 * @param item
	 */
	public void assignRuntimeScope(String name, Object item) {
		runtime.put(name, item, level);
	}
	
	public void assignRuntimeScope(String name, Object item, int level) {
		runtime.put(name, item, level);
	}
	
	public Object fetchRuntimeScope(String name, int level) {
		return runtime.get(name, level);
	}
	
	public Object fetchRuntimeScope(String name) {
		return runtime.get(name, level);
	}

	public void setLevel(int lvl) {
		level = lvl;
	}

	public Object fetchGlobalScope(String name) {
		return context.getAttribute(name, ScriptContext.GLOBAL_SCOPE);
	}

	public Object fetchEngineScope(String name) {
		return context.getAttribute(name, ScriptContext.ENGINE_SCOPE);
	}
	
	public void assignEngineScope(String name, Object value) {
		context.setAttribute(name, value, ScriptContext.ENGINE_SCOPE);
	}

	public int getLevel() {
		return level;
	}
}
