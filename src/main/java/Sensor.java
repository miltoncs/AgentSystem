import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by milton on 9/5/16.
 */
public abstract class Sensor extends Agent
{
    private String topic = "";
    private int period = 0;
    private AtomicBoolean running = new AtomicBoolean(false);
    private ScheduledExecutorService singleThread = Executors.newSingleThreadScheduledExecutor();

    protected Sensor()
    {
        start();
    }

    protected final void setType(String reportType)
    {
        topic = reportType;
    }

    protected final void setPeriod(int seconds)
    {
        period = seconds;
    }

    protected final void publishReport()
    {
        this.publish(topic, this.getReport());
    }

    protected final void startReporting()
    {
        running.set(true);
        singleThread.scheduleAtFixedRate(this::publishReport, 0, period, TimeUnit.SECONDS);
    }

    protected final void stopReporting()
    {
        running.set(false);
        singleThread.shutdown();
    }

    protected final void restart()
    {
        stopReporting();
        startReporting();
    }

    abstract String getReport();
    abstract void start() throws ;
    abstract void terminate();
}
