package cn.lcl.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {
    private static String filePath;
    private static String urlPath;

    /**
     * 上传文件 上传成功 则返回地址， 否则返回null
     *
     * @param file
     * @param newDirUrl 新建的文件夹
     * @return
     */
    public static String upload(MultipartFile file, String newDirUrl) throws IOException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取后缀
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // fileName处理
        String random = String.valueOf(UUID.randomUUID());
        // 客户端获取时的路径
        String url = urlPath + newDirUrl + "/" + random + fileName;
        // 在服务器中的根路径
        fileName = filePath + newDirUrl + "/" + random + fileName;
        // 文件对象
        File dest = new File(fileName);
        // 创建路径
        if (!dest.getParentFile().exists()) {
            boolean mkdir = dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return url;
    }


    // 重载
    public static String upload(MultipartFile file) throws IOException {
        return upload(file, "");
    }

    @Value("${file.filePath}")
    public void setFilePath(String filePath) {
        FileUtil.filePath = filePath;
    }

    @Value("${file.urlPath}")
    public void setUrlPath(String urlPath) {
        FileUtil.urlPath = urlPath;
    }
}
