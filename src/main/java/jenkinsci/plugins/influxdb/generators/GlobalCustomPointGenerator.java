package jenkinsci.plugins.influxdb.generators;

import java.util.List;

import com.influxdb.client.write.Point;

import hudson.model.Run;
import hudson.model.TaskListener;
import jenkinsci.plugins.influxdb.models.CustomPoint;
import jenkinsci.plugins.influxdb.renderer.ProjectNameRenderer;

public class GlobalCustomPointGenerator extends AbstractPointGenerator {

    private List<CustomPoint> customPoints;

    public GlobalCustomPointGenerator(Run<?, ?> build, TaskListener listener, ProjectNameRenderer projectNameRenderer,
            long timestamp, String jenkinsEnvParameterTag, List<CustomPoint> customPoints) {
        this(build, listener, projectNameRenderer, timestamp, jenkinsEnvParameterTag);
        this.customPoints = customPoints;
    }

    public GlobalCustomPointGenerator(Run<?, ?> build, TaskListener listener, ProjectNameRenderer projectNameRenderer,
            long timestamp, String jenkinsEnvParameterTag) {
        super(build, listener, projectNameRenderer, timestamp, jenkinsEnvParameterTag);
    }

    @Override
    public boolean hasReport() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Point[] generate() {
        // TODO Auto-generated method stub
        return null;
    }

}
