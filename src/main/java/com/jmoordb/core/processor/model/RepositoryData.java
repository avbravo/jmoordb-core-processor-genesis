/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmoordb.core.processor.model;

import com.jmoordb.core.annotation.enumerations.GenerationType;
import com.jmoordb.core.annotation.enumerations.GenerationType;
import com.jmoordb.core.annotation.enumerations.TypePK;

/**
 *
 * @author avbravo Se utiliza para gestionar los datos leidos del repositorio
 */
public class RepositoryData {

    private String nameOfEntity;
    private String nameOfEntityLower;
    private String nameOfPackage;
    private String packageOfRepository;
    private String fieldPk;
    private GenerationType generationType;
    private String typeParameter = "String";
    private Boolean isAutogenerated = Boolean.FALSE;
    private TypePK typePK;
    private String interfaceName;

    public RepositoryData() {
    }

    public RepositoryData(String nameOfEntity, String nameOfEntityLower, String nameOfPackage, String fieldPk, GenerationType generationType, TypePK typePK, String typeParameter, Boolean isAutogenerated,String packageOfRepository,String interfaceName) {
        this.nameOfEntity = nameOfEntity;
        this.nameOfEntityLower = nameOfEntityLower;
        this.nameOfPackage = nameOfPackage;
        this.fieldPk = fieldPk;
        this.generationType = generationType;
        this.typePK = typePK;
        this.typeParameter = typeParameter;
        this.isAutogenerated = isAutogenerated;
        this.packageOfRepository =packageOfRepository;
        this.interfaceName = interfaceName;
    }

    public String getPackageOfRepository() {
        return packageOfRepository;
    }

    public void setPackageOfRepository(String packageOfRepository) {
        this.packageOfRepository = packageOfRepository;
    }

    public String getTypeParameter() {
        return typeParameter;
    }

    public void setTypeParameter(String typeParameter) {
        this.typeParameter = typeParameter;
    }

    public Boolean getIsAutogenerated() {
        return isAutogenerated;
    }

    public void setIsAutogenerated(Boolean isAutogenerated) {
        this.isAutogenerated = isAutogenerated;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
    
    

    public String getNameOfEntityLower() {
        return nameOfEntityLower;
    }

    public void setNameOfEntityLower(String nameOfEntityLower) {
        this.nameOfEntityLower = nameOfEntityLower;
    }

    public String getNameOfEntity() {
        return nameOfEntity;
    }

    public void setNameOfEntity(String nameOfEntity) {
        this.nameOfEntity = nameOfEntity;
    }

    public String getNameOfPackage() {
        return nameOfPackage;
    }

    public void setNameOfPackage(String nameOfPackage) {
        this.nameOfPackage = nameOfPackage;
    }

    public String getFieldPk() {
        return fieldPk;
    }

    public void setFieldPk(String fieldPk) {
        this.fieldPk = fieldPk;
    }

    public GenerationType getGenerationType() {
        return generationType;
    }

    public void setGenerationType(GenerationType generationType) {
        this.generationType = generationType;
    }

    public TypePK getTypePK() {
        return typePK;
    }

    public void setTypePK(TypePK typePK) {
        this.typePK = typePK;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RepositoryData{");
        sb.append("\nnameOfEntity=").append(nameOfEntity);
        sb.append("\n, nameOfEntityLower=").append(nameOfEntityLower);
        sb.append("\n, nameOfPackage=").append(nameOfPackage);
        sb.append("\n, packageOfRepository=").append(packageOfRepository);
        sb.append("\n, fieldPk=").append(fieldPk);
        sb.append("\n, generationType=").append(generationType);
        sb.append("\n, typeParameter=").append(typeParameter);
        sb.append("\n, isAutogenerated=").append(isAutogenerated);
        sb.append("\n, typePK=").append(typePK);
        sb.append("\n, interfaceName=").append(interfaceName);
        sb.append('}');
        return sb.toString();
    }
    
    
    
    

    public static class Builder {

        private String nameOfEntity;
        private String nameOfEntityLower;
        private String nameOfPackage;
        private String fieldPK;
        private GenerationType generationType;
        private TypePK typePK;
        private String typeParameter = "String";
        private Boolean isAutogenerated = Boolean.FALSE;
        private String packageOfRepository;
        private String interfaceName;

        public Builder nameOfEntity(String nameOfEntity) {
            this.nameOfEntity = nameOfEntity;
            return this;
        }
        public Builder interfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }
        public Builder packageOfRepository(String packageOfRepository) {
            this.packageOfRepository= packageOfRepository;
            return this;
        }
        public Builder typeParameter(String typeParameter) {
            this.typeParameter=typeParameter;
            return this;
        }
        public Builder isAutogenerated(Boolean isAutogenerated) {
            this.isAutogenerated=isAutogenerated;
            return this;
        }

        public Builder nameOfEntityLower(String nameOfEntityLower) {
            this.nameOfEntityLower = nameOfEntityLower;
            return this;
        }

        public Builder nameOfPackage(String nameOfPackage) {
            this.nameOfPackage = nameOfPackage;
            return this;
        }

        public Builder fieldPK(String fieldPK) {
            this.fieldPK = fieldPK;
            return this;
        }

        public Builder generationType(GenerationType generationType) {
            this.generationType = generationType;
            return this;
        }

        public Builder typePK(TypePK typePK) {
            this.typePK = typePK;
            return this;
        }

        public RepositoryData build() {
            return new RepositoryData(nameOfEntity, nameOfEntityLower, nameOfPackage, fieldPK, generationType, typePK, typeParameter,isAutogenerated,packageOfRepository,interfaceName);
        }

    }

    

}
