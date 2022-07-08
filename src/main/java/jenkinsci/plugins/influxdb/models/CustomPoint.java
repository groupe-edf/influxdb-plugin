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
    private List<CustomField> customFields;

    public CustomPoint(String name, List<CustomField> customFields) {
        this.name = name;
        this.customFields = customFields;
    }

    public String getName() {
        return name;
    }

    @DataBoundSetter
    public void setName(String name) {
        this.name = name;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<CustomPoint> {

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return "points";
        }

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
