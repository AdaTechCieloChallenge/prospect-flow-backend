package br.com.adatech.prospectflow.core.domain;

public enum ClientType {
    PJ("PJ"),
    PF("PF");

    private final String description;

    ClientType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ClientType convertFromString(String description) {
        for (ClientType clientType : ClientType.values()) {
            if (clientType.getDescription().equalsIgnoreCase(description)) {
                return clientType;
            }
        }
        throw new IllegalArgumentException("Invalid client type string: " + description);
    }

    public static ClientType convertFromInt(int type) {
        if (type >= 0 && type < ClientType.values().length) {
            return ClientType.values()[type];
        }
        throw new IllegalArgumentException("Invalid client type index: " + type);
    }
}
