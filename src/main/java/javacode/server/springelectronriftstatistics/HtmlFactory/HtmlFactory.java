package javacode.server.springelectronriftstatistics.HtmlFactory;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import javacode.server.springelectronriftstatistics.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class HtmlFactory {
    private static final String DEFAULT_USER_IMAGE = "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";
    Configuration cfg;
    private static HtmlFactory instance;

    private HtmlFactory() throws IOException {
        cfg = new Configuration(Configuration.VERSION_2_3_31);
        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(this.getClass().getResource("/templates").getPath()));
        cfg.setTemplateLoader(fileTemplateLoader);
    }

    public static synchronized HtmlFactory getInstance() throws IOException {
        if (instance == null) {
            instance = new HtmlFactory();
        }
        return instance;
    }

    public String  loginPageAction (User user) {
        try {
            FileWriter fileWriter = new FileWriter("userInfo.txt");
            fileWriter.write(user.toString());
            String html;
            String img = "data:image/jpeg;base64,";
            byte[] bytesImg = user.getAccountimage();
            String base64Img;
            if (bytesImg == null) {
                base64Img = DEFAULT_USER_IMAGE;
            }else {
                base64Img = java.util.Base64.getEncoder().encodeToString(bytesImg);
            }
            img += base64Img;

            Template template = cfg.getTemplate("loginPageAction.ftl");
            Map<String, Object> data = new HashMap<>();
            data.put("Username", user.getUsername());
            data.put("UsernamePhoto", img);

            StringWriter out = new StringWriter();
            template.process(data, out);
            html = out.toString();

            return html;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
