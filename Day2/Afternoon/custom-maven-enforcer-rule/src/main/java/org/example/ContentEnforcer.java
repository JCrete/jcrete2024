package org.example;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.enforcer.rule.api.AbstractEnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipFile;

@Named("checkWithABadName")
public class ContentEnforcer extends AbstractEnforcerRule {

    private String classifier;

    private Set<String> filesInJar;

    private Set<String> filesNotInJar;

    @Inject
    private MavenProject project;

    @Override
    public void execute() throws EnforcerRuleException {
        Path artifactPath = null;
        if(classifier == null) {
            artifactPath = project.getArtifact().getFile().toPath();
        }
        else {
            for(Artifact artifact: project.getAttachedArtifacts()) {
                if(classifier.equals(artifact.getClassifier())) {
                    artifactPath = artifact.getFile().toPath();
                }
            }
        }
        if(artifactPath == null) {
            throw new EnforcerRuleException("can't find artifact with classifier: " + classifier );
        }

        if(!Files.exists(artifactPath)) {
            throw new EnforcerRuleException("Project is missing main artifact");
        }
        else if(Files.isDirectory(artifactPath)) {
            throw new EnforcerRuleException("Main artifact is nog a regular file");
        }

        // loop over resources and verify if filesInJar exist AND filesNotInJar don't exist

        // for writing tests, have a look at https://github.com/mojohaus/extra-enforcer-rules/tree/master/src/it
        // https://github.com/mojohaus/extra-enforcer-rules/blob/master/pom.xml#L158-L200 shows how the integration-profile is configured
        // you can run this as mvn verify -Prun-its


    }
}
