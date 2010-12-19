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
	      //����Workbook����, ֻ��Workbook����
	      //ֱ�Ӵӱ����ļ�����Workbook
	      //������������Workbook
	    	WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("ISO-8859-1");
            rwb= Workbook.getWorkbook(new File("D:/EclipseWorkspace/knowledge.xls"),workbookSettings); 

	//      InputStream is = new FileInputStream("D:/EclipseWorkspace/knowledge.xls");
	//      rwb = Workbook.getWorkbook(is);      //Sheet(���������)����Excel������½ǵ�Sheet1,Sheet2,Sheet3���ڳ�����
	      //Sheet���±��Ǵ�0��ʼ
	      //��ȡ��һ��Sheet��
	       Sheet rs = rwb.getSheet(0);
	       //��ȡSheet������������������
	       int rsColumns = rs.getColumns();
	       //��ȡSheet������������������
	       int rsRows = rs.getRows();
	       //��ȡָ����Ԫ��Ķ�������
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
	       //�����Ѿ�������Excel�����������µĿ�д���Excel������
	       jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File("D:/EclipseWorkspace/knowledge2.xls"),rwb);
	       //��ȡ��һ�Ź�����
	       jxl.write.WritableSheet ws = wwb.getSheet(0);       //��õ�һ����Ԫ�����
	       jxl.write.WritableCell wc = ws.getWritableCell(0, 0);
	       //�жϵ�Ԫ�������, ������Ӧ��ת��
	      /*
	       if (wc.getType() == CellType.LABEL) {
	         Label l = (Label) wc;
	         l.setString("The value has been modified.");
	       }
	       */
	       //д��Excel����
	       wwb.write();
	       wwb.close();    }catch(Exception e){
	      e.printStackTrace();
	    }
	    finally{
	      //�������ʱ���رն����ͷ�ռ�õ��ڴ�ռ�
	      rwb.close();    }
	  }
	
	public static String removeDotTrim(String str)
	{
		String temp = "";
		temp = str.trim();
		return temp;
	}

}
