package com.linebeck.gitter.enums;

public enum RepositoryType {
    CONFIGURATION,
    DENIZEN,
    UNKNOWN;

    public static RepositoryType getRepositoryTypeByName(String name) {
        for(var repositoryType : RepositoryType.values()) {
            if(repositoryType.name().equalsIgnoreCase(name)) {
                return repositoryType;
            }
        }
        return UNKNOWN;
    }
}
