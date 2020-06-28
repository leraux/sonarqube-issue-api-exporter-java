package in.flyspark.sonarqube.exporter.service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.flyspark.sonarqube.exporter.entity.Issues;
import in.flyspark.sonarqube.exporter.util.Constants;
import in.flyspark.sonarqube.exporter.util.DateTimeUtil;
import in.flyspark.sonarqube.exporter.util.Utils;

public class ExcelService {
	private static final Logger logger = LoggerFactory.getLogger(ExcelService.class.getSimpleName());

	public static String exportExcel(List<Issues> issues, String projectName) throws IOException {
		logger.debug("Exporting Excel");
		Workbook workbook = null;
		FileOutputStream fileOut = null;
		File file = null;
		try {
			if (!issues.isEmpty()) {

				workbook = new XSSFWorkbook();
				CreationHelper createHelper = workbook.getCreationHelper();

				CellStyle generalCellStyle = workbook.createCellStyle();
				generalCellStyle.setAlignment(HorizontalAlignment.LEFT);
				generalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				generalCellStyle.setWrapText(true);
				setFont(workbook.createFont(), generalCellStyle, false, 11, IndexedColors.BLACK.getIndex());
				setBorder(generalCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setBGColor(workbook, generalCellStyle, new Color(255, 255, 255), FillPatternType.SOLID_FOREGROUND);

				CellStyle generalCellStyleCenter = workbook.createCellStyle();
				generalCellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
				generalCellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
				generalCellStyleCenter.setWrapText(true);
				setFont(workbook.createFont(), generalCellStyleCenter, false, 11, IndexedColors.BLACK.getIndex());
				setBorder(generalCellStyleCenter, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setBGColor(workbook, generalCellStyleCenter, new Color(255, 255, 255), FillPatternType.SOLID_FOREGROUND);

				CellStyle generalBoldCellStyle = workbook.createCellStyle();
				generalBoldCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				generalBoldCellStyle.setWrapText(true);
				setFont(workbook.createFont(), generalBoldCellStyle, true, 11, IndexedColors.BLACK.getIndex());
				setBorder(generalBoldCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setBGColor(workbook, generalBoldCellStyle, new Color(255, 255, 255), FillPatternType.SOLID_FOREGROUND);

				CellStyle createdDateCellStyle = workbook.createCellStyle();
				createdDateCellStyle.setAlignment(HorizontalAlignment.CENTER);
				createdDateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				createdDateCellStyle.setWrapText(true);
				setFont(workbook.createFont(), createdDateCellStyle, false, 11, IndexedColors.BLACK.getIndex());
				setBorder(createdDateCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setBGColor(workbook, createdDateCellStyle, new Color(255, 255, 255), FillPatternType.SOLID_FOREGROUND);
				createdDateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("YYYY-MM-DD HH:MM:SS"));

				Cell firstCell = null, lastCell = null;
				Row firstRow = null, lastRow = null;

				// Summary Sheet

				Sheet summarySheet = workbook.createSheet(Constants.SUMMARY_SHEET);

				Row rowDate = summarySheet.createRow(1);

				Cell dateLabelCell = rowDate.createCell(1);
				dateLabelCell.setCellValue("Date");
				dateLabelCell.setCellStyle(generalBoldCellStyle);

				Cell dateValueCell = rowDate.createCell(2);
				dateValueCell.setCellValue(DateTimeUtil.getDate());
				dateValueCell.setCellStyle(generalBoldCellStyle);

				// Severity

				String severityColumns[] = { "Severity", "Count" };
				Map<String, Long> mapSeverity = issues.stream().filter(issue -> !Utils.isNullEmpty(issue.getLine()))
						.collect(Collectors.groupingBy(Issues::getSeverity, Collectors.counting()));

				CellStyle severityCellStyle = workbook.createCellStyle();
				severityCellStyle.setWrapText(true);
				severityCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				setBorder(severityCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setFont(workbook.createFont(), severityCellStyle, true, 12, IndexedColors.BLACK.getIndex());

				Row severityHeaderRow = summarySheet.createRow(3);
				for (int i = 0; i < severityColumns.length; i++) {
					Cell cell = severityHeaderRow.createCell(i + 1);
					cell.setCellValue(severityColumns[i]);
					cell.setCellStyle(severityCellStyle);

					if (i == 0)
						firstCell = cell;
					if (i == (severityColumns.length - 1))
						lastCell = cell;
				}

				severityCellStyle = workbook.createCellStyle();
				severityCellStyle.setWrapText(true);
				setBorder(severityCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setFont(workbook.createFont(), severityCellStyle, false, 12, IndexedColors.BLACK.getIndex());

				int severityRowNumber = 4, iTemp = 0;
				for (Map.Entry<String, Long> entry : mapSeverity.entrySet()) {
					int severityColumnIndex = 1;
					Row severityRow = summarySheet.createRow(severityRowNumber++);

					Cell cellKey = severityRow.createCell(severityColumnIndex++);
					cellKey.setCellValue(entry.getKey());
					cellKey.setCellStyle(severityCellStyle);

					Cell cellValue = severityRow.createCell(severityColumnIndex++);
					cellValue.setCellValue(entry.getValue());
					cellValue.setCellStyle(severityCellStyle);

					if (iTemp == 0)
						firstRow = severityRow;
					if (iTemp == (mapSeverity.size() - 1))
						lastRow = severityRow;

					iTemp++;
				}

				severityCellStyle = workbook.createCellStyle();
				severityCellStyle.setWrapText(true);
				setBorder(severityCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setFont(workbook.createFont(), severityCellStyle, true, 12, IndexedColors.BLACK.getIndex());
				setBGColor(workbook, severityCellStyle, new Color(217, 225, 242), FillPatternType.SOLID_FOREGROUND);

				int severityColumnIndex = 1;
				Row severityTotalRow = summarySheet.createRow(severityRowNumber++);

				Cell severityTotalLabelCell = severityTotalRow.createCell(severityColumnIndex++);
				severityTotalLabelCell.setCellValue("Grand Total");
				severityTotalLabelCell.setCellStyle(severityCellStyle);

				Cell severityTotalValueCell = severityTotalRow.createCell(severityColumnIndex++);
				severityTotalValueCell.setCellFormula("SUM(C" + (firstRow.getRowNum() + 1) + ":C" + (lastRow.getRowNum() + 1) + ")");
				severityTotalValueCell.setCellStyle(severityCellStyle);

				// Type

				Map<String, Long> mapType = issues.stream().filter(issue -> !Utils.isNullEmpty(issue.getLine()))
						.collect(Collectors.groupingBy(Issues::getType, Collectors.counting()));
				String typeColumns[] = { "Type", "Count" };

				CellStyle typeCellStyle = workbook.createCellStyle();
				typeCellStyle.setWrapText(true);
				setBorder(typeCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setFont(workbook.createFont(), typeCellStyle, true, 12, IndexedColors.BLACK.getIndex());

				Row typeHeaderRow = summarySheet.createRow(3 + mapSeverity.size() + 3);
				for (int i = 0; i < typeColumns.length; i++) {
					Cell cell = typeHeaderRow.createCell(i + 1);
					cell.setCellValue(typeColumns[i]);
					cell.setCellStyle(typeCellStyle);
				}

				typeCellStyle = workbook.createCellStyle();
				typeCellStyle.setWrapText(true);
				setBorder(typeCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setFont(workbook.createFont(), typeCellStyle, false, 12, IndexedColors.BLACK.getIndex());

				iTemp = 0;
				int typeRowNumber = 3 + mapSeverity.size() + 4;
				for (Map.Entry<String, Long> entry : mapType.entrySet()) {
					int typeColumnIndex = 1;
					Row typeRow = summarySheet.createRow(typeRowNumber++);

					Cell cellKey = typeRow.createCell(typeColumnIndex++);
					cellKey.setCellValue(entry.getKey());
					cellKey.setCellStyle(typeCellStyle);

					Cell cellValue = typeRow.createCell(typeColumnIndex++);
					cellValue.setCellValue(entry.getValue());
					cellValue.setCellStyle(typeCellStyle);

					if (iTemp == 0)
						firstRow = typeRow;
					if (iTemp == (mapType.size() - 1))
						lastRow = typeRow;

					iTemp++;
				}

				typeCellStyle = workbook.createCellStyle();
				typeCellStyle.setWrapText(true);
				setBorder(typeCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setFont(workbook.createFont(), typeCellStyle, true, 12, IndexedColors.BLACK.getIndex());
				setBGColor(workbook, typeCellStyle, new Color(217, 225, 242), FillPatternType.SOLID_FOREGROUND);

				int typeColumnIndex = 1;
				Row typeTotalRow = summarySheet.createRow(typeRowNumber++);

				Cell typeTotalLabelCell = typeTotalRow.createCell(typeColumnIndex++);
				typeTotalLabelCell.setCellValue("Grand Total");
				typeTotalLabelCell.setCellStyle(typeCellStyle);

				Cell typeTotalValueCell = typeTotalRow.createCell(typeColumnIndex++);
				typeTotalValueCell.setCellFormula("SUM(C" + (firstRow.getRowNum() + 1) + ":C" + (lastRow.getRowNum() + 1) + ")");
				typeTotalValueCell.setCellStyle(typeCellStyle);

				for (int i = 1; i < 10; i++) {
					summarySheet.autoSizeColumn(i);
				}

				Sheet reportSheet = workbook.createSheet(Constants.REPORT_SHEET);

				CellStyle reportHeaderCellStyle = workbook.createCellStyle();
				reportHeaderCellStyle.setWrapText(true);
				setFont(workbook.createFont(), reportHeaderCellStyle, true, 12, IndexedColors.WHITE.getIndex());
				setBorder(reportHeaderCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
				setBGColor(workbook, reportHeaderCellStyle, new Color(32, 55, 100), FillPatternType.SOLID_FOREGROUND);
				reportHeaderCellStyle.setAlignment(HorizontalAlignment.CENTER);

				Row reportHeaderRow = reportSheet.createRow(0);

				String reportColumns[] = { "#", "Type", "Severity", "Component", "Line", "Message" };
				for (int i = 0; i < reportColumns.length; i++) {
					Cell cell = reportHeaderRow.createCell(i);
					cell.setCellValue(reportColumns[i]);
					cell.setCellStyle(reportHeaderCellStyle);
					if (i == 0)
						firstCell = cell;
					if (i == (reportColumns.length - 1))
						lastCell = cell;
				}

				severityCellStyle = workbook.createCellStyle();
				severityCellStyle.setWrapText(true);
				severityCellStyle.setAlignment(HorizontalAlignment.CENTER);
				setBorder(severityCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);

				int reportRowNumber = 1;
				int reportColumnNumber = 0;
				for (Issues issue : issues) {
					if (Utils.isNullEmpty(issue.getLine()))
						continue;

					reportColumnNumber = 0;
					Row reportRow = reportSheet.createRow(reportRowNumber++);

					Cell cellSrNo = reportRow.createCell(reportColumnNumber++);
					if (reportRowNumber > 2) {
						cellSrNo.setCellFormula("A" + ((reportRowNumber - 1) + "+" + 1));
					} else {
						cellSrNo.setCellValue(1);
					}
					cellSrNo.setCellStyle(generalCellStyleCenter);

					Cell cellType = reportRow.createCell(reportColumnNumber++);
					cellType.setCellValue(issue.getType());
					cellType.setCellStyle(generalCellStyleCenter);

					Cell severityCell = reportRow.createCell(reportColumnNumber++);
					severityCell.setCellValue(issue.getSeverity());
					severityCell.setCellStyle(generalCellStyleCenter);

					Cell cellComponent = reportRow.createCell(reportColumnNumber++);
					cellComponent.setCellValue(issue.getComponent());
					cellComponent.setCellStyle(generalCellStyle);

					Cell cellLine = reportRow.createCell(reportColumnNumber++);
					cellLine.setCellValue(Utils.isNullEmpty(issue.getLine()) ? 0 : Integer.parseInt(issue.getLine()));
					cellLine.setCellStyle(generalCellStyleCenter);

					Cell cellMessage = reportRow.createCell(reportColumnNumber++);
					cellMessage.setCellValue(issue.getMessage());
					cellMessage.setCellStyle(generalCellStyle);

				}

				reportSheet.setAutoFilter(
						new CellRangeAddress(firstCell.getRowIndex(), lastCell.getRowIndex(), firstCell.getColumnIndex(), lastCell.getColumnIndex()));
				reportSheet.createFreezePane(0, 1);

				for (int i = 0; i < reportColumns.length; i++) {
					if (i == 0 || i == 4) {
						reportSheet.setColumnWidth(i, 2000);
					} else if (i == (reportColumns.length - 1)) {
						reportSheet.setColumnWidth(i, 12000);
					} else if (i == 3 || i == 5) {
						reportSheet.setColumnWidth(i, 20000);
					} else {
						reportSheet.setColumnWidth(i, 6000);
					}
				}

				file = new File(Constants.getFileName(projectName) + ".xlsx");
				fileOut = new FileOutputStream(file);
				workbook.write(fileOut);
				logger.debug("Excel Generated : {}", file.getName());
				return file.getName();
			} else {
				logger.warn("No Data Found");
			}
		} catch (Exception ex) {
			logger.error("exportExcel : ", ex);
			ex.printStackTrace();
		} finally {
			if (fileOut != null)
				fileOut.close();
			if (workbook != null)
				workbook.close();

		}
		return "";
	}

	private static void setFont(Font font, CellStyle cellStyle, boolean isBold, int height, short color) {
		font.setBold(isBold);
		font.setFontHeightInPoints((short) height);
		font.setColor(color);
		cellStyle.setFont(font);
	}

	private static void setBGColor(Workbook workbook, CellStyle cellStyle, Color color, FillPatternType fp) {
		byte[] rgb = new byte[] { (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue() };
		if (cellStyle instanceof XSSFCellStyle) {
			XSSFCellStyle xssfreportHeaderCellStyle = (XSSFCellStyle) cellStyle;
			xssfreportHeaderCellStyle.setFillForegroundColor(new XSSFColor(rgb, null));
		} else if (cellStyle instanceof HSSFCellStyle) {
			cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.getIndex());
			HSSFWorkbook hssfworkbook = (HSSFWorkbook) workbook;
			HSSFPalette palette = hssfworkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.LIME.getIndex(), rgb[0], rgb[1], rgb[2]);
		}
		cellStyle.setFillPattern(fp);

	}

	private static void setBorder(CellStyle cellStyle, BorderStyle left, BorderStyle top, BorderStyle right, BorderStyle bottom) {
		cellStyle.setBorderLeft(left);
		cellStyle.setBorderTop(top);
		cellStyle.setBorderRight(right);
		cellStyle.setBorderBottom(bottom);
	}
}
