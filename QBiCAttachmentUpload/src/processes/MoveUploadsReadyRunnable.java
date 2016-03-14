package processes;

import java.io.IOException;

import main.UploadsPanel;

import com.github.sardine.Sardine;
import com.vaadin.ui.Component;

public class MoveUploadsReadyRunnable implements Runnable {

  private UploadsPanel view;
  private Sardine sardine;

  public MoveUploadsReadyRunnable(UploadsPanel view) {
    this.view = view;
  }

  @Override
  public void run() {
    try {
      sardine.shutdown();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    view.commitDone();
  }

  public void setSardine(Sardine sardine) {
    this.sardine = sardine;
  }

}
