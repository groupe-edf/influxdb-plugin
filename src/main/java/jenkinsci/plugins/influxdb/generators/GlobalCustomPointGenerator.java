package jenkinsci.plugins.influxdb.generators;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;

import com.influxdb.client.write.Point;

import hudson.model.Run;
import hudson.model.TaskListener;
import jenkinsci.plugins.influxdb.models.CustomField;
import jenkinsci.plugins.influxdb.models.CustomPoint;
import jenkinsci.plugins.influxdb.renderer.ProjectNameRenderer;

public class GlobalCustomPointGenerator extends AbstractPointGenerator {

    private static final Logger LOGGER = Logger.getLogger(GlobalCustomPointGenerator.class.getName());

    private List<CustomPoint> customPoints;
    private String customPrefix;

    public GlobalCustomPointGenerator(Run<?, ?> build, TaskListener listener, ProjectNameRenderer projectNameRenderer,
            long timestamp, String jenkinsEnvParameterTag, String customPrefix, List<CustomPoint> customPoints) {
        super(build, listener, projectNameRenderer, timestamp, jenkinsEnvParameterTag);
        this.customPoints = customPoints;
        this.customPrefix = customPrefix;
    }

    @Override
    public boolean hasReport() {
        return CollectionUtils.isNotEmpty(customPoints);
    }

    @Override
    public Point[] generate() {
        List<Point> points = new ArrayList<>();
        try {
            getCustomPointFromLogs(this.build);
        } catch (IOException e) {
            LOGGER.log(Level.FINE, "Cannot read logs from build");
        }
        for (CustomPoint customPoint : customPoints) {
            Point point = buildPoint(customPoint.getName(), customPrefix, build);
            for (CustomField customField : customPoint.getCustomFields()) {
                point.addField(customField.getName(), customField.getValue());
            }
            points.add(point);
        }
        return points.toArray(new Point[0]);
    }

    private void getCustomPointFromLogs(Run<?, ?> build) throws IOException {

        try (BufferedReader br = new BufferedReader(build.getLogReader())) {
            String line;
            Matcher match;
            while ((line = br.readLine()) != null) {
                for (CustomPoint point : customPoints) {
                    for (CustomField field : point.getCustomFields()) {
                        Pattern fieldPattern = Pattern.compile(field.getRegex());
                        match = fieldPattern.matcher(line);
                        if (match.find()) {
                            field.setValue(match.group());
                        }
                    }
                }
            }
        }
    }
}
