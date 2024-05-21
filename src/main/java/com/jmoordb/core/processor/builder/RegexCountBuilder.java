package com.jmoordb.core.processor.builder;

import com.jmoordb.core.annotation.enumerations.CaseSensitive;
import static com.jmoordb.core.annotation.enumerations.LikeByType.ANYWHERE;
import static com.jmoordb.core.annotation.enumerations.LikeByType.FROMTHEEND;
import static com.jmoordb.core.annotation.enumerations.LikeByType.FROMTHESTART;
import com.jmoordb.core.processor.model.RepositoryData;
import com.jmoordb.core.processor.fields.RepositoryMethod;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;

public class RegexCountBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder regexCount(RepositoryData repositoryData)">
    public static StringBuilder regexCount(RepositoryData repositoryData, RepositoryMethod repositoryMethod) {
        StringBuilder builder = new StringBuilder();
        try {
            /**
             * Campo se obtiene del token
             */
            String field = repositoryMethod.getTokenWhere().get(0);
            /**
             * Obtengo el nombre del pàrametro que tiene el valor del token a
             * partir de la posicion 1. Es decir elimino el @
             */
            String valueParam = repositoryMethod.getTokenWhere().get(2).substring(1);

            String param = ProcessorUtil.parametersOfMethod(repositoryMethod);

            String sentence = "";
            if (repositoryMethod.getCaseSensitive().equals(CaseSensitive.YES)) {
                switch (repositoryMethod.getLikeByType()) {
                    case FROMTHESTART:
                        sentence = "contador = collection.countDocuments(new Document(\"" + field + "\", new Document(\"$regex\", \"^\"+" + valueParam + ")));";
                        break;
                    case FROMTHEEND:
                        sentence = "contador = collection.countDocuments(new Document(\"" + field + "\", new Document(\"$regex\", " + valueParam + " + \"$\" )));";
                        break;
                    case ANYWHERE:
                        sentence = "contador = collection.countDocuments(new Document(\"" + field + "\", new Document(\"$regex\", " + valueParam + ")));";
                        break;

                }

            } else {
                switch (repositoryMethod.getLikeByType()) {
                    case FROMTHESTART:
                        sentence = "contador = collection.countDocuments(new Document(\"" + field + "\", new Document(\"$regex\", \"^\"+" + valueParam + ").append(\"$options\", \"i\")));";
                        break;
                    case FROMTHEEND:
                        sentence = "contador = collection.countDocuments(new Document(\"" + field + "\", new Document(\"$regex\", " + valueParam + "+ \"$\" ).append(\"$options\", \"i\")));";
                        break;
                    case ANYWHERE:
                        sentence = "contador = collection.countDocuments(new Document(\"" + field + "\", new Document(\"$regex\", " + valueParam + ").append(\"$options\", \"i\")));";
                        break;

                }

            }
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
//                    + "             MongoCollection<Document> collection = database.getCollection(mongodbCollectionValue);\n"
//                    + "            setDynamicCollection(\"\");\n"
                    + "           MongoCollection<Document> collection = getCollection().get();\n"
                    + "             " + sentence + "\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "               exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
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
