package net.asfun.template.filter;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.Filter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AbsFilterTest extends ZzzBase{

	
	@Before
	public void setUp() throws Exception {
		filter = new AbsFilter();
	}

	@Test
	public void testInt() throws CompilerException {
		Object res = filter.filter(20, compiler);
		assertEquals(20, res);
	}
	
	@Test
	public void testInteger() throws CompilerException {
		Object res = filter.filter(new Integer(-20), compiler);
		assertEquals(20, res);
	}
	
	@Test
	public void testFloat() throws CompilerException {
		Object res = filter.filter(new Double(-20.24), compiler, new String[]{"abc","edf"});
		assertEquals(20.24f, (Double)res, 0.01f);
	}
	
	@Test
	public void testLong() throws CompilerException {
		Object res = filter.filter(new Long(-0), compiler);
		assertEquals(0, res);
	}
	
	@Test
	public void testShort() throws CompilerException {
		Object res = filter.filter(new Short((short) -22222222), compiler);
		assertEquals((short)22222222, res);
	}
	
	@Test
	public void testByte() throws CompilerException {
		Object res = filter.filter(new Byte((byte) 222), compiler);
		assertEquals((byte)-222, res);
	}
	
	@Test
	public void testString() throws CompilerException {
		Object res = filter.filter("-215.5256", compiler);
		assertEquals(215.52559d, ((BigDecimal)res).doubleValue(), 0.0001d);
	}
	
	@Test
	public void testBigInt() throws CompilerException {
		Object res = filter.filter(BigInteger.valueOf(-1547898522234l), compiler);
		assertEquals(BigInteger.valueOf(1547898522234l), res);
	}
	
	@Test(expected=CompilerException.class)
	public void testString2() throws CompilerException {
		Object res = filter.filter("abcd", compiler);
		assertEquals(12, res);
	}
	
	@Test
	public void testOther() throws CompilerException {
		Filter af = new AbsFilter();
		Object res = filter.filter(af, compiler);
		assertEquals(af, res);
	}

	@Ignore
	public void testGetName() {
		assertEquals("abs", filter.getName());
	}
	
}
