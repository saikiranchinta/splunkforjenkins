package jenkins.plugins.splunkins;

import hudson.Extension;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;
import hudson.tools.ToolProperty;
import hudson.util.FormValidation;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.util.List;

public class SplunkinsInstallation extends ToolInstallation {

    @DataBoundConstructor
    public SplunkinsInstallation(String name, String home, List<? extends ToolProperty<?>> properties) {
        super(name, home, properties);
    }

    public static Descriptor getSplunkinsDescriptor() {
        return (Descriptor) Jenkins.getInstance().getDescriptor(SplunkinsInstallation.class);
    }

    @Extension
    public static final class Descriptor extends ToolDescriptor<SplunkinsInstallation> {
        public String host;
        public Integer port;
        public String username;
        public String password;
        public String scheme;

        public Descriptor() {
            super();
            load();
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            req.bindJSON(this, formData.getJSONObject("splunkins"));
            save();
            return super.configure(req, formData);
        }

        @Override
        public ToolInstallation newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            req.bindJSON(this, formData.getJSONObject("splunkins"));
            save();
            return super.newInstance(req, formData);
        }

        @Override
        public String getDisplayName() {
            return Messages.DisplayName();
        }

        /*
         * Form validation methods
         */
        public FormValidation doCheckInteger(@QueryParameter("value") String value) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return FormValidation.error(Messages.ValueIntErrorMsg());
            }

            return FormValidation.ok();
        }

        public FormValidation doCheckHost(@QueryParameter("value") String value) {
            if (StringUtils.isBlank(value)) {
                return FormValidation.warning(Messages.PleaseProvideHost());
            }

            return FormValidation.ok();
        }

        public FormValidation doCheckString(@QueryParameter("value") String value) {
            if (StringUtils.isBlank(value)) {
                return FormValidation.error(Messages.ValueCannotBeBlank());
            }

            return FormValidation.ok();
        }
    }
}