package com.eastcom.ripple.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/23
 * @description 导出csv工具类
 */
@Slf4j
public class CsvExportUtil {
    //行尾分隔符定义
    private final static String NEW_LINE_SEPARATOR = "\n";
    //上传文件的存储位置，得到当前的classpath的绝对路径的URI表示法
    private final static URL PATH = Thread.currentThread().getContextClassLoader().getResource("");

    /**
     * @return File
     * @Description 创建CSV文件
     * @Param fileName 文件名，head 表头，values 表体
     **/
    public static File makeTempCSV(String fileName, Object[] head, List<Object[]> values) throws IOException {
        //        创建临时文件
        File file = File.createTempFile(fileName, ".csv", new File(PATH.getPath()));
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

        BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        CSVPrinter printer = new CSVPrinter(bufferedWriter, formator);

        //        写入表头
        printer.printRecord(head);

        //        写入内容
        for (Object[] value : values) {
            printer.printRecord(value);
        }

        printer.close();
        bufferedWriter.close();
        return file;
    }

    /**
     * @return boolean
     * @Description 下载文件
     * @Param response，file
     **/
    public static boolean downloadFile(HttpServletResponse response, File file) {
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        OutputStream os = null;
        try {
            fileInputStream = new FileInputStream(file);
            log.info("==="+file.getName()+"==="+file.getPath()+"====");
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            os = response.getOutputStream();
            //MS产本头部需要插入BOM
            //如果不写入这几个字节，会导致用Excel打开时，中文显示乱码
            os.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            byte[] buffer = new byte[1024];
            int i = bufferedInputStream.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                os.flush();
                i = bufferedInputStream.read(buffer);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            //关闭流
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            file.delete();
        }
        return false;
    }

    /**
     * @return File
     * @Description 上传文件
     * @Param multipartFile
     **/
    public static File uploadFile(MultipartFile multipartFile) {
        String path = PATH.getPath() + multipartFile.getOriginalFilename();
        try {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            multipartFile.transferTo(file);
            log.info("上传文件成功，文件名===>" + multipartFile.getOriginalFilename() + ", 路径===>" + file.getPath());
            return file;
        } catch (IOException e) {
            log.error("上传文件失败" + e.getMessage(), e);
            return null;
        }

    }

    /**
     * @return List<List<String>>
     * @description 读取CSV文件的内容（不含表头）
     * @Param filePath 文件存储路径，colNum 列数
     **/
    public static List<List<String>> readCSV(String filePath, int colNum) {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            CSVParser parser = CSVFormat.DEFAULT.parse(bufferedReader);
            //          表内容集合，外层List为行的集合，内层List为字段集合
            List<List<String>> values = new ArrayList<>();
            int rowIndex = 0;

            for (CSVRecord record : parser.getRecords()) {
                //              跳过表头
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                //              每行的内容
                List<String> value = new ArrayList<>(colNum + 1);
                for (int i = 0; i < colNum; i++) {
                    value.add(record.get(i));
                }
                values.add(value);
                rowIndex++;
            }
            return values;
        } catch (IOException e) {
            log.error("解析CSV内容失败" + e.getMessage(), e);
        }finally {
            //关闭流
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 设置Header，响应请求头
     *
     * @param fileName
     * @param response
     * @throws UnsupportedEncodingException
     * @return 返回拼接后的文件名
     */
    public static String responseSetProperties(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //拼接文件名
        String fn = fileName + sdf.format(new Date());
        // 读取字符编码
        String utf = "UTF-8";

        // 设置响应
        response.setCharacterEncoding("utf-8");
        //传递的文件类型二进制流文件
        response.setContentType("application/octet-stream");
        // 设置Content-Disposition，告诉浏览器下载资源的默认文件名
        // URLEncoder.encode(fileName, "UTF-8") 对文件名进行编码设置，使得返回前台不会乱码
        response.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(fn, utf)+".csv");
        //让浏览器能访问到其他的响应头
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        return fn;
    }

}
