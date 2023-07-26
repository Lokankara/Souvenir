package com.storage.web;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR =
            "src\\main\\webapp\\WEB-INF\\static";
    private static PageGenerator pageGenerator;
    private final Configuration configuration;

    private PageGenerator() {
        configuration = new Configuration(Configuration.VERSION_2_3_19);
    }

    public static PageGenerator instance() {
        return pageGenerator == null ? new PageGenerator() : pageGenerator;
    }

    public String getPage(String filename) {
        return getPage(filename, Collections.emptyMap());
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = configuration.getTemplate(HTML_DIR + File.separator + filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            System.err.println(e.getMessage());
        }
        return stream.toString();
    }
}