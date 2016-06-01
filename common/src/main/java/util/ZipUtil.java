package util;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


/**
 * Zip文件工具类
 *
 * @author Luxh
 */
public class ZipUtil {
	private final static Log log = LogFactory.getLog(ZipUtil.class);

	/**
	 * 把文件压缩成zip格式
	 *
	 * @param files       需要压缩的文件
	 * @param zipFilePath 压缩后的zip文件路径 ,如"D:/test/aa.zip";
	 */
	public static void compressFiles2Zip(File[] files, String zipFilePath) {
		if (files != null && files.length > 0) {
			if (isEndsWithZip(zipFilePath)) {
				ZipArchiveOutputStream zaos = null;
				try {
					File zipFile = new File(zipFilePath);
					zaos = new ZipArchiveOutputStream(zipFile);
//Use Zip64 extensions for all entries where they are required
					zaos.setUseZip64(Zip64Mode.AsNeeded);

//将每个文件用ZipArchiveEntry封装
//再用ZipArchiveOutputStream写到压缩文件中
					for (File file : files) {
						if (file != null) {
							ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getName());
							zaos.putArchiveEntry(zipArchiveEntry);
							InputStream is = null;
							try {
								is = new FileInputStream(file);
								byte[] buffer = new byte[1024 * 5];
								int len = -1;
								while ((len = is.read(buffer)) != -1) {
//把缓冲区的字节写入到ZipArchiveEntry
									zaos.write(buffer, 0, len);
								}
//Writes all necessary data for this entry.
								zaos.closeArchiveEntry();
							} catch (Exception e) {
								throw new RuntimeException(e);
							} finally {
								if (is != null)
									is.close();
							}

						}
					}
					zaos.finish();
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					try {
						if (zaos != null) {
							zaos.close();
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}

			}

		}

	}


	/**
	 * 把zip文件解压到指定的文件夹
	 *
	 * @param zipFilePath zip文件路径, 如 "D:/test/aa.zip"
	 * @param saveFileDir 解压后的文件存放路径, 如"D:/test/"
	 */
	public static void decompressZip(String zipFilePath, String saveFileDir) {
		File saveFileDirFile = new File(saveFileDir);
		if (!saveFileDirFile.exists()) {
			boolean ret = saveFileDirFile.mkdirs();
			if (!ret) {
				log.warn("error when mkdir  descDir");
			}
		}

		if (isEndsWithZip(zipFilePath)) {
			File file = new File(zipFilePath);
			if (file.exists()) {
				InputStream is = null;
//can read Zip archives
				ZipArchiveInputStream zais = null;
				try {
					is = new FileInputStream(file);
					zais = new ZipArchiveInputStream(is);
					ArchiveEntry archiveEntry = null;
//把zip包中的每个文件读取出来
//然后把文件写到指定的文件夹
					while ((archiveEntry = zais.getNextEntry()) != null) {
//获取文件名
						String entryFileName = archiveEntry.getName();
//构造解压出来的文件存放路径
						String entryFilePath = saveFileDir + entryFileName;
						byte[] content = new byte[(int) archiveEntry.getSize()];
						zais.read(content);
						OutputStream os = null;
						try {
//把解压出来的文件写到指定路径
							File entryFile = new File(entryFilePath);
							os = new FileOutputStream(entryFile);
							os.write(content);
						} catch (IOException e) {
							throw new IOException(e);
						} finally {
							if (os != null) {
								os.flush();
								os.close();
							}
						}

					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					try {
						if (zais != null) {
							zais.close();
						}
						if (is != null) {
							is.close();
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}


	/**
	 * 判断文件名是否以.zip为后缀
	 *
	 * @param fileName 需要判断的文件名
	 * @return 是zip文件返回true, 否则返回false
	 */
	public static boolean isEndsWithZip(String fileName) {
		boolean flag = false;
		if (fileName != null && !"".equals(fileName.trim())) {
			if (fileName.endsWith(".ZIP") || fileName.endsWith(".zip")) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 解压文件到指定目录
	 *
	 * @param zipFilePath
	 * @param descDir
	 * @author isea533
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(String zipFilePath, String descDir) throws IOException {
		File zipFile = new File(zipFilePath);
		String lastStr = descDir.substring(descDir.length() - 1);
		if (!lastStr.equals("/") && !lastStr.equals("\\")) {
			descDir += "/";
		}
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			boolean ret = pathFile.mkdirs();
			if (!ret) {
				log.warn("error unzip descDir error");
			}
		}
		ZipFile zip = null;
		try {
			zip = new ZipFile(zipFile, Charset.forName("GBK"));
			for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream in = zip.getInputStream(entry);
				String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
				//判断路径是否存在,不存在则创建文件路径
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				if (!file.exists()) {
					boolean ret = file.mkdirs();
					if (!ret) {
						log.warn("error unzip descDir error2");
					}
				}
				//判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}
				//输出文件路径信息
				OutputStream out = null;

				try {
					out = new FileOutputStream(outPath);
					byte[] buf1 = new byte[1024];
					int len;
					while ((len = in.read(buf1)) > 0) {
						out.write(buf1, 0, len);
					}
					in.close();
					out.close();
				} catch (IOException e) {
					log.error("unzip error", e);
				} finally {
					try {
						if (out != null) {
							out.close();
						}
					} catch (IOException e) {
						log.error("close out error", e);
					} finally {
						try {
							if (in != null) {
								in.close();
							}
						} catch (IOException e) {
							log.error("close in error", e);
						}
					}

				}
			}
		} finally {
			if (zip != null) {
				zip.close();
			}
		}
		log.info("******************解压完毕********************");
	}


	public static boolean zipFileList(List<File> sourceFiles, String zipFilePath, String fileName) {
		boolean flag = false;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			File zipFilePathFile = new File(zipFilePath);
			if (!zipFilePathFile.exists()) {
				boolean ret = zipFilePathFile.mkdirs();
				if (!ret) {
					log.warn("error when mkdir  descDir");
					return flag;
				}
			}
			File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
			if (null == sourceFiles || sourceFiles.size() < 1) {
				log.error("打包的文件列表为空！！");
				return flag;
			} else {
				fos = new FileOutputStream(zipFile);
				zos = new ZipOutputStream(new BufferedOutputStream(fos));
				byte[] bufs = new byte[1024 * 10];
				for (int i = 0; i < sourceFiles.size(); i++) {
					// 创建ZIP实体,并添加进压缩包
					ZipEntry zipEntry = new ZipEntry(sourceFiles.get(i).getName());
					zos.putNextEntry(zipEntry);
					// 读取待压缩的文件并写进压缩包里
					fis = new FileInputStream(sourceFiles.get(i));
					bis = new BufferedInputStream(fis, 1024 * 10);
					int read = 0;
					while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
						zos.write(bufs, 0, read);
					}
				}
				flag = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("文件找不到");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("ioException!!!");
			return false;
		} finally {
			// 关闭流
			try {
				if (null != bis) bis.close();
				if (null != zos) zos.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("关闭文件流时出错！！");
			}
		}
		return flag;
	}

}
  
		  
		  
		  