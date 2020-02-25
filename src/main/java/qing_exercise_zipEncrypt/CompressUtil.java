package qing_exercise_zipEncrypt;

import net.lingala.zip4j.ZipFile;
//import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

//import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * ZIP压缩文件操作工具类
 * 支持密码
 * 依赖zip4j开源项目(http://www.lingala.net/zip4j/)
 * 版本1.3.1
 */
public class CompressUtil {
	/** 
	 * logger
	 */
	protected static Logger logger = Logger.getLogger(CompressUtil.class);
	@Test
	public void testUncompressZip() {
		File file = new File("D:/TT/rt.txt.zip");
		System.out.println(file.getParentFile().getPath());
		String temp = "D:/TT/";
		try {
			uncompressZip(file, temp);
		} catch (Exception e) {
			logger.error("解压失败！");
			e.printStackTrace();
		}
	}

	/**
	 * 解压zip压缩包 jdk1.5
	 * @param storeFile 待解压的zip文件路径 如D:\\TT\\rt.txt.zip || D:/TT/rt.txt.zip* 
	 * @param tempPath  解压后的文件路径 如：D:\\TT\\ || D:/TT/
	 * @throws Exception
	 */
	public static void uncompressZip(File storeFile, String tempPath) throws Exception {
		File file = storeFile;
		String temp = tempPath;
		FileInputStream fis = null;
		ZipInputStream zins = null;
		// 解压缩
		try {
			fis = new FileInputStream(file);
			zins = new ZipInputStream(fis);
			ZipEntry ze = null;
			byte[] ch = new byte[256];
			while ((ze = zins.getNextEntry()) != null) {
				File zfile = new File(temp + "/" + ze.getName());
				File fpath = new File(zfile.getParentFile().getPath());
				if (ze.isDirectory()) {
					if (!zfile.exists())
						zfile.mkdirs();
					zins.closeEntry();
				} else {
					if (!fpath.exists())
						fpath.mkdirs();
					FileOutputStream fouts = new FileOutputStream(zfile);
					int i;
					while ((i = zins.read(ch)) != -1)
						fouts.write(ch, 0, i);
					zins.closeEntry();
					fouts.close();
				}
			}
			fis.close();
			zins.close();
		} catch (Exception e) {
			e.printStackTrace();
			file.delete();
			throw new RuntimeException("文件" + file.toString() + "解压失败，原因：" + e.toString());
		}
	}
	@Test
	public void testTarFile() {
		String filePath = "D:/TT/";
		String[] fileName = { "ss.txt", "st.txt" };
		String tarName = "all.zip";
		try {
			tarFile(filePath, fileName, tarName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 将多个文件压缩成一个zip jdk 1.5		 
	 * @param filePath 要压缩的文件存在的路径 如：D:/TT/	 * 
	 * @param fileName 要压缩的文件数组 如：ss.txt、st.txt	 * 
	 * @param tarName  压缩后的压缩文件名称 如：all.zip	 * 
	 * @return	 * 
	 * @throws Exception	 * 
	 */
	public static String tarFile(String filePath, String[] fileName, String tarName) throws Exception {
		String[] filenames = fileName;
		byte[] buf = new byte[1024];
		String outFilename = tarName;
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filePath + File.separator + outFilename));
		for (int i = 0; i < filenames.length; i++) {
			FileInputStream in = new FileInputStream(filePath + File.separator + filenames[i]);
			out.putNextEntry(new ZipEntry(filenames[i]));
			int len = 0;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();
		}
		out.close();
		return filePath;
	}
	@Test
	public void testZipWithPaw() {
		String zipFile = "D:/TT/st.txt.zip";
		String filePath = "D:/TT/";
		zipWithPaw(zipFile, filePath);
	}
	/**	 * 
	 * zip加密压缩 zip4j_1.3.1.jar	 * 
	 * @param zipfile  压缩文件完整路径（包含文件名）	 * 
	 * @param filePath 压缩文件路径	 * 
	 * @return	 * 
	 */
	public static String zipWithPaw(String zipfile, String filePath) {
		try {
			/*
			 * ZipFile zipFile = new ZipFile("E:\\ZipTest\\test.zip");
			 * 
			 * File folderToAdd =new File("E:\\ZipTest");
			 */
			ZipFile zipFile = new ZipFile(zipfile);
			File folderToAdd = new File(filePath);
			String pawd = "123456";// 加密密码
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(CompressionMethod.DEFLATE);
			parameters.setCompressionLevel(CompressionLevel.NORMAL);
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(EncryptionMethod.AES);
			parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
			
//			parameters.setPassword(pawd);
			
			zipFile.addFolder(folderToAdd, parameters);
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return zipfile;
	}
	@Test
	public void testZip() {
//        zip("D:/TT/st.txt");
//        zip("D:/TT/st.txt", "111");
//        System.out.println(File.separator);
//        zip("D:/TT/st.txt", "D:\\TT\\", "222");
		zip("D:/TT/st.txt", "D:\\TT\\", false, "222");
	}
	/**	 * 
	 * 压缩指定文件到当前文件夹	 * 
	 * 如：D:/TT/st.txt 当前文件夹为D:/TT/st.zip	 * 
	 * @param src 要压缩的指定文件
	 * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.	 * 
	 */
	public static String zip(String src) {
		return zip(src, null);
	}
	/**	 * 
	 * 使用给定密码压缩指定文件或文件夹到当前目录	 *	 * 	 * 
	 * @param src    要压缩的文件	 * 
	 * @param passwd 压缩使用的密码	 * 
	 * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.	 * 
	 */
	public static String zip(String src, String passwd) {
		return zip(src, null, passwd);
	}
	/**
	 * 
	 * 使用给定密码压缩指定文件或文件夹到当前目录	 * 
	 * @param src    要压缩的文件	 * 
	 * @param dest   压缩文件存放路径	 * 
	 * @param passwd 压缩使用的密码	 * 
	 * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.	 * 
	 */
	public static String zip(String src, String dest, String passwd) {
		return zip(src, dest, true, passwd);
	}
	/**
	 * 
	 * 使用给定密码压缩指定文件或文件夹到指定位置.	 * 
	 * <p/>	 * 
	 * dest可传最终压缩文件存放的绝对路径,也可以传存放目录,也可以传null或者"".<br />	 * 	 * 如果传null或者""则将压缩文件存放在当前目录,即跟源文件同目录,压缩文件名取源文件名,以.zip为后缀;<br />	 * 
	 * 如果以路径分隔符(File.separator)结尾,则视为目录,压缩文件名取源文件名,以.zip为后缀,否则视为文件名.	 * 
	 * @param src         要压缩的文件或文件夹路径 * 
	 * @param dest        压缩文件存放路径 * 
	 * @param isCreateDir 是否在压缩文件里创建目录,仅在压缩文件为目录时有效.<br /> * 
	 *                    如果为false,将直接压缩目录下文件到压缩文件.	 * 
	 * @param passwd      压缩使用的密码 * 
	 * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.	 * 
	 */
	public static String zip(String src, String dest, boolean isCreateDir, String passwd) {
		File srcFile = new File(src);
		dest = buildDestinationZipFilePath(srcFile, dest);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(CompressionMethod.DEFLATE); // 压缩方式
		parameters.setCompressionLevel(CompressionLevel.NORMAL); // 压缩级别
		if (!StringUtils.isEmpty(passwd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD); // 加密方式
			
//			parameters.setPassword(passwd.toCharArray());
			
		}
		try {
			ZipFile zipFile = new ZipFile(dest);
			if (srcFile.isDirectory()) {
				// 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
				if (!isCreateDir) {
					File[] subFiles = srcFile.listFiles();
					ArrayList<File> temp = new ArrayList<File>();
					Collections.addAll(temp, subFiles);
					zipFile.addFiles(temp, parameters);
					return dest;
				}
				zipFile.addFolder(srcFile, parameters);
			} else {
				zipFile.addFile(srcFile, parameters);
			}
			return dest;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return src;
	}
	/*** 
	 * 构建压缩文件存放路径,如果不存在将会创建
	 * 传入的可能是文件名或者目录,也可能不传,此方法用以转换最终压缩文件的存放路径
	 * @param srcFile   源文件
	 * @param destParam 压缩目标路径
	 * @return 正确的压缩文件存放路径
	 */
	private static String buildDestinationZipFilePath(File srcFile, String destParam) {
		if (StringUtils.isEmpty(destParam)) {
			if (srcFile.isDirectory()) {
				destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
			} else {
				String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				destParam = srcFile.getParent() + File.separator + fileName + ".zip";
			}
		} else {
			createDestDirectoryIfNecessary(destParam); // 在指定路径不存在的情况下将其创建出来
			if (destParam.endsWith(File.separator)) {
				String fileName = "";
				if (srcFile.isDirectory()) {
					fileName = srcFile.getName();
				} else {
					fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				}
				destParam += fileName + ".zip";
			}
		}
		return destParam;
	}
	/**
	 * 
	 * 在必要的情况下创建压缩文件存放目录,比如指定的存放路径并没有被创建
	 * @param destParam 指定的存放路径,有可能该路径并没有被创建
	 */
	private static void createDestDirectoryIfNecessary(String destParam) {
		File destDir = null;
		if (destParam.endsWith(File.separator)) {
			destDir = new File(destParam);
		} else {
			destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}
}
