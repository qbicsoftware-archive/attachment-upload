package processes;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import logging.Log4j2Logger;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;

/**
 * Uploader for tsv files
 * 
 * @author Andreas Friedrich
 * 
 */
@SuppressWarnings("serial")
public class Uploader implements Upload.SucceededListener, Upload.FailedListener, Upload.Receiver {

  Panel root; // Root element for contained components.
  File file; // File to write to.
  String error;
  String tmpFolder;

  logging.Logger logger = new Log4j2Logger(Uploader.class);

  public Uploader(String tmpFolder) {
    this.tmpFolder = tmpFolder;
  }

  /**
   * Callback method to begin receiving the upload.
   */
  public OutputStream receiveUpload(String filename, String MIMEType) {
    FileOutputStream fos = null; // Output stream to write to
    error = null;
    file = new File(tmpFolder + "up_" + filename);
    // TODO probably not needed; some browsers set MIME information to application/octet-stream,
    // which leads to bug
    // if (!MIMEType.equals("text/plain") && !MIMEType.equals("text/tab-separated-values")) {
    // error = "Wrong File type. Please only upload tsv or txt files.";
    // }
    try {
      // Open the file for writing.
      fos = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
      // Error while opening the file. Not reported here.
      e.printStackTrace();
      return null;
    }
    return fos; // Return the output stream to write to
  }

  public String getError() {
    return error;
  }

  public File getFile() {
    return file;
  }

  /**
   * This is called if the upload is finished.
   */
  public void uploadSucceeded(Upload.SucceededEvent event) {
    // Display the uploaded file in the image panel.
    logger.info("Uploading " + event.getFilename() + " of type '" + event.getMIMEType()
        + "' successful.");
  }

  /**
   * This is called if the upload fails.
   */
  public void uploadFailed(Upload.FailedEvent event) {
    // Log the failure on screen.
    logger.error("Uploading " + event.getFilename() + " of type '" + event.getMIMEType()
        + "' failed.");
  }
}
