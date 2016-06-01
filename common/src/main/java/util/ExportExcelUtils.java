package util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.huawei.fda.common.util.ExportExcelUtils
 *
 * @author Huawei
 * @version v1.0
 * @date 2015/11/18 0018 14:14
 * @update
 */
public class ExportExcelUtils {

	private static final String FTLPATH = "/ftl/excel";
	private static final String SUFFIX = ".ftl";
	private static final int maxRowNum = 60000;


	/**
	 * Excel 导出，
	 * 不指定的页面，自动分页
	 * @param response
	 * @param data 数据
	 * @param templateName 模块名称
	 * @param fieName 导出文件名
	 */
	public static void exportExcelAutoPage(HttpServletResponse response,List data,String templateName,String fieName) {
		if (null != data && data.size() > 0) {
			int sheetCount = (data.size() % maxRowNum == 0) ? (data.size() / maxRowNum) : ((data.size() / maxRowNum) + 1);
			List<Worksheet> sheet = new ArrayList<Worksheet>();
			for (int i = 0; i < sheetCount; i++) {
				Worksheet worksheet = new Worksheet();
				worksheet.setSheet("第" + (i + 1) + "页");
				List rowData = null;
				if ( data.size() > maxRowNum) {
					//得到数据
					int begin = i * maxRowNum;
					int end = (i + 1) * maxRowNum;
					if (i == (sheetCount - 1)) {
						end = data.size();
					}
					rowData = data.subList(begin, end);
				} else {
					rowData = data;
				}

				worksheet.setRows(rowData);
				sheet.add(worksheet);
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("sheet", sheet);


			Configuration configuration = FreeMarkerUtils.getConfiguration();
			try {
				response.setHeader("Content-disposition", "attachment; filename=" + new String(fieName.getBytes("gb2312"), "ISO-8859-1") + DateUtils.getCurrentTime() + ".xml");// 设定输出文件头
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.setContentType("text/xml");// 定义输出类型application/msexcel
			configuration.setClassForTemplateLoading(ExportExcelUtils.class, FTLPATH);  //FTL文件所存在的位置
			Template template = null;
			Writer out = null;
			try {
				template = configuration.getTemplate(templateName + SUFFIX); //文件名
				out = response.getWriter();
				template.process(dataMap, out);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}


	/**
	 * Excel 导出，
	 * 指定页面
	 * @param response
	 * @param data 数据
	 * @param templateName 模块名称
	 * @param fieName 导出文件名
	 */
	public static void exportExcelManualPage(HttpServletResponse response,List data,String templateName,String fieName) {
		if (null != data && data.size() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("sheet", data);
			Configuration configuration = FreeMarkerUtils.getConfiguration();
			try {
				response.setHeader("Content-disposition", "attachment; filename=" + new String(fieName.getBytes("gb2312"), "ISO-8859-1")+"-"+ DateUtils.getCurrentTime() + ".xml");// 设定输出文件头
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.setContentType("text/xml");// 定义输出类型
			configuration.setClassForTemplateLoading(ExportExcelUtils.class, FTLPATH);  //FTL文件所存在的位置
			Template template = null;
			Writer out = null;
			try {
				template = configuration.getTemplate(templateName + SUFFIX); //文件名
				out = response.getWriter();
				template.process(dataMap, out);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public static void exportTestItemList(HttpServletResponse response, List data, String templateName, String fieName) {
		if (null != data && data.size() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("testItemList", data);
			Configuration configuration = FreeMarkerUtils.getConfiguration();
			try {
				response.setHeader("Content-disposition", "attachment; filename=" + new String(fieName.getBytes
						("gb2312"), "ISO-8859-1") + "-" + DateUtils.getCurrentTime() + ".csv");// 设定输出文件头
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
//			response.setContentType("text/xml");// 定义输出类型
			configuration.setClassForTemplateLoading(ExportExcelUtils.class, "/email");  //FTL文件所存在的位置
			Template template = null;
			Writer out = null;
			try {
				template = configuration.getTemplate(templateName + SUFFIX); //文件名
				template.setEncoding("UTF-8");
				response.setCharacterEncoding("GBK");
				out = response.getWriter();
				template.process(dataMap, out);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public static void exportICTReportDetailList(HttpServletResponse response, List data, String templateName, String
			fieName) {
		if (null != data && data.size() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("ictDetailList", data);
			Configuration configuration = FreeMarkerUtils.getConfiguration();
			try {
				response.setHeader("Content-disposition", "attachment; filename=" + new String(fieName.getBytes
						("gb2312"), "ISO-8859-1") + "-" + DateUtils.getCurrentTime() + ".csv");// 设定输出文件头
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
//			response.setContentType("text/xml");// 定义输出类型
			configuration.setClassForTemplateLoading(ExportExcelUtils.class, "/email");  //FTL文件所存在的位置
			Template template = null;
			Writer out = null;
			try {
				template = configuration.getTemplate(templateName + SUFFIX); //文件名
				template.setEncoding("UTF-8");
				response.setCharacterEncoding("GBK");
				out = response.getWriter();
				template.process(dataMap, out);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public static void exportHTFaultModelMappingList(HttpServletResponse response, List data, String templateName,
													 String fieName) {
		if (null != data && data.size() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("hardTestFaultModelMappingList", data);
			Configuration configuration = FreeMarkerUtils.getConfiguration();
			try {
				response.setHeader("Content-disposition", "attachment; filename=" + new String(fieName.getBytes
						("gb2312"), "ISO-8859-1") + "-" + DateUtils.getCurrentTime() + ".csv");// 设定输出文件头
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
//			response.setContentType("text/xml");// 定义输出类型
			configuration.setClassForTemplateLoading(ExportExcelUtils.class, "/email");  //FTL文件所存在的位置
			Template template = null;
			Writer out = null;
			try {
				template = configuration.getTemplate(templateName + SUFFIX); //文件名
				template.setEncoding("UTF-8");
				response.setCharacterEncoding("GBK");
				out = response.getWriter();
				template.process(dataMap, out);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}


}
