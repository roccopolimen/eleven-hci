public class StopWatch
{
    private long startTime, stopTime;
    private boolean running;

    public StopWatch()
    {
        startTime = 0;
        stopTime = 0;
        running = false;
    }

    public void start()
    //Start Timer
    {
        startTime = System.currentTimeMillis();
        running = true;
    }

    public void stop()
    //Stop Timer
    {
        stopTime = System.currentTimeMillis();
        running = false;
    }

    public double elapsedTime()
    //return the time the stopwatch has been running
    {
        double elapsed;
        if (running)
        {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000.0);
        }
        else
        {
            elapsed = ((stopTime - startTime) / 1000.0);
        }
        return elapsed;
    }
}