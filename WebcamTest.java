import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

public class WebcamTest {
        static {
    System.load("C:\\Users\\ANIKET\\OneDrive\\Desktop\\Javastart\\opencv_java4120.dll");
}

    public static void main(String[] args) {

        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("Camera not found");
            return;
        }

        Mat frame = new Mat();
        System.out.println("Camera opened");

        while (true) {
            camera.read(frame);
            if (frame.empty()) break;

            HighGui.imshow("Webcam Test", frame);

            if (HighGui.waitKey(1) == 27) { // ESC
                break;
            }
        }

        camera.release();
        HighGui.destroyAllWindows();
    }
}