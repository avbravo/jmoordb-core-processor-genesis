package com.jmoordb.core.processor.builder;

import com.jmoordb.core.processor.model.RepositoryData;
import com.jmoordb.core.processor.fields.RepositoryMethod;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;

public class CountBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder count(RepositoryData repositoryData)">
    public static StringBuilder count(RepositoryData repositoryData, RepositoryMethod repositoryMethod) {
        StringBuilder builder = new StringBuilder();
        try {
            ;

            String param = ProcessorUtil.parametersOfMethod(repositoryMethod);

            String code
                    = ProcessorUtil.editorFold(repositoryMethod, param) + "\n\n"
                    + "    @Override\n"
                    + "    public " + repositoryMethod.getReturnTypeValue() + " " + repositoryMethod.getNameOfMethod() + "(" + param + ") {\n"
                    + "        Long contador = 0L;\n"
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
//                    + "               MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);\n"
//                    + "               setDynamicCollection(\"\");\n"
                    + "           MongoCollection<Document> collection = getCollection().get();\n"
                    + "               Document whereCondition = new Document();\n"
                    + "               if (search.length != 0) {\n"
                    + "                  whereCondition = search[0].getFilter();\n"
                    + "               } \n"
                    + "               if (whereCondition.isEmpty()) {\n"
                    + "                  contador = collection.countDocuments();\n"
                    + "               } else {\n"
                    + "                  contador = collection.countDocuments(whereCondition);\n"
                    + "               }\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "              exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return contador;\n"
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
