import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;
import java.util.concurrent.Executors;


/**
 * Created by milton on 9/3/16.
 */
public abstract class Agent implements Runnable
{
    private final Config cfg = new Config();
    private final HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
    private static final String REGISTRATION_KEY = "registration";

    public Agent()
    {
        Executors.newSingleThreadExecutor().execute(this);
    }

    final protected Map<String, String> getAgentMap()
    {
        return instance.getMap(REGISTRATION_KEY);
    }

    final protected void publish(String topic, String message)
    {
        instance.getTopic(topic).publish(message);
    }
}