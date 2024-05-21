package com.jmoordb.core.processor.builder;

import com.jmoordb.core.processor.model.RepositoryData;
import com.jmoordb.core.processor.fields.RepositoryMethod;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;

public class IndexBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder generate(RepositoryData repositoryData)">
    public static StringBuilder createIndex(RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {

           String code
                    = ProcessorUtil.editorFold("public String createIndex(Bson bson)") + "\n\n"
                    + "    @Override\n"
                    + "    public String createIndex(Bson bson) {\n"
                    + "        String result = \"\";\n"
                    + "        try {\n"
                    + "               String mongodbDatabaseValue = mongodbDatabase;\n"
                    + "               String mongodbCollectionValue = mongodbCollection;\n"
                    + "               if (!getDynamicDatabase().equals(\"\")) {\n"
                    + "                   mongodbDatabaseValue = getDynamicDatabase();\n"
                    + "                }\n"
                    + "               if (!getDynamicCollection().equals(\"\")) {\n"
                    + "                   mongodbCollectionValue = getDynamicCollection();\n"
                    + "                }\n"
                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
                    + "               setDynamicDatabase(\"\");\n"
                    + "               MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);\n"
                    + "               setDynamicCollection(\"\");\n"
                    + "               result = collection.createIndex(bson);\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "               exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return result;\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="StringBuilder generate(RepositoryData repositoryData)">
    public static StringBuilder dropIndex(RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {

           String code
                    = ProcessorUtil.editorFold("void dropIndex(Bson bson)") + "\n\n"
                    + "    @Override\n"
                    + "    public void dropIndex(Bson bson) {\n"
                                + "        try {\n"
                    + "               String mongodbDatabaseValue = mongodbDatabase;\n"
                    + "               String mongodbCollectionValue = mongodbCollection;\n"
                    + "               if (!getDynamicDatabase().equals(\"\")) {\n"
                    + "                   mongodbDatabaseValue = getDynamicDatabase();\n"
                    + "                }\n"
                    + "               if (!getDynamicCollection().equals(\"\")) {\n"
                    + "                   mongodbCollectionValue = getDynamicCollection();\n"
                    + "                }\n"
                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
                    + "               setDynamicDatabase(\"\");\n"
                    + "               MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);\n"
                    + "               setDynamicCollection(\"\");\n"
                    + "               collection.dropIndex(bson);\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "               exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
    
    
    
     // <editor-fold defaultstate="collapsed" desc="StringBuilder listIndexes(RepositoryData repositoryData">
    public static StringBuilder listIndexes(RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {

           String code
                    = ProcessorUtil.editorFold("Optional<ListIndexesIterable<Document>> listIndexes()") + "\n\n"
                    + "    @Override\n"
                    + "   public Optional<ListIndexesIterable<Document>> listIndexes(){\n"
                    + "        ListIndexesIterable<Document> result;\n"
                    + "        try {\n"
                    + "               String mongodbDatabaseValue = mongodbDatabase;\n"
                    + "               String mongodbCollectionValue = mongodbCollection;\n"
                    + "               if (!getDynamicDatabase().equals(\"\")) {\n"
                    + "                   mongodbDatabaseValue = getDynamicDatabase();\n"
                    + "                }\n"
                    + "               if (!getDynamicCollection().equals(\"\")) {\n"
                    + "                   mongodbCollectionValue = getDynamicCollection();\n"
                    + "                }\n"
                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
                    + "               setDynamicDatabase(\"\");\n"
                    + "               MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);\n"
                    + "               setDynamicCollection(\"\");\n"
                    + "               result = collection.listIndexes();\n"
                    + "               return Optional.of(result);\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "               exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return  Optional.empty();\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
     // <editor-fold defaultstate="collapsed" desc="StringBuilder listCollectionNames(RepositoryData repositoryData)">
    public static StringBuilder listCollectionNames(RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {

           String code
                    = ProcessorUtil.editorFold("Optional<MongoIterable<String>> listCollectionNames() ") + "\n\n"
                    + "    @Override\n"
                    + "    public Optional<MongoIterable<String>> listCollectionNames(){ \n"
                    + "        MongoIterable<String> result;\n"
                    + "        try {\n"
                    + "               String mongodbDatabaseValue = mongodbDatabase;\n"
                    + "               String mongodbCollectionValue = mongodbCollection;\n"
                    + "               if (!getDynamicDatabase().equals(\"\")) {\n"
                    + "                   mongodbDatabaseValue = getDynamicDatabase();\n"
                    + "                }\n"
                    + "               if (!getDynamicCollection().equals(\"\")) {\n"
                    + "                   mongodbCollectionValue = getDynamicCollection();\n"
                    + "                }\n"
                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
                    + "               setDynamicDatabase(\"\");\n"
                    + "               MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);\n"
                    + "               setDynamicCollection(\"\");\n"
                    + "               result = database.listCollectionNames();\n"
                    + "               return Optional.of(result);\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "               exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return  Optional.empty();\n"
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
