/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmoordb.core.processor.model;

import com.jmoordb.core.annotation.enumerations.JakartaSource;

/**
 *
 * @author avbravo Se utiliza para gestionar los datos leidos del repositorio
 */
public class ProjectionData {

    private String collection;
    private String database;
    private JakartaSource jakartaSource;
    private String packageOfProjection;
    private String projectionName;

    public ProjectionData() {
    }

    public ProjectionData(String collection, String database, JakartaSource jakartaSource, String packageOfProjection, String projectionName) {
        this.collection = collection;
        this.database = database;
        this.jakartaSource = jakartaSource;
        this.packageOfProjection = packageOfProjection;
        this.projectionName = projectionName;
    }

    public JakartaSource getJakartaSource() {
        return jakartaSource;
    }

    public void setJakartaSource(JakartaSource jakartaSource) {
        this.jakartaSource = jakartaSource;
    }

    public String getProjectionName() {
        return projectionName;
    }

    public void setProjectionName(String projectionName) {
        this.projectionName = projectionName;
    }

    public String getPackageOfProjection() {
        return packageOfProjection;
    }

    public void setPackageOfProjection(String packageOfProjection) {
        this.packageOfProjection = packageOfProjection;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProjectionData{");
        sb.append("\n\tcollection=").append(collection);
        sb.append("\n\t, database=").append(database);
        sb.append("\n\t, jakartaSource=").append(jakartaSource);
        sb.append("\n\t, packageOfProjection=").append(packageOfProjection);
        sb.append("\n\t, projectionName=").append(projectionName).append("\n");
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {

        private String collection;
        private String database;
        private String packageOfProjection;
        private String projectionName;
        JakartaSource jakartaSource;

        public Builder jakartaSource(JakartaSource jakartaSource) {
            this.jakartaSource = jakartaSource;
            return this;
        }

        public Builder collection(String collection) {
            this.collection = collection;
            return this;
        }

        public Builder projectionName(String projectionName) {
            this.projectionName = projectionName;
            return this;
        }

        public Builder packageOfProjection(String packageOfProjection) {
            this.packageOfProjection = packageOfProjection;
            return this;
        }

        public Builder database(String database) {
            this.database = database;
            return this;
        }

        public ProjectionData build() {
            return new ProjectionData(collection, database, jakartaSource, packageOfProjection, projectionName);

        }
    }
}
