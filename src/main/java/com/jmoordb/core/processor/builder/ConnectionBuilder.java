package com.jmoordb.core.processor.builder;

import com.jmoordb.core.processor.model.RepositoryData;
import com.jmoordb.core.processor.fields.RepositoryMethod;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.Optional;

public class ConnectionBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder generate(RepositoryData repositoryData)">
    public static StringBuilder getCollection(RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {
            String code
                    = ProcessorUtil.editorFold("public Optional<MongoCollection<Document>> getCollection()") + "";
            code += """
             
             @Override
              public Optional<MongoCollection<Document>> getCollection() {
             
                     try {
                         String mongodbDatabaseValue = mongodbDatabase;
                         String mongodbCollectionValue = mongodbCollection;
                         if (!getDynamicDatabase().equals("")) {
                             mongodbDatabaseValue = getDynamicDatabase();
                         }
                         if (!getDynamicCollection().equals("")) {
                             mongodbCollectionValue = getDynamicCollection();
                         }
                         MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);
                         setDynamicDatabase("");
                         MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);
                         setDynamicCollection("");
                         return Optional.of(collection);
                     } catch (Exception e) {
                         MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
                         exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
                     }
                     return Optional.empty();
                 }
            // </editor-fold
             """;



            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
}
