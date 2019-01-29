package com.lordofthejars.odo.testbed.junit5;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.nio.file.Files;
import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static org.junit.platform.commons.support.AnnotationSupport.isAnnotated;

public class GitExtension implements BeforeAllCallback, ParameterResolver {

    private final Path sharedTempDir;
    private Path projectDirectory;

    public GitExtension() {
        try {
            sharedTempDir = Files.createTempDirectory("odo-test");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) {

        AnnotatedElement annotatedElement =
            context.getElement().orElseThrow(() -> new IllegalStateException("No test class found."));

        if (isAnnotated(annotatedElement, GitClone.class)) {

            final GitClone gitClone = annotatedElement.getAnnotation(GitClone.class);

            final Path directory = sharedTempDir.resolve(repoName(gitClone.value()));

            try {
                Files.createDirectories(directory);
                Git.cloneRepository()
                    .setURI(gitClone.value())
                    .setDirectory(directory.toFile())
                    .call();

                projectDirectory = directory;
            } catch (GitAPIException | IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private String repoName(String repo) {
        return repo.substring(repo.lastIndexOf('/') + 1);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
        throws ParameterResolutionException {
        return Path.class.isAssignableFrom(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
        throws ParameterResolutionException {
        return this.projectDirectory;
    }
}
