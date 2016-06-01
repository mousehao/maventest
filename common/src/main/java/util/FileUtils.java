package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huawei on 15/11/14.
 */
public class FileUtils {
	private final static Log log = LogFactory.getLog(FileUtils.class);

	public static String getFilePathBySuffixName(String boardFileDir, final String suffixName) {
		List<File> allFiles = getAllFiles(new File(boardFileDir));
		for (File f:allFiles){
			if(f.getName().endsWith(suffixName)){
				return f.getPath();
			}
		}
		return null;
	}
	private static List<File> getAllFiles(File file){
		List<File> result=new ArrayList<File>();
		File[] subFiles=file.listFiles();
		for(File f:subFiles) {
			if (!f.isDirectory()) {
				result.add(f);
			}else {
				result.addAll(getAllFiles(f));
			}
		}
		return result;
	}

	public static String getSimpleFileName(String filename) {
		int one = filename.lastIndexOf(".");
		return filename.substring(0, one);
	}

	public static String getFileName(String srcFilePath) {
		int one = srcFilePath.lastIndexOf("/");
		return srcFilePath.substring(one + 1, srcFilePath.length());
	}

	public static String getFileNameBySuffixName(String boardFileDir, final String suffixName) {
		List<File> allFiles = getAllFiles(new File(boardFileDir));
		for (File f:allFiles){
			if(f.getName().endsWith(suffixName)){
				return f.getName();
			}
		}
		return null;
	}

	public static void delFolder(String fileFolderpath) throws IOException {
		File f = new File(fileFolderpath);//定义文件路径
		if (f.exists() && f.isDirectory()) {//判断是文件还是目录
			File delFiles[] = f.listFiles();
			if (delFiles != null && delFiles.length == 0) {//若目录下没有文件则直接删除
				boolean result = f.delete();
				if (!result) {
					log.error("删除文件夹失败");
				}
			} else if (delFiles != null) {//若有则把文件放进数组，并判断是否有下级目录
				int i = delFiles.length;
				for (int j = 0; j < i; j++) {
					if (delFiles[j].isDirectory()) {
						delFolder(delFiles[j].getAbsolutePath());//递归调用del方法并取得子目录路径
					}
					boolean result = delFiles[j].delete();//删除文件
					if (!result) {
						log.error("删除子文件失败");

					}
				}
				boolean result = f.delete();
				if (!result) {
					log.error("删除文件夹失败");

				}
			}
		}
	}

	public static void createFolderIfNotExist(String folder) {
		File f = new File(folder);//定义文件路径
		if (!f.exists()) {
			boolean result = f.mkdirs();
			if (!result) {
				log.error("创建文件夹失败");

			}
		}
	}

	public static boolean isEncodingEqualUTF8(InputStream in) {
		try {
			byte[] b = new byte[3];
			in.read(b);
			if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
