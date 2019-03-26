package io.jshift.odo.junit5;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import io.jshift.odo.api.GitClone;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

public class GitExtension implements BeforeAllCallback, ParameterResolver {

    private static final Logger logger = Logger.getLogger(GitExtension.class.getName());

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

        if (AnnotationSupport.isAnnotated(annotatedElement, GitClone.class)) {

            final GitClone gitClone = annotatedElement.getAnnotation(GitClone.class);

            final Path directory = sharedTempDir.resolve(repoName(gitClone.value()));

            logger.info(String.format("Cloning %s repo to %s", gitClone.value(), directory));

            try {
                Files.createDirectories(directory);

                if (gitClone.subdir().isEmpty()) {
                    clone(gitClone.value(), directory);
                    projectDirectory = directory;
                } else {
                    sparseCheckout(gitClone.value(), directory, gitClone.subdir());
                    projectDirectory = directory.resolve(gitClone.subdir());
                }

            } catch (GitAPIException | IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private void clone(String repo, Path directory) throws GitAPIException {
        final Git git = Git.cloneRepository()
            .setURI(repo)
            .setDirectory(directory.toFile())
            .call();
        git.getRepository().close();
    }

    private void sparseCheckout(String repo, Path directory, String path) throws GitAPIException, IOException {

        try (final Git gitRepo = Git.cloneRepository()
            .setURI(repo)
            .setDirectory(directory.toFile())
            .setNoCheckout(true)
            .call()) {

            gitRepo.checkout().setName("master").setStartPoint("origin/master").addPath(path).call();
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
