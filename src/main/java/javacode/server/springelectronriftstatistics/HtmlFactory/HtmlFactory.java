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
            String base64Img = java.util.Base64.getEncoder().encodeToString(bytesImg);
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
