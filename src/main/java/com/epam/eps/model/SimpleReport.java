/**
 *
 */
package com.epam.eps.model;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.ReviewSector;
import com.epam.eps.framework.support.inject.Inject;
import com.epam.eps.framework.support.risk.RiskList;
import com.epam.eps.model.risk.Risk;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alexander_Lotsmanov
 */
public class SimpleReport implements Report {
	private static final int COUNT_TRAIT = 80;
	private static final int COUNT_DELIMITERS = 2;

	private static final String EPS = "EMERGENCY PREVENTION SYSTEM";
	private static final String WITH_ENTER = "%s\n";
	private static final String ENTER = "\n";
	private static final String SPACE = " ";
	private static final String EQUALLY = " = ";
	private static final String TRAIT = "-";
	private static final String SEMICOLON = ";";
	private static final String COLON = ":";
	private static final String RANKS = "Entered ranks plank with following borders";
	private static final String WIDTH = "Field's width is M";
	private static final String HEIGHT = "Field's height is N";
	private static final String RISK_GROUPS_REPORT = "Risk groups report";
	private static final String GROUPS = "groups";

	private static final String NUMBER = "%s ";
	private static final String OCCUPIED = "|X|";
	private static final String EMPTY = " - ";

	private static final String EPS_BEAN_ID_RISK_NONE = "eps.bean.id.risk.none";
	private static final String EPS_BEAN_ID_RISK_MINOR = "eps.bean.id.risk.minor";
	private static final String EPS_BEAN_ID_RISK_NORMAL = "eps.bean.id.risk.normal";
	private static final String EPS_BEAN_ID_RISK_MAJOR = "eps.bean.id.risk.major";
	private static final String EPS_BEAN_ID_RISK_CRITICAL = "eps.bean.id.risk.critical";

	private static final String EPS_BEAN_ID_REVIEW_SECTOR = "eps.bean.id.reviewSector";

	@RiskList(id = {EPS_BEAN_ID_RISK_NONE, EPS_BEAN_ID_RISK_MINOR,
			EPS_BEAN_ID_RISK_NORMAL, EPS_BEAN_ID_RISK_MAJOR, EPS_BEAN_ID_RISK_CRITICAL})
	private List<Risk> risks;

	@Inject(EPS_BEAN_ID_REVIEW_SECTOR)
	private ReviewSector reviewSector;

	//get count of input symbol
	public String getCountSymbols(String symbol, int count){
		StringBuilder symbols = new StringBuilder();

		for (int i = 0; i < count; i++){
			symbols.append(symbol);
		}

		return symbols.toString();
	}

	@Override
	public String getReport() {
		StringBuilder report = new StringBuilder();

		report.append(ENTER);
		report.append(String.format(WITH_ENTER, EPS));
		report.append(getCountSymbols(String.format(WITH_ENTER, getCountSymbols(TRAIT, COUNT_TRAIT)), COUNT_DELIMITERS));
		report.append(WIDTH);
		report.append(EQUALLY);
		report.append(String.format(WITH_ENTER, reviewSector.getWidth()));
		report.append(HEIGHT);
		report.append(EQUALLY);
		report.append(String.format(WITH_ENTER, String.format(WITH_ENTER, reviewSector.getHeight())));
		report.append(String.format(WITH_ENTER, RANKS + COLON));
		risks.stream().forEach(risk -> report.append(String.format(WITH_ENTER,
				risk.getClass().getSimpleName() + EQUALLY + risk.getMin() + TRAIT + risk.getMax() + SEMICOLON)));
		report.append(ENTER);
		report.append(String.format(WITH_ENTER, getCountSymbols(TRAIT, COUNT_TRAIT)));
		report.append(String.format(WITH_ENTER, String.format(WITH_ENTER, getSector(reviewSector.getSector()))));
		report.append(String.format(WITH_ENTER, RISK_GROUPS_REPORT + COLON));
		report.append(String.format(WITH_ENTER, getCountSymbols(TRAIT, COUNT_TRAIT)));
		risks.stream().forEach(risk -> report.append(String.format(WITH_ENTER,
				risk.getClass().getSimpleName() + COLON + SPACE + risk.getGroups().size() + SPACE + GROUPS + SEMICOLON)));

		return report.toString();
	}

	//forming header
	private String getHeader(int width, int countDigitsMaxWidth, int countDigitsMaxHeight){
		StringBuilder header = new StringBuilder();

		for (int i = 0; i < countDigitsMaxHeight + 2; i++){
			header.append(SPACE);
		}

		for (int i = 0; i < width - 1; i++){
			if (countDigitsMaxWidth < 2) header.append(SPACE);
			header.append(String.format(NUMBER, getNumberWithSpaces(i, countDigitsMaxWidth)));
		}

		if (countDigitsMaxWidth < 2) header.append(SPACE);
		header.append(getNumberWithSpaces(width - 1, countDigitsMaxWidth));
		header.append(ENTER);

		return header.toString();
	}

	//returns the number with the required number of spaces before it
	private String getNumberWithSpaces(int number, int countDigitsMax){
		StringBuilder numberWithSpaces = new StringBuilder();

		int countSpaces = countDigitsMax - getCountDigits(number);

		for (int i = 0; i < countSpaces; i++){
			numberWithSpaces.append(SPACE);
		}

		numberWithSpaces.append(number);

		return numberWithSpaces.toString();
	}

	//number of digits in numbers
	private int getCountDigits(int number){
		return String.valueOf(number).length();
	}

	//get cell
	private String getCell(Cell cell){
		return cell != null ? OCCUPIED : EMPTY;
	}

	@Override
	public String getSector(Cell[][] quadrantsMatrix) {
		StringBuilder field = new StringBuilder();

		int width = quadrantsMatrix[0].length;
		int height = quadrantsMatrix.length;

		int countDigitsMaxWidth = getCountDigits(width - 1);
		int countDigitsMaxHeight = getCountDigits(height - 1);

		field.append(getHeader(width, countDigitsMaxWidth, countDigitsMaxHeight));

		for (int i = 0; i < height; i++){
			field.append(SPACE);
			field.append(String.format(NUMBER, getNumberWithSpaces(i, countDigitsMaxHeight)));
			for (int j = 0; j < width; j++){
				if (countDigitsMaxWidth > 2){
					for (int k = 0; k < countDigitsMaxWidth - 2; k++){
						field.append(SPACE);
					}
				}
				field.append(getCell(quadrantsMatrix[i][j]));
			}
			field.append(ENTER);
		}

		return field.toString();
	}
}

