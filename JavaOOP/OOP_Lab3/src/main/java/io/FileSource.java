package io;
import humanresources.EmployeeGroup;

public interface FileSource extends Source<EmployeeGroup> {
    public void setPath(String path);
    public String getPath();
}
