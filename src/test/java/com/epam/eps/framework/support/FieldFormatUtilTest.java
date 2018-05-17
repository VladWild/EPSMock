package com.epam.eps.framework.support;

import com.epam.eps.framework.core.Cell;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FieldFormatUtilTest {
	@Test
	public void getFieldTest() {
		final String fieldString = ""
				+ "{|X| - }"
				+ "{|X||X|}";
		final Cell[][] actual = {
				{new Cell(0,0), null},
				{new Cell(0,1), new Cell(1,1)}};
		assertArrayEquals(FieldFormatUtil.getField(fieldString), actual);
	}

	@Test
	public void getForrmatStringTest() {
		final Cell[][] target = {
				{new Cell(0,0), null},
				{new Cell(0,1), new Cell(1,1)}};
		final String fieldString = ""
				+ "{|X| - }"
				+ "{|X||X|}";
		assertEquals(FieldFormatUtil.getForrmatString(target), fieldString);
	}
}

