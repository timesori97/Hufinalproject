package www.silver.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;

@Controller
public class FileDataUtil {

    // ���ε� �� �̹��� Ȯ���� Ȯ�ο� ����Ʈ
    private ArrayList<String> extNameArray = new ArrayList<String>() {
        {
            add("gif");
            add("jpg");
            add("png");
        }
    };

    @Resource(name = "uploadPath")
    private String uploadPath;

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @RequestMapping(value="/download", method=RequestMethod.GET) // mapping
    @ResponseBody  // return type�� view�� �ƴ�, ������ ��ü�� return type�� �������� ������(���� �ٿ�)
    public FileSystemResource fileDownload(@RequestParam("filename") String fileName, HttpServletResponse response) {
        File file = new File(uploadPath + "/" + fileName);
        response.setContentType("application/download; utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        return new FileSystemResource(file);
    }

    // �޼ҵ带 ȣ���� ���� �Ű������� ��ȯ�� ������ �� �̸� ��ȯ
    public List<String> fileUpload(MultipartFile[] file) throws IOException {
        List<String> files = new ArrayList<>();
        for (int i = 0; i < file.length; i++) {
            if (file[i].getOriginalFilename() != "") {  
                String originalName = file[i].getOriginalFilename();
                // ���� Ȯ���� �˻�
                String ext = originalName.substring(originalName.lastIndexOf('.') + 1).toLowerCase();
                if (!extNameArray.contains(ext)) {
                    throw new IOException("������ �ʴ� ���� Ȯ����: " + ext);
                }
                UUID uid = UUID.randomUUID();
                String saveName = uid.toString() + "." + originalName.split("\\.")[1];
                byte[] fileData = file[i].getBytes();
                
                File target = new File(uploadPath, saveName);
                FileCopyUtils.copy(fileData, target);
                files.add(saveName);
            }			
        }		
        return files;
    }
    
    public String fileUpload(MultipartFile singleFile) throws IOException {
        if (singleFile != null && !singleFile.isEmpty()) {
            String originalName = singleFile.getOriginalFilename();
            
            // ���� Ȯ���� �˻�
            String ext = originalName.substring(originalName.lastIndexOf('.') + 1).toLowerCase();
            if (!extNameArray.contains(ext)) {
                throw new IOException("������ �ʴ� ���� Ȯ����: " + ext);
            }
            
            // ������ ���ϸ� ����
            UUID uid = UUID.randomUUID();
            // originalName.split("\\.")[1] ���� �� ����� �� �����մϴ�.
            String saveName = uid.toString() + "." + ext;
            
            byte[] fileData = singleFile.getBytes();
            
            File target = new File(uploadPath, saveName);
            FileCopyUtils.copy(fileData, target);
            
            return saveName;
        }
        return null; // ������ ���� ��� null ��ȯ
    }

    // ���ε� �� �̹��� Ȯ���� ����Ʈ ��ȯ
    public ArrayList<String> getExtNameArray() {
        return extNameArray;
    }

    public void setExtNameArray(ArrayList<String> extNameArray) {
        this.extNameArray = extNameArray;
    }
}
