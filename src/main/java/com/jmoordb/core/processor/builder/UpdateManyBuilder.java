package com.jmoordb.core.processor.builder;

import com.jmoordb.core.processor.model.RepositoryData;
import com.jmoordb.core.processor.fields.RepositoryMethod;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;

public class UpdateManyBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder updateMany(RepositoryData repositoryData, RepositoryMethod repositoryMethod)">
    public static StringBuilder updateMany(RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {

            String calculateReturn = "";
            String catchReturn = "";

            String code
                    = ProcessorUtil.editorFold("Long updateMany(Bson query, Bson update)") + "\n\n"
                    + "    @Override\n"
                    + "    public Long updateMany(Bson query, Bson update) {\n"
                    + "        try {\n"
                    + "               String mongodbDatabaseValue = mongodbDatabase;\n"
                    + "               String mongodbCollectionValue = mongodbCollection;\n"
                    + "               if (!getDynamicDatabase().equals(\"\")) {\n"
                    + "                   mongodbDatabaseValue = getDynamicDatabase();\n"
                    + "                }\n"
                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
                    + "               setDynamicDatabase(\"\");\n"  
                    + "               MongoCollection<Document> collection = database.getCollection(mongodbCollection);\n"
                    + "               UpdateResult result = collection.updateMany(query, update);\n"
                    + "               return result.getModifiedCount();\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "               exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return 0L;\n"
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
