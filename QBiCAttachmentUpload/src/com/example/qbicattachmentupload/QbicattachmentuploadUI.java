package com.example.qbicattachmentupload;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;

import main.OpenBisClient;
import main.UploadsPanel;
import model.AttachmentConfig;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("qbicattachmentupload")
public class QbicattachmentuploadUI extends UI {

  @WebServlet(value = "/*", asyncSupported = true)
  @VaadinServletConfiguration(productionMode = false, ui = QbicattachmentuploadUI.class)
  public static class Servlet extends VaadinServlet {
  }

  @Override
  protected void init(VaadinRequest request) {
    final VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    setContent(layout);

    Properties config = new Properties();
    try {
      List<String> configs =
          new ArrayList<String>(Arrays.asList("/Users/frieda/Desktop/dev software/liferay-portal-6.2-ce-ga4/qbic-ext.properties",
              "/home/rayslife/portlet.properties", "/usr/local/share/guse/portlets.properties",
              "/home/tomcat-liferay/liferay_production/portlets.properties"));
      for (String s : configs) {
        File f = new File(s);
        if (f.exists())
          config.load(new FileReader(s));
      }
      StringWriter configDebug = new StringWriter();
      config.list(new PrintWriter(configDebug));

      String TMP_FOLDER = "tmp.folder";
      String MAX_ATTACHMENT_SIZE = "max.attachment.size";
      String ATTACHMENT_URI = "attachment.uri";
      String ATTACHMENT_USER = "attachment.user";
      String ATTACHMENT_PASS = "attachment.password";
      String DATASOURCE_USER = "datasource.user";
      String DATASOURCE_PASS = "datasource.password";
      String DATASOURCE_URL = "datasource.url";
      String user = config.getProperty(DATASOURCE_USER);
      String pw = config.getProperty(DATASOURCE_PASS);
      String url = config.getProperty(DATASOURCE_URL);
      AttachmentConfig attachConfig =
          new AttachmentConfig(Integer.parseInt(config.getProperty(MAX_ATTACHMENT_SIZE)),
              config.getProperty(ATTACHMENT_URI), config.getProperty(ATTACHMENT_USER),
              config.getProperty(ATTACHMENT_PASS));

      OpenBisClient openbis = new OpenBisClient(user, pw, url);
      openbis.login();
      UploadsPanel panel =
          new UploadsPanel(config.getProperty(TMP_FOLDER), "CHICKEN_FARM", "QNONO",
              new ArrayList<String>(Arrays.asList("Results", "Experiment Planning")), "iisfr01",
              attachConfig, openbis);
      layout.addComponent(panel);

    } catch (IOException e) {
      System.err.println("Failed to load configuration: " + e);
    }
  }
}
