package com.enjoyf.framework.jdbc.bean;

import java.sql.Connection;

public class ConnectionBean {
    public Connection readConnection = null;
    public Connection backupReadConnection = null;
    public Connection writeConnection = null;
    
    public Connection getReadConnection() {
        return readConnection;
    }

    public void setReadConnection(Connection readConnection) {
        this.readConnection = readConnection;
    }

    public Connection getWriteConnection() {
        return writeConnection;
    }

    public void setWriteConnection(Connection writeConnection) {
        this.writeConnection = writeConnection;
    }

    public Connection getBackupReadConnection() {
        return backupReadConnection;
    }

    public void setBackupReadConnection(Connection backupReadConnection) {
        this.backupReadConnection = backupReadConnection;
    }

    
}
