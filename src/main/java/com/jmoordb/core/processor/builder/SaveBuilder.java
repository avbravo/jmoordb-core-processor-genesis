package com.jmoordb.core.processor.builder;

import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.processor.model.RepositoryData;
import com.jmoordb.core.processor.fields.RepositoryMethod;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.Optional;

public class SaveBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder save(RepositoryData repositoryData)">
    public static StringBuilder save(RepositoryData repositoryData, RepositoryMethod repositoryMethod) {
        StringBuilder builder = new StringBuilder();
        try {
            String param = ProcessorUtil.parametersOfMethod(repositoryMethod);

            String calculateReturn = "";
            String catchReturn = "";
            if (repositoryMethod.getReturnType().equals(ReturnType.OPTIONAL)) {
                calculateReturn = "return Optional.of(" + repositoryData.getNameOfEntityLower() + ");";
                catchReturn = "return Optional.empty();";
            } else {
                if (repositoryMethod.getReturnType().equals(ReturnType.BOOLEAN)) {
                    calculateReturn = "return Boolean.TRUE;";
                    catchReturn = "return Boolean.FALSE;";
                }
            }
            String autoSetField = "";
            String autoincrementWhileSentence = "";
            String elseAutoincrementWhileSentence = "";
            String returnAutoincrementWhileSentence = calculateReturn + "\n";
            if (repositoryData.getIsAutogenerated()) {
                autoSetField = repositoryData.getNameOfEntityLower() + ".set" + JmoordbCoreUtil.letterToUpper(repositoryData.getFieldPk()) + "(autogeneratedRepository.generate(mongodbDatabase, mongodbCollection));";

                autoincrementWhileSentence = "\tBoolean success = Boolean.FALSE;\n";
                autoincrementWhileSentence += "\twhile (!success) {\n";
                elseAutoincrementWhileSentence = "\telse{\n";
                elseAutoincrementWhileSentence += "\t\t  success= Boolean.TRUE;\n";
                elseAutoincrementWhileSentence += "\t}\n";
                returnAutoincrementWhileSentence = "";
            }

            String code
                    = ProcessorUtil.editorFold(repositoryMethod, param) + "\n\n"
                    + "    @Override\n"
                    + "    public " + repositoryMethod.getReturnTypeValue() + " " + repositoryMethod.getNameOfMethod() + "(" + param + ") {\n"
                    + "        try {\n"
                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabase);\n"
                    + "               MongoCollection<Document> collection = database.getCollection(mongodbCollection);\n"
                    + "               " + autoincrementWhileSentence
                    + "               " + autoSetField + "\n"
                    + "               if (findByPk(" + repositoryData.getNameOfEntityLower() + ".get" + JmoordbCoreUtil.letterToUpper(repositoryData.getFieldPk()) + "()).isPresent()) { \n"
                    + "                   MessagesUtil.warning(\"There is already a record with that id\");\n"
                    + "                   exception = new JmoordbException((\"There is already a record with that id\");\n"
                    //                    + "                  " + calculateReturn + "\n"
                    + "                " + returnAutoincrementWhileSentence
                    + "               }\n"
                    + "               " + elseAutoincrementWhileSentence
                    + "              //Jsonb jsonb = JsonbBuilder.create();\n"
                    + "              // InsertOneResult insertOneResult = collection.insertOne(Document.parse(jsonb.toJson(" + repositoryData.getNameOfEntityLower() + ")));\n"
                    + "               //  InsertOneResult insertOneResult = collection.insertOne(Document.parse(" + repositoryData.getNameOfEntityLower() + "Supplier.toJson(" + repositoryData.getNameOfEntityLower() + ")));\n"
                    + "               InsertOneResult insertOneResult = collection.insertOne(" + repositoryData.getNameOfEntityLower() + "Supplier.toDocument(" + repositoryData.getNameOfEntityLower() + "));\n"
                    + "               if (insertOneResult.getInsertedId() != null) {\n"
                    + "                  " + calculateReturn + "\n"
                    + "               }\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "               exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
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
    // <editor-fold defaultstate="collapsed" desc="StringBuilder saveOfCrud(RepositoryData repositoryData)">
    public static StringBuilder saveOfCrud(RepositoryData repositoryData) {

        // public Optional<T> save(T t);
        StringBuilder builder = new StringBuilder();
        try {
            String nameOfEntityUpper = JmoordbCoreUtil.letterToUpper(repositoryData.getNameOfEntity());
            String nameOfEntityLower = JmoordbCoreUtil.letterToLower(repositoryData.getNameOfEntity());

            String calculateReturn = "";
            String catchReturn = "";

            calculateReturn = "return Optional.of(" + nameOfEntityLower + ");";
            catchReturn = "return Optional.empty();";

            String autoSetField = "";
            String autoincrementWhileSentence = "";
            String elseAutoincrementWhileSentence = "";
            String returnAutoincrementWhileSentence = calculateReturn + "\n";
            if (repositoryData.getIsAutogenerated()) {
                autoSetField = repositoryData.getNameOfEntityLower() + ".set" + JmoordbCoreUtil.letterToUpper(repositoryData.getFieldPk()) + "(autogeneratedRepository.generate(mongodbDatabase, mongodbCollection));";
                 autoincrementWhileSentence = "\tBoolean success = Boolean.FALSE;\n";
                autoincrementWhileSentence += "\twhile (!success) {\n";
                elseAutoincrementWhileSentence = "\telse{\n";
                elseAutoincrementWhileSentence += "\t\t  success= Boolean.TRUE;\n";
                elseAutoincrementWhileSentence += "\t     }\n";
                elseAutoincrementWhileSentence += "\t   }\n";
                returnAutoincrementWhileSentence = "";
            }
            String text = " public Optional<" + nameOfEntityUpper + "> save(" + nameOfEntityUpper + " " + nameOfEntityLower + ")";
            String code
                    = ProcessorUtil.editorFold(text) + "\n\n"
                    + "    @Override\n"
                    + "    public Optional<" + nameOfEntityUpper + "> save(" + nameOfEntityUpper + " " + nameOfEntityLower + ") {\n"
                    + "        try {\n"
                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabase);\n"
                    + "               MongoCollection<Document> collection = database.getCollection(mongodbCollection);\n"
                    + "               " + autoincrementWhileSentence
                    + "               " + autoSetField + "\n"
                    + "               if (findByPk(" + repositoryData.getNameOfEntityLower() + ".get" + JmoordbCoreUtil.letterToUpper(repositoryData.getFieldPk()) + "()).isPresent()) { \n"
                    + "                   MessagesUtil.warning(\"There is already a record with that id\");\n"
                    + "                    exception = new JmoordbException(\"There is already a record with that id\");\n"
//                    + "                  " + calculateReturn + "\n"
                    + "                " + returnAutoincrementWhileSentence
                    + "               }\n"
                      + "               " + elseAutoincrementWhileSentence
                    + "               InsertOneResult insertOneResult = collection.insertOne(" + repositoryData.getNameOfEntityLower() + "Supplier.toDocument(" + repositoryData.getNameOfEntityLower() + "));\n"
                    + "               if (insertOneResult.getInsertedId() != null) {\n"
                    + "                  " + calculateReturn + "\n"
                    + "               }\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "               exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
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
