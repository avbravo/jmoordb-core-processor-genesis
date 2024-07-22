/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.builder.castconverter;

import com.jmoordb.core.util.MessagesUtil;

/**
 *
 * @author avbravo
 */
public interface SupplierCastConverterBuilder {

    // <editor-fold defaultstate="collapsed" desc="String castConverter(String returnTypeString, String fieldName)">
    public static String castConverter(String returnTypeString, String fieldName) {
        String result = "";
        try {
            if (returnTypeString.startsWith("java.lang.String")) {
                return "document_.getString(\"" + fieldName + "\")";
            }
            if (returnTypeString.startsWith("org.bson.types.ObjectId")) {
                return "document_.getObjectId(\"" + fieldName + "\")";
            }

            if (returnTypeString.startsWith("java.util.UUID")) {
                return "document_.getString(\"" + fieldName + "\")";
            }
            if (returnTypeString.startsWith("java.lang.Integer")) {
                return "document_.getInteger(\"" + fieldName + "\")";
            }
            if (returnTypeString.startsWith("java.lang.Double")) {
                return "document_.getDouble(\"" + fieldName + "\")";
            }
            if (returnTypeString.startsWith("java.util.Date")) {
                return "document_.getDate(\"" + fieldName + "\")";
            }
            if (returnTypeString.startsWith("java.time.LocalDateTime")) {

                String line = "LocalDateTime.of(JmoordbCoreDateUtil.anioDeUnaFecha(document_.getDate(\"" + fieldName + "\")), \n"
                        + "\tJmoordbCoreDateUtil.mesDeUnaFecha(document_.getDate(\"" + fieldName + "\")), \n"
                        + "\tJmoordbCoreDateUtil.diaDeUnaFecha(document_.getDate(\"" + fieldName + "\")), \n"
                        + "\tJmoordbCoreDateUtil.horaDeUnaFecha(document_.getDate(\"" + fieldName + "\")), \n"
                        + "\tJmoordbCoreDateUtil.minutosDeUnaFecha(document_.getDate(\"" + fieldName + "\")), \n"
                        + "\tJmoordbCoreDateUtil.segundosDeUnaFecha(document_.getDate(\"" + fieldName + "\")))\n";

                return line;
            }
            if (returnTypeString.startsWith("java.lang.Boolean")) {
                return "document_.getBoolean(\"" + fieldName + "\")";
            }
            if (returnTypeString.startsWith("java.lang.Long")) {
                return "document_.getLong(\"" + fieldName + "\")";
            }
            if (returnTypeString.contains("ObjectId")) {
                return "document_.getObjectId(\"" + fieldName + "\")";
            }
            if (returnTypeString.startsWith("java.util.UUID")) {
                return "document_.getString(\"" + fieldName + "\")";
            }
            if (returnTypeString.contains("Float")) {
                return "(Float)document_.get(\"" + fieldName + "\")";
            }
            if (returnTypeString.contains("int")) {
                return "(int)document_.get(\"" + fieldName + "\")";
            }
            if (returnTypeString.contains("List")) {

                String clase = returnTypeString.replace("java.util.List<", "");
                clase = clase.replace(">", "");

                return "document_.getList(\"" + fieldName + "\"," + clase + ".class)";
            }
            if (returnTypeString.contains("Set")) {
                String clase = returnTypeString.replace("java.util.Set<", "");
                clase = clase.replace(">", "");

                return "new java.util.HashSet<>(document_.getList(\"" + fieldName + "\"," + clase + ".class))";
            }
            if (returnTypeString.contains("Stream")) {
                String clase = returnTypeString.replace("java.util.stream.Stream<", "");
                clase = clase.replace(">", "");

                return "document_.getList(\"" + fieldName + "\"," + clase + ".class).stream()";
            }

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

}
