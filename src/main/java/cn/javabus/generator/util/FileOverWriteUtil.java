package cn.javabus.generator.util;

import java.io.*;

/**
 * @author ou.zhenxing on 2020-04-04.
 * 文件重写工具类,有些生成的东西是写死的,需要强制重写文件
 */
public class FileOverWriteUtil {




    /**
     * 自定义删除内容
     * import org.apache.ibatis.annotations.Param
     *
     * @param fileName
     * @Param("record") @Param("example")
     */
    public static void delParamAnnotation(String fileName) {
        if (fileName == null) {
            return;
        }
        System.out.println("开始自定义生成Dao文件: " + fileName);
        BufferedReader br = null;
        String line = null;
        StringBuffer buf = new StringBuffer();

        try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(fileName));

            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            int publicCount = 0;
            while ((line = br.readLine()) != null) {
                if (line.contains("import org.apache.ibatis.annotations.Param")) {
                    continue;//丢弃@Id
                }
                if (line.contains("@Param(\"record\")") || line.contains("@Param(\"example\")")) {
                    // 丢弃@Column注解和导入包
                    buf.append(line.replace("@Param(\"record\")", "")
                            .replace("@Param(\"example\")", "")).append("\r\n");
                } else {
                    // 如果不用修改, 则按原来的内容回写
                    buf.append(line).append("\r\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        write(fileName, buf.toString());
    }

    /**
     * 重写文件内容
     *
     * @param filePath 需要覆盖的文件
     * @param content  新的文件内容
     */
    public static void write(String filePath, String content) {
        BufferedWriter bw = null;

        try {
            // 根据文件路径创建缓冲输出流
            bw = new BufferedWriter(new FileWriter(filePath));
            // 将内容写入文件中
            bw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    bw = null;
                }
            }
        }
    }
}
