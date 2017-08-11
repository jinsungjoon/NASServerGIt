package com.nwtns.framework.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class NWExcel {

	public static List<List<List<String>>> excel(String filePath, String ext) {
		List sheetList = new ArrayList();

		try {
			NWLog.i(NWExcel.class, "Start Excel Read");
			Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));
			int sheets = workbook.getNumberOfSheets();
			for (int sheetIndex = 0; sheetIndex < sheets; sheetIndex++) {
				Sheet sheet = workbook.getSheetAt(sheetIndex); // 해당 엑셀파일의 시트(Sheet) 수
				int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
				List rowList = new ArrayList();
				for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
					Row row = sheet.getRow(rowIndex); // 각 행을 읽어온다
					if (row != null) {
						int cells = row.getPhysicalNumberOfCells();
						List cellList = new ArrayList();
						Cell firstCell = row.getCell(0);
						if (firstCell.getCellType() == Cell.CELL_TYPE_BLANK) {
							continue;
						}
						if (cells > 0) {
							for (int columnIndex = 0; columnIndex < cells; columnIndex++) {
								Cell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
								String value = "";
								switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고
								case Cell.CELL_TYPE_NUMERIC:
									value = String.valueOf((int)cell.getNumericCellValue());
									cellList.add(value);
									break;
								case Cell.CELL_TYPE_STRING:
									value = cell.getStringCellValue();
									cellList.add(value);
									break;
								}
							}
							rowList.add(cellList);
						}
					}
				}
				sheetList.add(rowList);
			}
		}
		catch (Exception e) {
			NWLog.i(NWExcel.class, "err");
		}
		NWLog.i(NWExcel.class, "End Excel Read");

		return sheetList;
	}
}
