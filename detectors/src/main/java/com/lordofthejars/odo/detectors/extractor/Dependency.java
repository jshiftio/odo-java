package com.lordofthejars.odo.detectors.extractor;

import java.util.Objects;

public class Dependency {
    private String artifactId;
    private String groupId;

    public Dependency(String group, String artifact) {
        this.artifactId = artifact;
        this.groupId = group;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dependency{");
        sb.append("artifactId='").append(artifactId).append('\'');
        sb.append(", groupId='").append(groupId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Dependency that = (Dependency) o;
        return Objects.equals(artifactId, that.artifactId) &&
            Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(artifactId, groupId);
    }
}
