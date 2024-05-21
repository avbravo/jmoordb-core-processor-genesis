package com.jmoordb.core.processor.builder;

import com.jmoordb.core.processor.model.RepositoryData;
import com.jmoordb.core.processor.fields.RepositoryMethod;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;

public class DeleteManyBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder deleteMany(RepositoryData repositoryData)">
    /**
     * @Delete(where = "idoceano .eq. @idoceano .and. oceano .ne. @oceano .not.
     * fecha .gt. @fecha .or. activo .ne. @activo .and. km .gt. km")
     * @param repositoryData
     * @param repositoryMethod
     * @return
     */
    public static StringBuilder deleteMany(RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {

            String sentence = " Document whereCondition = new Document();\n";
            sentence += "\t\twhereCondition = search.getFilter();\n";

            sentence += "\t\tcom.mongodb.client.result.DeleteResult deleteResult = collection.deleteMany(whereCondition);\n";

            /**
             * Más de un parámetro
             */
            String code
                    = ProcessorUtil.editorFold("Long deleteMany(com.jmoordb.core.model.Search search)") + "\n\n"
                    + "    @Override\n"
                    + "    public Long deleteMany(com.jmoordb.core.model.Search search)" + "{\n"
                    + "        try {\n"
//                    + "               String mongodbDatabaseValue = mongodbDatabase;\n"
//                    + "               String mongodbCollectionValue = mongodbCollection;\n"
//                    + "               if (!getDynamicDatabase().equals(\"\")) {\n"
//                    + "                   mongodbDatabaseValue = getDynamicDatabase();\n"
//                    + "                }\n"
//                         + "               if (!getDynamicCollection().equals(\"\")) {\n"
//                    + "                   mongodbCollectionValue = getDynamicCollection();\n"
//                    + "                }\n"
//                    
//                    + "               MongoDatabase database = mongoClient.getDatabase(mongodbDatabaseValue);\n"
//                    + "               setDynamicDatabase(\"\");\n"  
//                                 + "               MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);\n"
//                    + "               setDynamicCollection(\"\");\n"
                    //                    + "               MongoCursor<Document> cursor;\n"
                   + "           MongoCollection<Document> collection = getCollection().get();\n"
                    + "               " + sentence + "\n"
                    + "               return deleteResult.getDeletedCount();\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "              exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
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
