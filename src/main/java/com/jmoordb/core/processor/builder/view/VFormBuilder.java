package com.jmoordb.core.processor.builder.view;

import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.processor.methods.view.ViewMethod;
import com.jmoordb.core.processor.model.view.ViewData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;

public class VFormBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder save(ViewData viewData)">
    public static StringBuilder save(ViewData viewData, ViewMethod viewMethod) {
        StringBuilder builder = new StringBuilder();
        try {
            String param = ProcessorUtil.parametersOfMethod(viewMethod);

            String calculateReturn = "";
            String catchReturn = "";
            if (viewMethod.getReturnType().equals(ReturnType.OPTIONAL)) {
                calculateReturn = "return Optional.of(" + viewData.getNameOfEntityLower() + ");";
                catchReturn = "return Optional.empty();";
            } else {
                if (viewMethod.getReturnType().equals(ReturnType.BOOLEAN)) {
                    calculateReturn = "return Boolean.TRUE;";
                    catchReturn = "return Boolean.FALSE;";
                }
            }
            String autoSetField = "";
            if (viewData.getIsAutogenerated()) {
                autoSetField = viewData.getNameOfEntityLower() + ".set" + JmoordbCoreUtil.letterToUpper(viewData.getFieldPk()) + "(autogeneratedView.generate(mongodbDatabase, mongodbCollection));";
            }

            String code
                    = ProcessorUtil.editorFold(viewMethod, param) + "\n\n"
                    + "    @Override\n"
                    + "    public " + viewMethod.getReturnTypeValue() + " " + viewMethod.getNameOfMethod() + "(" + param + ") {\n"
                    + "        try {\n"
                    + "               String mongodbDatabaseValue = mongodbDatabase;\n"
                    + "               String mongodbCollectionValue = mongodbCollection;\n"
                    + "               if (!getDynamicDatabase().equals(\"\")) {\n"
                    + "                   mongodbDatabaseValue = getDynamicDatabase();\n"
                    + "                }\n"
                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
                    + "               setDynamicDatabase(\"\");\n"  
                    + "               MongoCollection<Document> collection = database.getCollection(mongodbCollection);\n"
                    + "               " + autoSetField + "\n"
                    + "               if (findByPk(" + viewData.getNameOfEntityLower() + ".get" + JmoordbCoreUtil.letterToUpper(viewData.getFieldPk()) + "()).isPresent()) { \n"
                    + "                   MessagesUtil.warning(\"There is already a record with that id\");\n"
                    + "                  " + calculateReturn + "\n"
                    + "               }\n"
                    + "              //Jsonb jsonb = JsonbBuilder.create();\n"
                    + "              // InsertOneResult insertOneResult = collection.insertOne(Document.parse(jsonb.toJson(" + viewData.getNameOfEntityLower() + ")));\n"
                    + "               //  InsertOneResult insertOneResult = collection.insertOne(Document.parse(" + viewData.getNameOfEntityLower() + "Supplier.toJson(" + viewData.getNameOfEntityLower() + ")));\n"
                    + "               InsertOneResult insertOneResult = collection.insertOne(" + viewData.getNameOfEntityLower() + "Supplier.toDocument(" + viewData.getNameOfEntityLower() + "));\n"
                    + "               if (insertOneResult.getInsertedId() != null) {\n"
                    + "                  " + calculateReturn + "\n"
                    + "               }\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         " + catchReturn + "\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="StringBuilder saveOfCrud(ViewData viewData)">
    public static StringBuilder saveOfCrud(ViewData viewData) {

        // public Optional<T> save(T t);
        StringBuilder builder = new StringBuilder();
        try {
            String nameOfEntityUpper = JmoordbCoreUtil.letterToUpper(viewData.getNameOfEntity());
            String nameOfEntityLower = JmoordbCoreUtil.letterToLower(viewData.getNameOfEntity());

            String calculateReturn = "";
            String catchReturn = "";

            calculateReturn = "return Optional.of(" + nameOfEntityLower + ");";
            catchReturn = "return Optional.empty();";

            String autoSetField = "";
            if (viewData.getIsAutogenerated()) {
                autoSetField = viewData.getNameOfEntityLower() + ".set" + JmoordbCoreUtil.letterToUpper(viewData.getFieldPk()) + "(autogeneratedView.generate(mongodbDatabase, mongodbCollection));";
            }
            String text = " public Optional<" + nameOfEntityUpper + "> save(" + nameOfEntityUpper + " " + nameOfEntityLower + ")";
            String code
                    = ProcessorUtil.editorFold(text) + "\n\n"
                    + "    @Override\n"
                    + "    public Optional<" + nameOfEntityUpper + "> save(" + nameOfEntityUpper + " " + nameOfEntityLower + ") {\n"
                    + "        try {\n"
                    + "               String mongodbDatabaseValue = mongodbDatabase;\n"
                    + "               String mongodbCollectionValue = mongodbCollection;\n"
                    + "               if (!getDynamicDatabase().equals(\"\")) {\n"
                    + "                   mongodbDatabaseValue = getDynamicDatabase();\n"
                    + "                }\n"
                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
                    + "               setDynamicDatabase(\"\");\n"  
                    + "               MongoCollection<Document> collection = database.getCollection(mongodbCollection);\n"
                    + "               " + autoSetField + "\n"
                    + "               if (findByPk(" + viewData.getNameOfEntityLower() + ".get" + JmoordbCoreUtil.letterToUpper(viewData.getFieldPk()) + "()).isPresent()) { \n"
                    + "                   MessagesUtil.warning(\"There is already a record with that id\");\n"
                    + "                  " + calculateReturn + "\n"
                    + "               }\n"
                    + "               InsertOneResult insertOneResult = collection.insertOne(" + viewData.getNameOfEntityLower() + "Supplier.toDocument(" + viewData.getNameOfEntityLower() + "));\n"
                    + "               if (insertOneResult.getInsertedId() != null) {\n"
                    + "                  " + calculateReturn + "\n"
                    + "               }\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         " + catchReturn + "\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
}
