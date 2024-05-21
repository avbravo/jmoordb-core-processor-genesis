package com.jmoordb.core.processor.builder;

import com.jmoordb.core.processor.model.RepositoryData;
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
                    = ProcessorUtil.editorFold("Long updateMany(Bson query, Bson update)") + "\n\n";
            code +="""
                   
                @Override
                public Long updateMany(Bson query, Bson update) {
                       try {

                           MongoCollection<Document> collection = getCollection().get();
                           
                           UpdateResult result = collection.updateMany(query, update);
                           return result.getModifiedCount();
                         } catch (Exception e) {
                           MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                           exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                         }
                          return 0L;
                }
             // </editor-fold>
              """;
            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
}
