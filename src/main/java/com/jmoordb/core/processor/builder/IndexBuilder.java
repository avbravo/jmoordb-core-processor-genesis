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
                    = ProcessorUtil.editorFold("public String createIndex(Bson bson)") + "\n\n";
            code+="""
                @Override
                public String createIndex(Bson bson) {
                       String result = \"\";
                       try {
                           String mongodbDatabaseValue = mongodbDatabase;
                           String mongodbCollectionValue = mongodbCollection;
                           if (!getDynamicDatabase().equals(\"\")) {
                               mongodbDatabaseValue = getDynamicDatabase();
                            }
                            if (!getDynamicCollection().equals(\"\")) {
                                mongodbCollectionValue = getDynamicCollection();
                            }
                            MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);
                            setDynamicDatabase(\"\");
                            MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);
                            setDynamicCollection(\"\");
                            result = collection.createIndex(bson);
                        } catch (Exception e) {
                           MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                           exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                        }
                        return result;
                       }\
                  // </editor-fold>;
                  """;

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
                    = ProcessorUtil.editorFold("void dropIndex(Bson bson)") + "\n\n";
            code +="""
                   
                @Override
                public void dropIndex(Bson bson) {
                try {
                     String mongodbDatabaseValue = mongodbDatabase;
                     String mongodbCollectionValue = mongodbCollection;
                     if (!getDynamicDatabase().equals(\"\")) {
                         mongodbDatabaseValue = getDynamicDatabase();
                     }
                     if (!getDynamicCollection().equals(\"\")) {
                        mongodbCollectionValue = getDynamicCollection();
                     }
                     MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);
                     setDynamicDatabase(\"\");
                     MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);
                     setDynamicCollection(\"\");
                     collection.dropIndex(bson);
                } catch (Exception e) {
                     MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                     exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                }
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
    // <editor-fold defaultstate="collapsed" desc="StringBuilder listIndexes(RepositoryData repositoryData">
    public static StringBuilder listIndexes(RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {

            String code
                    = ProcessorUtil.editorFold("Optional<ListIndexesIterable<Document>> listIndexes()") + "\n\n";
            code+="""
                   @Override
                   public Optional<ListIndexesIterable<Document>> listIndexes(){
                          ListIndexesIterable<Document> result;
                          try {
                              String mongodbDatabaseValue = mongodbDatabase;
                              String mongodbCollectionValue = mongodbCollection;
                              if (!getDynamicDatabase().equals(\"\")) {
                                  mongodbDatabaseValue = getDynamicDatabase();
                              }
                              if (!getDynamicCollection().equals(\"\")) {
                                mongodbCollectionValue = getDynamicCollection();
                              }
                              MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);
                              setDynamicDatabase(\"\");
                              MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);
                              setDynamicCollection(\"\");
                              result = collection.listIndexes();
                              return Optional.of(result);
                            } catch (Exception e) {
                              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                              exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                            }
                              return  Optional.empty();
                    }
                 //  </editor-fold>
                  """;
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
                    = ProcessorUtil.editorFold("Optional<MongoIterable<String>> listCollectionNames() ") + "\n\n";
            code += """
                 
            @Override
            public Optional<MongoIterable<String>> listCollectionNames(){
                   MongoIterable<String> result;
                   try {
                        String mongodbDatabaseValue = mongodbDatabase;
                        String mongodbCollectionValue = mongodbCollection;
                        if (!getDynamicDatabase().equals(\"\")) {
                            mongodbDatabaseValue = getDynamicDatabase();
                        }
                        if (!getDynamicCollection().equals(\"\")) {
                            mongodbCollectionValue = getDynamicCollection();
                        }
                        MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);
                        setDynamicDatabase(\"\");
                        MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);
                        setDynamicCollection(\"\");
                        result = database.listCollectionNames();
                        return Optional.of(result);
                    } catch (Exception e) {
                        MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                        exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                    }
                     return  Optional.empty();
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
