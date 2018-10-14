import java.io.File;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FileDetection extends Observable {
    private File[] listOfFiles;

    public void DetectCurrentFiles(String directoryPath)
    {
        File directory = new File(directoryPath);
        if(directory.isDirectory())
        {
            Runnable readFiles = () ->
            {
                this.listOfFiles = directory.listFiles();
            };
            readFiles.run();
        }
    }

    public File[] GetFiles()
    {
        return  this.listOfFiles;
    }

    public  void CheckForNewFile(String directoryPath, int perionInSeconds)
    {
        File directory = new File(directoryPath);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        Runnable detection = ()->
        {
            boolean foundDifferent  = false;

            File[] currentFiles = directory.listFiles();
            for (int i = 0;i < currentFiles.length; i++)
            {
                for(int j=0;j<listOfFiles.length; j++)
                {
                    if(currentFiles[i].getName() == listOfFiles[j].getName())
                    {
                        foundDifferent = false;
                        break;
                    }
                    else
                    {
                        foundDifferent = true;
                    }
                }
                if(foundDifferent)
                {
                    setChanged();
                    notifyObservers(currentFiles[i]);
                }
            }
            listOfFiles = currentFiles;
        };

        scheduledExecutorService.scheduleAtFixedRate(detection,1,perionInSeconds, TimeUnit.SECONDS);
    }
}
