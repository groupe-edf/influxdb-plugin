package jenkinsci.plugins.influxdb.models;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.verb.POST;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;

public class CustomPoint extends AbstractDescribableImpl<CustomPoint> {

    private String name;
    private List<CustomField> fields;

    public CustomPoint(String name, List<CustomField> fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    @DataBoundSetter
    public void setName(String name) {
        this.name = name;
    }

    public List<CustomField> getFields() {
        return fields;
    }

    @DataBoundSetter
    public void setFields(List<CustomField> fields) {
        this.fields = fields;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<CustomPoint> {

        @POST
        public FormValidation doCheckName(@QueryParameter String name) {
            return FormValidation.validateRequired(name);
        }

        @POST
        public FormValidation doCheckFields(@QueryParameter List<CustomField> fields) {
            if (CollectionUtils.isEmpty(fields)) {
                return FormValidation.error("You must provide at least one field for a custom Point");
            }
            return FormValidation.ok();
        }

    }

}
