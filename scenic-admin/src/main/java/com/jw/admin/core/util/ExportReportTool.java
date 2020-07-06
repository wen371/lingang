package com.jw.admin.core.util;

import com.csvreader.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExportReportTool {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportReportTool.class);
	/**
	 * 查询数据的超始游标位置，相当于从第0行开始，主要用于导出报表
	 */
	public final static int START_ROW_NUM = 0;
	/**
	 * 查询数据的返回数据最大记录数，默认999999999条，主要用于导出报表
	 */
	public final static int TOTAL_COUNT = 999999999;
	/**
	 * 用于生成下载文件的根级目录,所有子目录在该目录下生成
	 */
	private final static String DOWNLAOD_ROOT = "";//  /WEB-INF

	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 缓冲大小
	 */
	private final static int BUFFER = 1024;
	/**
	 * 用于格式化数字，小数点后最多支持9位,例如：34567.345，格式化后为34567.345，后面不会补0或其它
	 */
	private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.#########");

	/**
	 * 生成csv文件，并压缩成zip格式后，为客户端提示下载
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @param headers
	 *            列头
	 * @param datas
	 *            数据
	 * @return true:导出成功;false:失败
	 */
	/**
	 * 生成csv文件，并压缩成zip格式后，为客户端提示下载
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @param headers
	 *            列头字段名称，用于自动从datas中get数据
	 * @param headerNames
	 *            列头中文名称，用于写入到csv文件中
	 * @param datas
	 *            数据
	 */
	public static void export(HttpServletRequest request, HttpServletResponse response, List<FieldDesc> headers,
			List<String> headerNames, List<?> datas) {
		//分文件导出,每个csv文件条数
		float pageInfoCount=3000;
		//System.out.println("分页数量"+pageInfoCount);
		// 参数校验
		if (request == null) {
			return;
		}
		if (response == null) {
			return;
		}
		if (headers == null || headers.isEmpty()) {
			return;
		}
		if(datas ==null || datas.size() == 0){
			return;
		}
		// 准备生成csv文件名以及服务器上的存储目录
		ServletContext servletContext = request.getSession().getServletContext();
		// 获得服务器上的绝对目录位置
		String rootPath = servletContext.getRealPath(DOWNLAOD_ROOT);
		//System.out.println("rootPath:"+rootPath);
		String zipPath = "";
		File dirFile = null;
		
		do {
			// 生成随机32位字符做为目录，为了避免重复
			zipPath = Generator.getRStr(32);
			// 生成子目录
			dirFile = new File(rootPath + File.separator + zipPath);
			// 如果目录存在，则重新生成一个目录
		} while (dirFile.exists());

		// 创建目录
		dirFile.mkdir();

		//导出的文件个数
		double fileSize=Math.ceil(datas.size()/pageInfoCount);
		//导出的临时文件路径
		String pathNames="";
		String CurrentTimestamp = Generator.getCurrentTimestamp();
		for(int i=0;i<fileSize;i++){
			// 生成日间戳做为csv文件名，例如：201606140810.csv
			String csvFileName=CurrentTimestamp+"("+(i+1)+")";
			//System.out.println("csvFileName："+csvFileName);
			// 生成csv文件的全路径名
			String csvFilePath = rootPath + File.separator + zipPath + File.separator + csvFileName + ".csv";
			pathNames+=csvFilePath+",";
			// 创建csv文件
			CsvWriter wr = new CsvWriter(csvFilePath, ',', Charset.forName("GBK"));

			// 将数据写入到csv文件中
			try {
				// 写入中文列头信息
				String[] arrHeaderName = (String[]) headerNames.toArray(new String[0]);
				// 列数
				int columnCount = headers.size();
				wr.writeRecord(arrHeaderName,true);
				// 写入数据
				// 生成用于存放数据的String数组
				String[] data = new String[headers.size()];
				// 遍历数据集
				FieldDesc fieldDesc = null;
				String value = "";
				int fromIndex=(int) ((i+1)*pageInfoCount-pageInfoCount);
				int toIndex=(int) (fromIndex+pageInfoCount);
				//处理分不够完整页
				if(i == fileSize-1 && datas.size() % pageInfoCount != 0){
					toIndex=datas.size();
				}
				List<?> pageList=datas.subList(fromIndex, toIndex);
				//System.out.println("分页fromIndex:"+fromIndex+"   toIndex:"+toIndex+"   共"+datas.size()+"条数据"+"  每页"+pageInfoCount+"条数据  ； 共"+fileSize+"页");
				for (Object obj : pageList) {
					for (int idx = 0; idx < columnCount; idx++) {
						// 获得字段的描述对象，用于判断是否需要翻译
						fieldDesc = headers.get(idx);
						if (fieldDesc.isFixed()) {
							// 显示固定的值,不需要通过get方法取值
							value = fieldDesc.getFixedValue();
						} else {
							// 先通过get方法取值
							value = getValue(obj, fieldDesc.getFieldName());
							if (fieldDesc.getHandler() != null) {
								// 通过自定义方式处理数据
								value = fieldDesc.getHandler().process(obj);
							} else {
								if (!fieldDesc.isCode()) {
									if (fieldDesc.isConvertToString()) {
										// 转换成文本显示
										value = "=\"" + value + "\"";
									}
								} else {
									// 代码项，需要翻译
									value = CodeHandler.convertSingleData(value, fieldDesc.getCodeItem());
								}
							}
						}
						// 将处理好的数据保存到数组元素中
						data[idx] = value;
					}
					// 将数据写入csv文件中
					wr.writeRecord(data);
				}
			} catch (IOException e) {
				// 写入失败，暂不做处理
				LOGGER.info(e.getMessage());
			}

			// 关闭文件流
			wr.close();
		}
		
		//压缩文件名 生成日间戳做为csv文件名，例如：201606140810.zip
		String zipFileName = Generator.getCurrentTimestamp() + ".zip";
		
		String dirPath=dirFile.getPath();
		//导出的文件路径数组
		pathNames=pathNames.substring(0, pathNames.length()-1);
		String[] pathNameArrs=pathNames.split(",");
		//System.out.println("压缩文件目录"+dirPath);
		ZipCompressor zc = new ZipCompressor(dirPath+File.separator+zipFileName); 
		zc.compress(pathNameArrs);


		// 准备将写完的csv文件，进行压缩，并输出到前台，直接下载
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName);
		response.setHeader("Expires", "0");
		// 为解决https下无法下载的问题，添加如下三个属性
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");

		try {

			// 文件
			File file = zc.getZipFile();
			ServletOutputStream ouputStream = response.getOutputStream();
			//ZipOutputStream zipOut = new ZipOutputStream(ouputStream);
			// 文件
			byte[] buf = new byte[BUFFER];

			// 被压缩的文件
			InputStream input = new FileInputStream(file);
			// 添加到压缩文件中
			//zipOut.putNextEntry(new ZipEntry(file.getName()));
			// 缓冲
			BufferedInputStream origin = new BufferedInputStream(input, BUFFER);

			int len;
			while ((len = origin.read(buf, 0, BUFFER)) != -1) {
				//zipOut.write(buf, 0, len);
				ouputStream.write(buf, 0, len);
			}
			// 关闭缓冲
			origin.close();
			// 关闭文件
			input.close();
			ouputStream.close();
		} catch (IOException e) {
			LOGGER.info(e.getMessage());
		}

		// 收尾工作，将前面生成的文件进行清除
		//删除目录
		deleteDir(dirFile);
	}

	
	public static String getValue(Object object, String memberName) {
		// 校验
		if (object == null) {
			return "";
		}
		if (memberName == null) {
			return "";
		}
		// 获取对应的Class
		Class<? extends Object> classType = object.getClass();
		try {
			// 构成getter方法的名字,"get"+成员变量首字母的大写+成员变量名称的剩余部分
			// String getMethodName = "get" +
			// memberName.substring(0,1).toUpperCase() +
			// memberName.substring(1);
			String getMethodName = buildGetMethodName(memberName);
			// 获得成员变量的get方法对象
			Method getMethod = classType.getMethod(getMethodName, new Class[] {});
			// 调用get方法，获得成员变量的值
			Object value = getMethod.invoke(object, new Object[] {});

			if (value == null) {
				return "";
			}
			if (value instanceof java.util.Date) {
				// 如果是日期Date类型，则进行格式化，转换成yyyy-MM-dd hh:mm:ss格式
				return DATE_FORMAT.format(value);
			}
			if (value instanceof Double || value instanceof Float) {
				// 如果是精度数值型，则进行格式化
				return DECIMAL_FORMAT.format(value);
			}
			// 其它类型，直接toString
			return value.toString();
		} catch (SecurityException e) {
			LOGGER.info(e.getMessage());
			return "无权操作";
		} catch (NoSuchMethodException e) {
			LOGGER.info(e.getMessage());
			return "成员变量" + memberName + "get方法不存在";
		} catch (IllegalAccessException e) {
			LOGGER.info(e.getMessage());
			return "成员变量" + memberName + "get方法无权限";
		} catch (IllegalArgumentException e) {
			LOGGER.info(e.getMessage());
			return "成员变量" + memberName + "get方法参数异常";
		} catch (InvocationTargetException e) {
			LOGGER.info(e.getMessage());
			return "成员变量" + memberName + "get方法异常";
		}
	}

	//
	public static String buildGetMethodName(String fieldName) {
		if (fieldName == null || "".equals(fieldName.trim())) {
			// 成员变量为null或空串，不处理
			return fieldName;
		}
		return buildGetterOrSetterMethodName("get", fieldName);
	}

	public static String buildSetMethodName(String fieldName) {
		if (fieldName == null || "".equals(fieldName.trim())) {
			// 成员变量为null或空串，不处理
			return fieldName;
		}
		return buildGetterOrSetterMethodName("set", fieldName);
	}

	private static String buildGetterOrSetterMethodName(String prefix, String fieldName) {
		if (isSingleFirstLetter(fieldName)) {
			// 首字母是单独的一个小字写母，其后第二个字母是大写
			return prefix + fieldName;
		}
		// 转换成 "get(或set)+首字母大写+其余部分"
		return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * 判断是否是“首字母是单个且小写，第二个字母大写”的格式
	 * <p>
	 * 用于处理一种特殊情况，如：gName这种命名方式，正常应该是userName这种，而不是只有一个小写字母
	 * 
	 * @param fieldName
	 * @return
	 */
	private static boolean isSingleFirstLetter(String fieldName) {
		// 正则表达式
		String regex = "[a-z]{1}[A-Z]{1}\\S*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(fieldName);
		return m.matches();
	}

	/*public static void export_setting_report(HttpServletRequest request, HttpServletResponse response,
			List<FieldDesc> headers, List<String> headerNames, List<FcxCommisionSetting> datas) {
		// 参数校验
		if (request == null) {
			return;
		}
		if (response == null) {
			return;
		}
		if (headers == null || headers.isEmpty()) {
			return;
		}
		// 准备生成csv文件名以及服务器上的存储目录
		ServletContext servletContext = request.getSession().getServletContext();
		// 获得服务器上的绝对目录位置
		String rootPath = servletContext.getRealPath(DOWNLAOD_ROOT);

		String zipPath = "";
		String csvFileName = "";
		File dirFile = null;

		do {
			// 生成随机32位字符做为目录，为了避免重复
			zipPath = Generator.getRStr(32);
			// 生成子目录
			dirFile = new File(rootPath + File.separator + zipPath);
			// 如果目录存在，则重新生成一个目录
		} while (dirFile.exists());

		// 创建目录
		dirFile.mkdir();

		// 生成日间戳做为csv文件名，例如：201606140810.csv
		csvFileName = Generator.getCurrentTimestamp();
		// 生成csv文件的全路径名
		String csvFilePath = rootPath + File.separator + zipPath + File.separator + csvFileName + ".csv";

		// 创建csv文件
		CsvWriter wr = new CsvWriter(csvFilePath, ',', Charset.forName("GBK"));

		// 将数据写入到csv文件中
		try {
			// 写入中文列头信息
			String[] arrHeaderName = (String[]) headerNames.toArray(new String[0]);
			// 列数
			int columnCount = headers.size();
			wr.writeRecord(arrHeaderName);
			// 写入数据
			// 生成用于存放数据的String数组
			String[] data = new String[headers.size()];
			// 遍历数据集
			FieldDesc fieldDesc = null;
			String value = "";

			for (FcxCommisionSetting obj : datas) {
				// 列数
				columnCount = headers.size();
				
				// 写入数据
				// 生成用于存放数据的String数组
				data = new String[headers.size()];

				for (int idx = 0; idx < columnCount; idx++) {
					// 获得字段的描述对象，用于判断是否需要翻译
					fieldDesc = headers.get(idx);
				
					if (fieldDesc.isFixed()) {
						// 显示固定的值,不需要通过get方法取值
						value = fieldDesc.getFixedValue();
					} else {
						value=getDataValue(obj, fieldDesc);
						if((idx==4||idx==5)&&!obj.getCode().equals("1234")){
							value="";
						}

						if((idx==6||idx==7)&&!obj.getCode().equals("0668")){
							value="";
						}

						if((idx==8||idx==9)&&!obj.getCode().equals("0817")){
							value="";
						}

						if((idx==10||idx==11)&&!obj.getCode().equals("0609")){
							value="";
						}
						
					}
					
					// 将处理好的数据保存到数组元素中
					data[idx] = value;
				}
				// 将数据写入csv文件中
				wr.writeRecord(data);
			}
		} catch (IOException e) {
			// 写入失败，暂不做处理
			LOGGER.info(e.getMessage());
		}

		// 关闭文件流
		wr.close();

		// 准备将写完的csv文件，进行压缩，并输出到前台，直接下载
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + csvFileName + ".zip");
		response.setHeader("Expires", "0");
		// 为解决https下无法下载的问题，添加如下三个属性
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		// 文件
		File file = new File(csvFilePath);
		try {
			ServletOutputStream ouputStream = response.getOutputStream();
			ZipOutputStream zipOut = new ZipOutputStream(ouputStream);

			// 文件
			byte[] buf = new byte[BUFFER];

			// 被压缩的文件
			InputStream input = new FileInputStream(file);
			// 添加到压缩文件中
			zipOut.putNextEntry(new ZipEntry(file.getName()));
			// 缓冲
			BufferedInputStream origin = new BufferedInputStream(input, BUFFER);

			int len;
			while ((len = origin.read(buf, 0, BUFFER)) != -1) {
				zipOut.write(buf, 0, len);
			}
			// 关闭缓冲
			origin.close();
			// 关闭文件
			input.close();
			zipOut.close();
			ouputStream.close();
		} catch (IOException e) {
			LOGGER.info(e.getMessage());
		}

		// 收尾工作，将前面生成的临时文件进行清除
		// (1)删除文件
		File delFile = new File(csvFilePath);
		delFile.delete();
		// (2)删除目录
		File delDir = new File(rootPath + File.separator + zipPath);
		delDir.delete();
	}*/
	
	
	/*public static String getDataValue(FcxCommisionSetting obj,FieldDesc fieldDesc ){
		// 先通过get方法取值
		String value = getValue(obj, fieldDesc.getFieldName());
		if (fieldDesc.getHandler() != null) {
			// 通过自定义方式处理数据
			value = fieldDesc.getHandler().process(obj);
		} else {
			if (!fieldDesc.isCode()) {
				if (fieldDesc.isConvertToString()) {
					// 转换成文本显示
					value = "=\"" + value + "\"";
				}
			} else {
				// 代码项，需要翻译
				value = CodeHandler.convertSingleData(value, fieldDesc.getCodeItem());
			}
		}
		return value;
	}*/
	
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}

