package cn.horace.cronjob.commons.bean;

/**
 * maven信息
 * <p>
 *
 * @author Horace
 */
public class MavenInfo {
    private String groupId;
    private String artifactId;
    private String version;
    private String buildJdk = "";
    private String createBy = "";
    private String mavenVersion = "";

    public MavenInfo(String groupId, String artifactId, String version, String buildJdk, String createBy, String mavenVersion) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.buildJdk = buildJdk;
        this.createBy = createBy;
        this.mavenVersion = mavenVersion;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getBuildJdk() {
        return buildJdk;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getMavenVersion() {
        return mavenVersion;
    }

    @Override
    public String toString() {
        return "MavenInfo{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", buildJdk='" + buildJdk + '\'' +
                ", createBy='" + createBy + '\'' +
                ", mavenVersion='" + mavenVersion + '\'' +
                '}';
    }
}