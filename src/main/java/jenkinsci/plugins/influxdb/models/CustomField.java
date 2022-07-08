package jenkinsci.plugins.influxdb.models;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.verb.POST;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;

public class CustomField extends AbstractDescribableImpl<CustomField> {

    private String name;
    private String regex;

    @DataBoundConstructor
    public CustomField(String name, String regex) {
        super();
        this.name = name;
        this.regex = regex;
    }

    public String getName() {
        return name;
    }

    @DataBoundSetter
    public void setName(String name) {
        this.name = name;
    }

    public String getRegex() {
        return regex;
    }

    @DataBoundSetter
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<CustomField> {

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return "fields";
        }

        @POST
        public FormValidation doCheckName(@QueryParameter String name) {
            return FormValidation.validateRequired(name);
        }

        @POST
        public FormValidation doCheckRegex(@QueryParameter String regex) {
            if (StringUtils.isBlank(regex)) {
                return FormValidation.error("Regular expression is null or empty");
            }
            try {
                Pattern.compile(regex);
            } catch (PatternSyntaxException pse) {
                return FormValidation.error(pse, "Regular expression is not valid");
            }
            return FormValidation.ok();
        }
    }

}
