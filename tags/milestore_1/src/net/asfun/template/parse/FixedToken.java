/**********************************************************************
Copyright (c) 2009 Asfun Net.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**********************************************************************/
package net.asfun.template.parse;

import static net.asfun.template.parse.ParserConstants.*;

public class FixedToken extends Token{

	public FixedToken(String image) throws ParserException{
		super(image);
	}
	
	@Override
	public int getType() {
		return TOKEN_FIXED;
	}
	
	/**
	 * set n is an integer and > 0
	 * change "{\[n]{" and "{\[n]!" and "{\[n]#" and "{\[n]%"
	 * to     "{\[n-1]{"   or "{\[n-1]!"   or "{\[n-1]#"   or "{\[n-1]%"
	 */
	@Override
	protected void parse() {
		content = image.replaceAll("\\{\\\\(\\\\*[\\{!#%])","{$1");
	}

	public boolean isBlank() {
		return content.trim().length()==0;
	}
	
	public String trim() {
		return content.trim();
	}
	
	public String output() {
		return content;
	}
	
	@Override
	public String toString() {
		if ( isBlank() ) {
			return "[OUT]";
		}
		return "[OUT]\r\n" + content;
	}
}
