package com.jw.admin.core.util;

import com.jw.order.bean.vo.OrderProductVo;
import com.jw.order.bean.vo.OrderScenicVo;
import com.jw.order.bean.vo.ReconciliationVo;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ExcelUtil {
	private static Logger logger = Logger.getLogger("ReadExcelUtil");

	public static String message = null;

	public ExcelUtil() {
	}
//	public static List<TPlayerInfo> readExcel(String filePathName , String channelId) {
//    	List<TPlayerInfo> list =new ArrayList<TPlayerInfo>();
//    	try {
//    	    String group = null;
//    	    String match = "^[1-9]\\d*$";
//            InputStream input = new FileInputStream(filePathName);  //建立输入流
//            Workbook wb  = null;
//            //根据文件格式(2003或者2007)来初始化
//            if(filePathName.endsWith("xlsx")) { //判断是否是excel2007格式
//            	wb = new XSSFWorkbook(input);
//            }else {
//            	wb = new HSSFWorkbook(input);
//            }
//            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单
//            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器
//            Row row = rows.next();
//            System.out.println("start。。。。。。");
//            while (rows.hasNext()) {
//                row = rows.next();  //获得行数据
//                TPlayerInfo pzInfo=new TPlayerInfo();
//                pzInfo.setCreateTime(new Date());
//                //System.out.println("Row #" + row.getRowNum());  //获得行号从0开始
//                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
//                while (cells.hasNext()) {
//                    Cell cell = cells.next();
//                    switch (cell.getColumnIndex()) {   //根据cell中的类型来输出数据
//                    case SysConstant.name:
//                    	if(cell!=null && !getCellValue(cell).isEmpty()) {
//	                    	pzInfo.setName(getCellValue(cell));
////	                    	System.out.println(pzInfo.getName());
//                        }
//                    	break;
//                    case SysConstant.certNo:
//                    	if(cell!=null && !getCellValue(cell).isEmpty()) {
//                    		pzInfo.setCertNo(getCellValue(cell));
//                    	}
//                        break;
//                    case SysConstant.certType:
//                        if(cell!=null && !getCellValue(cell).isEmpty()) {
//                            int certType = 0;
//                            if("身份证".equals(getCellValue(cell))){
//                                certType = 1;
//                            }else if("护照".equals(getCellValue(cell))){
//                                certType = 2;
//                            }else if("通行证".equals(getCellValue(cell))){
//                                certType = 3;
//                            } else {
//
//                            }
//                            pzInfo.setCertType(certType);
//                        }
//                        break;
//                    case SysConstant.age:
//                        if(cell!=null && !getCellValue(cell).isEmpty()) {
//                            System.out.println(getCellValue(cell));
//                            if(!Pattern.matches(match,getCellValue(cell))){
//                                System.out.println("年龄必须是正整数");
//                                message = "年龄必须是正整数";
//                                return null;
//                            }else {
//                                System.out.println(getCellValue(cell));
//                                pzInfo.setAge(Integer.parseInt(getCellValue(cell)));
//                            }
//                        } else {
//                    	    message = "年龄不能为空";
//                    	    return null;
//                        }
//                        break;
//                    case SysConstant.phone:
//                    	if(cell!=null && !getCellValue(cell).isEmpty()) {
//                    		pzInfo.setPhone(getCellValue(cell));
//                    	}
//                        break;
//                    case SysConstant.school:
//                    	if(cell!=null && !getCellValue(cell).isEmpty()) {
//                    		pzInfo.setSchool(getCellValue(cell));
//                    	}
//                        break;
//                    case SysConstant.email:
//                    	if(cell!=null && !getCellValue(cell).isEmpty()) {
//                    		pzInfo.setEmail(getCellValue(cell));
//                    	}
//                        break;
//                    case SysConstant.hobby:
//                    	if(cell!=null && !getCellValue(cell).isEmpty()) {
//                    		pzInfo.setHobby(getCellValue(cell));
//                    	}
//                        break;
//                    case SysConstant.productId:
//                        if(cell!=null && !getCellValue(cell).isEmpty()) {
//                            int product = 0;
//                            if(getCellValue(cell).equals("早鸟")){
//                                product = 1;
//                            }else if(getCellValue(cell).equals("单人")){
//                                product = 2;
//                            }else if(getCellValue(cell).equals("亲子")){
//                                product = 3;
//                            }else {
//
//                            }
//                            pzInfo.setProductId(product);
//                        }
//                        break;
//                    case SysConstant.parentCertNo:
//                        if(cell!=null && !getCellValue(cell).isEmpty()) {
//                            pzInfo.setParentCertNo(getCellValue(cell));
//                        }
//                        break;
//                    case SysConstant.parentCertType:
//                        if(cell!=null && !getCellValue(cell).isEmpty()) {
//                            int certType = 0;
//                            if("身份证".equals(getCellValue(cell))){
//                                certType = 1;
//                            }else if("护照".equals(getCellValue(cell))){
//                                certType = 2;
//                            }else if("通行证".equals(getCellValue(cell))){
//                                certType = 3;
//                            } else {
//
//                            }
//                            pzInfo.setParentCertType(certType);
//                        }
//                        break;
//                    default:
//                    logger.info("unsuported sell type");
//                    break;
//                    }
//                }
//                //if(pzInfo!=null && pzInfo.getContract()!=null && pzInfo.getInvoicenumber()!=null) {
//                if(pzInfo.getAge() >= 3 && pzInfo.getAge() <= 12){
//                    group = "1";
//                }else if(pzInfo.getAge() >= 13 && pzInfo.getAge() <= 18){
//                    group = "2";
//                }else if(pzInfo.getAge() >= 19 && pzInfo.getAge() <= 22){
//                    group = "3";
//                }else {
//
//                }
//                pzInfo.setGroupNo(group);
//                pzInfo.setSourceType("1");
//                //pzInfo.setPayStatus("1");
//                pzInfo.setChannelId(Integer.parseInt(channelId));
//                pzInfo.setQrCodeStr(IdUtils.createUUID(32));
//                pzInfo.setCreateTime(new Date());
////                pzInfo.setCertType(1);
//                pzInfo.setIsDelete("0");
////                System.out.println(JSON.toJSONString(pzInfo));
//                list.add(pzInfo);
//               // }
//            }
//            input.close();
//        } catch (Exception ex) {
//            log.info("文件解析错误 : " + ex);
//            //ex.printStackTrace();
//            return null;
//        }
//    	return list;
//    }
	
	
//	public static String getCellValue(Cell cell) {
//    	String value="";
//    	switch (cell.getCellType()) {   //根据cell中的类型来输出数据
//        case HSSFCell.CELL_TYPE_NUMERIC:
//        	if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                Date date = cell.getDateCellValue();
//                value = DateFormatUtils.format(date, SysConstant.DATE_FORMAT);
//            } else {
//                value = String.valueOf(cell.getNumericCellValue());
//            }
//            break;
//        case HSSFCell.CELL_TYPE_STRING:
//        	value=cell.getStringCellValue();
//            break;
//        case HSSFCell.CELL_TYPE_FORMULA:
//        	value=cell.getCellFormula();
//            break;
//        default:
//        	logger.info("unsuported sell type");
//        break;
//    	}
//    	return value;
//    }
	
//	public static HSSFWorkbook createExcel(String title, String[] headers, String[] fieldCodes , List<TPlayerInfoVo> list, String pattern, String flag){
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//        // 生成一个表格
//        HSSFSheet sheet = workbook.createSheet(title);
//        // 设置表格默认列宽度为15个字节
//        sheet.setDefaultColumnWidth((short) 15);
//        // 生成一个样式
//        HSSFCellStyle style = workbook.createCellStyle();
//        // 设置这些样式
//        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        // 生成一个字体
//        HSSFFont font = workbook.createFont();
//        font.setColor(HSSFColor.VIOLET.index);
//        font.setFontHeightInPoints((short) 12);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        // 把字体应用到当前的样式
//        style.setFont(font);
//        // 生成并设置另一个样式
//        HSSFCellStyle style2 = workbook.createCellStyle();
//        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        // 生成另一个字体
//        HSSFFont font2 = workbook.createFont();
//	    font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//        // 把字体应用到当前的样式
//        style2.setFont(font2);
//        // 产生表格标题行
//        HSSFRow row = sheet.createRow(0);
//
//        for (short i = 0; i < headers.length; i++){
//        	HSSFCell cell = row.createCell(i);
//        	cell.setCellStyle(style);
//        	HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//        	cell.setCellValue(text);
//        }
//        Map<String,Long> mapCount= new HashMap<String,Long>();
//        if(flag.equals("reportForm")){
//            mapCount.put("invoicenumber",(long)0);
//            mapCount.put("waCount",(long)0);
//            mapCount.put("prodcutCoverage",(long)0);
//            mapCount.put("productPremium",(long)0);
//        }
//        // 遍历集合数据，产生数据行
//        Iterator<TPlayerInfoVo> it = list.iterator();
//        int index = 0;
//        //
//        HSSFCellStyle style3 = workbook.createCellStyle();
//        HSSFFont font3 = workbook.createFont();
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        style.setFont(font);
//
//        while (it.hasNext()){
//            index++;
//            row = sheet.createRow(index);
//            TPlayerInfoVo t = it.next();
//            for(short i = 0; i < fieldCodes.length; i++){
//                HSSFCell cell = row.createCell(i);
//                cell.setCellStyle(style2);
//                String fieldName = fieldCodes[i];
//                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//                try{
//                    Class<TPlayerInfoVo> tCls = TPlayerInfoVo.class;
//                    Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
//                    Object value = getMethod.invoke(t, new Object[]{});
//                    // 判断值的类型后进行强制类型转换
//                    String textValue = null;
//                    if(value==null) {
//                    	continue;
//                    }else if (value instanceof Integer) {
//                    	 int intValue = (Integer) value;
//                    	 cell.setCellValue(intValue);
//                     }else if (value instanceof Date){
//                         Date date = (Date) value;
//                         SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//                         textValue = sdf.format(date);
//                     } else {
//
//               /*         if(fieldName.equals("invoicenumber")){
//                            long longValue = Long.parseLong(value.toString());
//                            longValue=mapCount.get("invoicenumber")+longValue;
//                            mapCount.put("invoicenumber",longValue);
//                        }*/
//                      /*  if(fieldName.equals("waCount")){
//                            long longValue = Long.parseLong(value.toString());
//                            longValue=mapCount.get("waCount")+longValue;
//                            mapCount.put("waCount",longValue);
//                        }
//                        if(fieldName.equals("prodcutCoverage")){
//                            long longValue = Long.parseLong(value.toString());
//                            longValue=mapCount.get("prodcutCoverage")+longValue;
//                            mapCount.put("prodcutCoverage",longValue);
//                        }
//                        if(fieldName.equals("productPremium")){
//                            long longValue = Long.parseLong(value.toString());
//                            longValue=mapCount.get("productPremium")+longValue;
//                            mapCount.put("productPremium",longValue);
//                        }*/
//                         // 其它数据类型都当作字符串简单处理
//                         textValue = value.toString();
//                     }
//
//                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//                    if (textValue != null)
//                    {
//                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
//                        Matcher matcher = p.matcher(textValue);
//                        if (matcher.matches()){
//                            // 是数字当作double处理
//                            cell.setCellValue(Double.parseDouble(textValue));
//                        }else{
//                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
//                          //  HSSFFont font3 = workbook.createFont();
//                        //    font3.setColor(HSSFColor.BLUE.index);
//                        //    richString.applyFont(font3);
//                            cell.setCellStyle(style3);
//                            cell.setCellValue(richString);
//                        }
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }finally{
//                    // 清理资源
//                }
//            }
//        }
//
//        if(flag.equals("reportForm")){
//            row = sheet.createRow(list.size()+1);
//            HSSFCell cell = row.createCell(0);
//            cell.setCellStyle(style2);
//            cell.setCellValue("总计");
//            cell = row.createCell(1);
//            cell.setCellStyle(style2);
//            cell.setCellValue(mapCount.get("invoicenumber"));
//            cell = row.createCell(2);
//            cell.setCellStyle(style2);
//            cell.setCellValue(mapCount.get("waCount"));
//            cell = row.createCell(3);
//            cell.setCellStyle(style2);
//            cell.setCellValue(mapCount.get("prodcutCoverage"));
//            cell = row.createCell(4);
//            cell.setCellStyle(style2);
//            cell.setCellValue(mapCount.get("productPremium"));
//        }
//        return workbook;
//	}

    public static HSSFWorkbook exportExcel(String title, String[] headers, String[] fieldCodes , List<ReconciliationVo> list, String pattern, String flag){
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);

        for (short i = 0; i < headers.length; i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        Map<String,Long> mapCount= new HashMap<String,Long>();
        if(flag.equals("reportForm")){
            mapCount.put("invoicenumber",(long)0);
            mapCount.put("waCount",(long)0);
            mapCount.put("prodcutCoverage",(long)0);
            mapCount.put("productPremium",(long)0);
        }
        // 遍历集合数据，产生数据行
        Iterator<ReconciliationVo> it = list.iterator();
        int index = 0;
        //
        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont font3 = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);

        while (it.hasNext()){
            index++;
            row = sheet.createRow(index);
            ReconciliationVo t = it.next();
            for(short i = 0; i < fieldCodes.length; i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String fieldName = fieldCodes[i];
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try{
                    Class<ReconciliationVo> tCls = ReconciliationVo.class;
                    Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if(value==null) {
                        continue;
                    }else if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                    }else if (value instanceof Date){
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {

                        textValue = value.toString();
                    }

                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null)
                    {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()){
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        }else{
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            //  HSSFFont font3 = workbook.createFont();
                            //    font3.setColor(HSSFColor.BLUE.index);
                            //    richString.applyFont(font3);
                            cell.setCellStyle(style3);
                            cell.setCellValue(richString);
                        }
                    }
                }catch (Exception e){
                    //e.printStackTrace();
                    log.info("",e);
                }finally{
                    // 清理资源
                }
            }
        }

        if(flag.equals("reportForm")){
            row = sheet.createRow(list.size()+1);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellValue("总计");
            cell = row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("invoicenumber"));
            cell = row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("waCount"));
            cell = row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("prodcutCoverage"));
            cell = row.createCell(4);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("productPremium"));
        }
        return workbook;
    }

    public static HSSFWorkbook exportProductExcel(String title, String[] headers, String[] fieldCodes , List<OrderProductVo> list, String pattern, String flag){
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);

        for (short i = 0; i < headers.length; i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        Map<String,Long> mapCount= new HashMap<String,Long>();
        if(flag.equals("reportForm")){
            mapCount.put("invoicenumber",(long)0);
            mapCount.put("waCount",(long)0);
            mapCount.put("prodcutCoverage",(long)0);
            mapCount.put("productPremium",(long)0);
        }
        // 遍历集合数据，产生数据行
        Iterator<OrderProductVo> it = list.iterator();
        int index = 0;
        //
        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont font3 = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);

        while (it.hasNext()){
            index++;
            row = sheet.createRow(index);
            OrderProductVo t = it.next();
            for(short i = 0; i < fieldCodes.length; i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String fieldName = fieldCodes[i];
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try{
                    Class<OrderProductVo> tCls = OrderProductVo.class;
                    Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if(value==null) {
                        continue;
                    }else if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                    }else if (value instanceof Date){
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {

                        textValue = value.toString();
                    }

                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null)
                    {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()){
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        }else{
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            //  HSSFFont font3 = workbook.createFont();
                            //    font3.setColor(HSSFColor.BLUE.index);
                            //    richString.applyFont(font3);
                            cell.setCellStyle(style3);
                            cell.setCellValue(richString);
                        }
                    }
                }catch (Exception e){
                    //e.printStackTrace();
                    log.info("",e);
                }finally{
                    // 清理资源
                }
            }
        }

        if(flag.equals("reportForm")){
            row = sheet.createRow(list.size()+1);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellValue("总计");
            cell = row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("invoicenumber"));
            cell = row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("waCount"));
            cell = row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("prodcutCoverage"));
            cell = row.createCell(4);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("productPremium"));
        }
        return workbook;
    }

    public static HSSFWorkbook exportScenicExcel(String title, String[] headers, String[] fieldCodes , List<OrderScenicVo> list, String pattern, String flag){
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);

        for (short i = 0; i < headers.length; i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        Map<String,Long> mapCount= new HashMap<String,Long>();
        if(flag.equals("reportForm")){
            mapCount.put("invoicenumber",(long)0);
            mapCount.put("waCount",(long)0);
            mapCount.put("prodcutCoverage",(long)0);
            mapCount.put("productPremium",(long)0);
        }
        // 遍历集合数据，产生数据行
        Iterator<OrderScenicVo> it = list.iterator();
        int index = 0;
        //
        HSSFCellStyle style3 = workbook.createCellStyle();
        HSSFFont font3 = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);

        while (it.hasNext()){
            index++;
            row = sheet.createRow(index);
            OrderScenicVo t = it.next();
            for(short i = 0; i < fieldCodes.length; i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String fieldName = fieldCodes[i];
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try{
                    Class<OrderScenicVo> tCls = OrderScenicVo.class;
                    Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if(value==null) {
                        continue;
                    }else if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                    }else if (value instanceof Date){
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {

                        textValue = value.toString();
                    }

                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null)
                    {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()){
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        }else{
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            //  HSSFFont font3 = workbook.createFont();
                            //    font3.setColor(HSSFColor.BLUE.index);
                            //    richString.applyFont(font3);
                            cell.setCellStyle(style3);
                            cell.setCellValue(richString);
                        }
                    }
                }catch (Exception e){
                    //e.printStackTrace();
                    log.info("",e);
                }finally{
                    // 清理资源
                }
            }
        }

        if(flag.equals("reportForm")){
            row = sheet.createRow(list.size()+1);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellValue("总计");
            cell = row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("invoicenumber"));
            cell = row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("waCount"));
            cell = row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("prodcutCoverage"));
            cell = row.createCell(4);
            cell.setCellStyle(style2);
            cell.setCellValue(mapCount.get("productPremium"));
        }
        return workbook;
    }
}
