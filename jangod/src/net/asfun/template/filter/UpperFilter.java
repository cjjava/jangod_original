package net.asfun.template.filter;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.Filter;

public class UpperFilter implements Filter{

	@Override
	public Object filter(Object object, String... arg) throws CompilerException {
		if ( object instanceof String ) {
			String value = object.toString();
			return value.toUpperCase();
		}
		return object;
	}

	@Override
	public String getName() {
		return "upper";
	}

}