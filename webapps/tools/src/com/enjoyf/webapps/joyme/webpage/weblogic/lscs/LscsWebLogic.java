package com.enjoyf.webapps.joyme.webpage.weblogic.lscs;

import com.enjoyf.util.JsonBinder;
import com.enjoyf.webapps.joyme.webpage.dto.lscs.CardObject;
import com.enjoyf.webapps.joyme.webpage.dto.lscs.CardJson;
import com.enjoyf.webapps.joyme.webpage.dto.lscs.Category;
import com.enjoyf.webapps.joyme.webpage.dto.lscs.CategoryJson;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-10-16 下午3:31
 * Description:
 */
@Service(value = "lscsWebLogic")
public class LscsWebLogic {

    public List<CardJson> praseByExcel(File excelFile) {
        List<CardJson> returnList = new ArrayList<CardJson>();
        try {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> keyRowIterator = sheet.rowIterator();

            int i = 1;
            int rowIndx = 0;
            while (keyRowIterator.hasNext()) {
                Row row = keyRowIterator.next();
                if (rowIndx > 0) {
                    CardJson categoryJson = new CardJson();

                    Cell categoryIdCell = row.getCell(0);
                    Cell manualIdCell = row.getCell(1);
                    Cell nameCell = row.getCell(2);
                    Cell descCell = row.getCell(3);
                    Cell jobCell = row.getCell(4);
                    Cell typeCell = row.getCell(5);
                    Cell manuCell = row.getCell(6);
                    Cell attackCell = row.getCell(7);
                    Cell lifeCell = row.getCell(8);
                    Cell picUrlCell = row.getCell(9);
                    Cell operCell = row.getCell(10);

                    CardObject object = new CardObject();
                    object.setCategoryId((int) categoryIdCell.getNumericCellValue());
                    object.setManualId((int) manualIdCell.getNumericCellValue());
                    object.setName(nameCell.getStringCellValue());
                    object.setPicUrl(picUrlCell.getStringCellValue());
                    object.setJob(jobCell.getStringCellValue());
                    object.setType(typeCell.getStringCellValue());
                    object.setMana((int) manuCell.getNumericCellValue());
                    object.setAttack((int) attackCell.getNumericCellValue());
                    try {
                        object.setLife((int) lifeCell.getNumericCellValue());
                    } catch (Exception e) {
                        System.out.println(object.getName()+"--lefe null");
                    }
                    try {
                        object.setDesc(descCell.getStringCellValue());
                    } catch (Exception e) {
                        System.out.println(object.getName());
                    }

                    String operate = operCell.getStringCellValue();

                    categoryJson.setType(operate);
                    categoryJson.setObject(object);

                    returnList.add(categoryJson);
                }
                rowIndx++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnList;
    }

    public List<CategoryJson> praseCategoryByExcel(File excelFile) {
        List<CategoryJson> returnList = new ArrayList<CategoryJson>();

        try {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> keyRowIterator = sheet.rowIterator();

            int rowIndx = 0;
            while (keyRowIterator.hasNext()) {
                Row row = keyRowIterator.next();
                if (rowIndx > 0) {
                    CategoryJson categoryJson = new CategoryJson();

                    Cell categoryIdCell = row.getCell(0);
                    Cell categoryNameCell = row.getCell(1);
                    Cell picUrlCell = row.getCell(2);

                    Cell operCell = row.getCell(3);

                    Category object = new Category();
                    object.setCategoryId((int) categoryIdCell.getNumericCellValue());
                    object.setCategoryName(categoryNameCell.getStringCellValue());
                    object.setPicUrl(picUrlCell.getStringCellValue());

                    String operate = operCell.getStringCellValue();

                    categoryJson.setType(operate);
                    categoryJson.setObject(object);

                    returnList.add(categoryJson);
                }
                rowIndx++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnList;
    }

    public static void main(String[] args) {
        LscsWebLogic lscsWebLogic = new LscsWebLogic();

        List<CardJson> returnList = lscsWebLogic.praseByExcel(new File("C:\\Users\\ericliu.EF\\Desktop\\lscs.xlsx"));

        System.out.println(JsonBinder.buildNormalBinder().toJson(returnList));
    }


}
