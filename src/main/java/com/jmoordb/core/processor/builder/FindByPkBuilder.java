package com.jmoordb.core.processor.builder;

import com.jmoordb.core.processor.model.RepositoryData;
import com.jmoordb.core.util.MessagesUtil;

public class FindByPkBuilder {

    // <editor-fold defaultstate="collapsed" desc="StringBuilder findByPK(RepositoryData repositoryData)">
    /**
     *
     * @param comment
     * @return inserta comentarios
     */
    public static StringBuilder findByPK(RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {
            String editorFoldStart = "public Optional<" + repositoryData.getNameOfEntity() + "> findByPk(" + repositoryData.getTypeParameter() + " id )";
            String code
                    = "// <editor-fold defaultstate=\"collapsed\" desc=\"" +editorFoldStart + "\">\n\n"
                    + "    public Optional<" + repositoryData.getNameOfEntity() + "> findByPk(" + repositoryData.getTypeParameter() + " id ) {\n"
                    + "        try {\n"
//                    + "               String mongodbDatabaseValue = mongodbDatabase;\n"
//                    + "               String mongodbCollectionValue = mongodbCollection;\n"
//                    + "               if (!getDynamicDatabase().equals(\"\")) {\n"
//                    + "                   mongodbDatabaseValue = getDynamicDatabase();\n"
//                    + "                }\n"
//                    + "               if (!getDynamicCollection().equals(\"\")) {\n"
//                    + "                   mongodbCollectionValue = getDynamicCollection();\n"
//                    + "                }\n"
//                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
//                    + "               setDynamicDatabase(\"\");\n"
//                    + "            MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);\n"
//                    + "            setDynamicCollection(\"\");\n"
                    + "           MongoCollection<Document> collection = getCollection().get();\n"
                    + "            Document doc = collection.find(eq(\"" + repositoryData.getFieldPk() + "\", id)).allowDiskUse(Boolean.TRUE).first();\n"
                    + "            if(doc == null){\n"
                    + "               return Optional.empty();\n"
                    + "            }\n"
                    + "            " + repositoryData.getNameOfEntity() + " " + repositoryData.getNameOfEntityLower() + " = " + repositoryData.getNameOfEntityLower() + "Supplier.get(" + repositoryData.getNameOfEntity() + "::new, doc);\n"
                    + "            return Optional.of(" + repositoryData.getNameOfEntityLower() + ");\n"
                    + "       } catch (Exception e) {\n"
                    + "            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "             exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "       }\n"
                    + "       return Optional.empty();\n"
                    + "    }\n"
                    + "// </editor-fold>\n\n\n";
            
           editorFoldStart = "public Optional<" + repositoryData.getNameOfEntity() + "> findByPkInternal(" + repositoryData.getTypeParameter() + " id, String mongodbDatabaseValue, String mongodbCollectionValue  )";
            code+= "// <editor-fold defaultstate=\"collapsed\" desc=\"" +editorFoldStart + "\">\n\n"
                    + "    public Optional<" + repositoryData.getNameOfEntity() + "> findByPkInternal(" + repositoryData.getTypeParameter() + " id , String mongodbDatabaseValue, String mongodbCollectionValue ) {\n"
                    + "        try {\n"
                    + "            MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
                    + "            MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);\n"
//                    + "            if (mongodbDatabaseValue.equals(\"\")) {\n"
//                    + "                mongodbDatabaseValue = mongodbDatabase; \n"
//                    + "            }\n"
//                    + "            if (mongodbCollectionValue.equals(\"\")) {\n"
//                    + "               mongodbCollectionValue = mongodbCollection;\n"
//                    + "            }\n"
//                    + "            MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
//                    + "            setDynamicDatabase(\"\");\n"
//                    + "            MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);\n"
//                    + "            setDynamicCollection(\"\");\n"
//                    + "           MongoCollection<Document> collection = getCollection().get();\n"
                    + "            Document doc = collection.find(eq(\"" + repositoryData.getFieldPk() + "\", id)).allowDiskUse(Boolean.TRUE).first();\n"
                    + "            if(doc == null){\n"
                    + "               return Optional.empty();\n"
                    + "            }\n"
                    + "            " + repositoryData.getNameOfEntity() + " " + repositoryData.getNameOfEntityLower() + " = " + repositoryData.getNameOfEntityLower() + "Supplier.get(" + repositoryData.getNameOfEntity() + "::new, doc);\n"
                    + "            return Optional.of(" + repositoryData.getNameOfEntityLower() + ");\n"
                    + "       } catch (Exception e) {\n"
                    + "            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "             exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "       }\n"
                    + "       return Optional.empty();\n"
                    + "    }\n"
                    + "// </editor-fold>\n";
            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }
// </editor-fold>

}
