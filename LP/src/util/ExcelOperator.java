package util;
import java.io.*;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;

public class ExcelOperator {

	/*******************************************************************
	 * Excel file Read/Write Operation
	 *********************************************************************/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jxl.Workbook rwb = null;
	    try{
	      //构建Workbook对象, 只读Workbook对象
	      //直接从本地文件创建Workbook
	      //从输入流创建Workbook
	    	WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("ISO-8859-1");
            rwb= Workbook.getWorkbook(new File("D:/EclipseWorkspace/knowledge.xls"),workbookSettings); 

	//      InputStream is = new FileInputStream("D:/EclipseWorkspace/knowledge.xls");
	//      rwb = Workbook.getWorkbook(is);      //Sheet(术语：工作表)就是Excel表格左下角的Sheet1,Sheet2,Sheet3但在程序中
	      //Sheet的下标是从0开始
	      //获取第一张Sheet表
	       Sheet rs = rwb.getSheet(0);
	       //获取Sheet表中所包含的总列数
	       int rsColumns = rs.getColumns();
	       //获取Sheet表中所包含的总行数
	       int rsRows = rs.getRows();
	       //获取指定单元格的对象引用
	       String cellContent = "";
	       for(int i=0;i<rsRows;i++){
	         for(int j=0;j<rsColumns;j++){
	           Cell cell = rs.getCell(j,i);
	           cellContent = cell.getContents();
	           cellContent.trim();
	           System.out.print(cellContent+' ');	           
	         }
	         System.out.println();
	       }
	       //利用已经创建的Excel工作薄创建新的可写入的Excel工作薄
	       jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File("D:/EclipseWorkspace/knowledge2.xls"),rwb);
	       //读取第一张工作表
	       jxl.write.WritableSheet ws = wwb.getSheet(0);       //获得第一个单元格对象
	       jxl.write.WritableCell wc = ws.getWritableCell(0, 0);
	       //判断单元格的类型, 做出相应的转化
	      /*
	       if (wc.getType() == CellType.LABEL) {
	         Label l = (Label) wc;
	         l.setString("The value has been modified.");
	       }
	       */
	       //写入Excel对象
	       wwb.write();
	       wwb.close();    }catch(Exception e){
	      e.printStackTrace();
	    }
	    finally{
	      //操作完成时，关闭对象，释放占用的内存空间
	      rwb.close();    }
	  }
	
	public static String removeDotTrim(String str)
	{
		String temp = "";
		temp = str.trim();
		return temp;
	}

}
