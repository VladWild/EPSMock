package com.epam.eps.framework.support;

import com.epam.eps.framework.core.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FieldFormatUtil {

	private static final String FIELD_ROW_REGEXP = "\\{(\\|X\\|| - )+\\}";
	private static final String OCCUPIED_CELL_REGEXP = "\\|X\\|";
	private static final String EMPTY_CELL_REGEXP = " - ";

	private FieldFormatUtil() {
	}

	public static Cell[][] getField(String fieldString) {
		Pattern pattern = Pattern.compile(FIELD_ROW_REGEXP);
		Matcher matcher = pattern.matcher(fieldString);

		List<List<Cell>> field = new ArrayList<>();
		while (matcher.find()) {
			createRow(field, matcher.group(0));
		}
		return createFieldMatrix(field);
	}

	private static void createRow(List<List<Cell>> field, String row) {
		final String regex = String.format("%s%s%s", OCCUPIED_CELL_REGEXP, "|",
				EMPTY_CELL_REGEXP);
		Matcher matcher = Pattern.compile(regex).matcher(row);
		field.add(new ArrayList<Cell>());
		while (matcher.find()) {
			int y = field.size() - 1;
			int x = field.get(y).size();
			Cell cell = matcher.group(0).matches(OCCUPIED_CELL_REGEXP)
					? new Cell(x, y) : null;
			field.get(y).add(cell);
		}
	}

	private static Cell[][] createFieldMatrix(List<List<Cell>> field) {
		Cell[][] fieldMatrix = new Cell[field.size()][field.get(0).size()];
		for (int i = 0; i < field.size(); i++) {
			field.get(i).toArray(fieldMatrix[i]);
		}
		return fieldMatrix;
	}

	public static String getForrmatString(Cell[][] cells) {
		StringBuilder builder = new StringBuilder();
		for (Cell[] cells2 : cells) {
			builder.append("{");
			for (Cell cell : cells2) {
				builder.append(cell != null ? "|X|" : " - ");
			}
			builder.append("}");
		}
		return builder.toString();
	}
}

