package org.d2predict.dotaheroprediction;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Use this class to get Screenshot of fullscreen applications.
 * 
 * @author Tim
 */
public class ScreenshotGrabber {
    
    BufferedImage screenshot;

    /**
     * Takes a screenshot of the current screen.
     *
     * @return The screenshot.
     */
    public BufferedImage takeScreenshot() throws AWTException, IOException, UnsupportedFlavorException {
        Image image;
        Clipboard cb;
        DataFlavor[] flavors;
        
        this.screenshot = null;

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_PRINTSCREEN);
        robot.delay(40);
        robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
        robot.delay(40);

        cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        flavors = cb.getAvailableDataFlavors();
        for (DataFlavor flavor : flavors) {
            if (flavor.isMimeTypeEqual(DataFlavor.imageFlavor)) {
                Transferable content = cb.getContents(null);
                this.screenshot = (BufferedImage) content.getTransferData(DataFlavor.imageFlavor);
                return this.screenshot;
            }
        }
        return null;
    }
    
    public void saveScreenshot(String filename) throws IOException {
        ImageIO.write(this.screenshot, "png", new File(filename));
    }
}
