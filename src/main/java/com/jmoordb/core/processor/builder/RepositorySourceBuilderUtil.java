package com.jmoordb.core.processor.builder;

import com.jmoordb.core.annotation.enumerations.ConfigEngine;
import com.jmoordb.core.annotation.enumerations.JakartaSource;
import com.jmoordb.core.annotation.repository.Repository;
import static com.jmoordb.core.processor.builder.AutosecuenceRepositorySourceBuilder.TAB;
import com.jmoordb.core.processor.model.RepositoryData;
import com.jmoordb.core.processor.fields.RepositoryMethod;
import com.jmoordb.core.util.MessagesUtil;

public class RepositorySourceBuilderUtil {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="String numberOfParametersOfMethod(RepositoryMethod repositoryMethod)">
    /**
     *
     * @param repositoryMethod
     * @return Los parametros del metodo como una cadena
     */
    private Integer numberOfParametersOfMethod(RepositoryMethod repositoryMethod) {
        Integer number = 0;
        try {

            number = repositoryMethod.getParamTypeElement().size();

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return number;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="StringBuilder definePackage(String packageName)">
    /**
     *
     * @param packageName
     * @return inserta en package en la clase
     */
    public StringBuilder definePackage(String packageName) {
        StringBuilder builder = new StringBuilder();
        try {
            String code = "";
            if (packageName != null) {
                code = "package " + packageName + ";\n";
            }
            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="StringBuilder defineClass(String startPart, String name, String extendPart)">
    /**
     *
     * @param startPart
     * @param name
     * @param extendPart
     * @return
     */
    public StringBuilder defineClass(String name, String extendPart) {
        className = name;
        StringBuilder builder = new StringBuilder();
        try {
            String code = "public class " + name;
            if (extendPart != null) {
                code += " " + extendPart;
            }
            code += "{\n";
            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="StringBuilder addApplicationScoped()">
    /**
     *
     * @param annotation
     * @return agrega anotaciones
     */
    public StringBuilder addApplicationScoped() {
        StringBuilder builder = new StringBuilder();
        try {
            String code = "";
            builder.append("@ApplicationScoped\n");
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="StringBuilder generateImport(Repository repository, RepositoryData repositoryData)">
    public StringBuilder generateImport(Repository repository, RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {
            String code = "";

            code += "// <editor-fold defaultstate=\"collapsed\" desc=\"imports\">\n\n";
            if (repository.jakartaSource() == JakartaSource.JAVAEE_LEGACY) {
                /*
            Java EE
                 */
                code += "import javax.enterprise.context.ApplicationScoped;\n"
                        + "import javax.inject.Inject;\n"
                        + "import javax.annotation.PostConstruct;\n";

            } else {
                if (repository.jakartaSource() == JakartaSource.JAKARTA) {
                     /**
                 * Jakarta EE
                 */
                code += "import jakarta.enterprise.context.ApplicationScoped;\n"
                        + "import jakarta.inject.Inject;\n"
                        + "import jakarta.annotation.PostConstruct;\n";
                }else{
                     /**
                 * Jettra
                 */
                code += "import com.avbravo.jettraframework.cdi.ApplicationScoped;\n"
                        + "import com.avbravo.jettraframework.cdi.Inject;\n"
                        + "import com.avbravo.jettraframework.cdi.PostConstruct;\n";
                }
               

            }
            /**
             * Microprofile
             */
            if (repository.configEngine() == ConfigEngine.MICROPROFILE_CONFIG) {
                code += "import org.eclipse.microprofile.config.Config;\n"
                        + "import org.eclipse.microprofile.config.inject.ConfigProperty;\n";
            } else {
                code += "import com.avbravo.jettraframework.config.JettraConfig;\n";
            }

            code += "/**\n"
                    + "* MongoDB\n"
                    + "*/\n"
                    + "import com.jmoordb.core.processor.model.JmoordbException;\n"
                    + "import com.mongodb.client.MongoDatabase;\n"
                    + "import static com.mongodb.client.model.Filters.eq;\n"
                    + "import static com.mongodb.client.model.Filters.and;\n"
                    + "import com.mongodb.client.MongoClient;\n"
                    + "import com.mongodb.client.MongoCollection;\n"
                    + "import com.mongodb.client.MongoCursor;\n"
                    + "import org.bson.Document;\n"
                    + "import com.mongodb.client.model.FindOneAndUpdateOptions;\n"
                    + "import com.mongodb.client.model.ReturnDocument;\n"
                    + "import com.mongodb.client.result.InsertOneResult;\n"
                    + "import org.bson.BsonInt64;\n"
                    + "import org.bson.conversions.Bson;\n"
                    + "import org.bson.BsonDocument;\n"
                    + "import com.mongodb.client.model.Filters;\n"
                    + "import com.mongodb.client.result.UpdateResult;\n"
                    + "import com.mongodb.client.model.UpdateOptions;\n"
                    + "import com.mongodb.client.ListIndexesIterable;\n"
                    + "import com.mongodb.client.MongoIterable;\n"
                    + "import org.bson.types.ObjectId;\n"
                    + "/**\n"
                    + "* Java\n"
                    + "*/\n"
                    + "import java.util.ArrayList;\n"
                    + "import java.util.List;\n"
                    + "import java.util.Set;\n"
                    + "import java.util.UUID;\n"
                    + "import java.util.Optional;\n"
                    + "import java.util.function.Supplier;\n"
                    + "import com.jmoordb.core.util.MessagesUtil;\n"
                    + "import com.jmoordb.core.model.Pagination;\n"
                    + "import com.jmoordb.core.model.Sorted;\n"
                    + "import com.jmoordb.core.util.JmoordbCoreDateUtil;\n"
                    + "import java.util.HashSet;\n"
                    + "import " + repositoryData.getNameOfPackage() + repositoryData.getNameOfEntity() + ";\n\n\n"
                    + "// </editor-fold>\n";
            builder.append(code);

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="StringBuilder inject(Repository repository, RepositoryData repositoryData, String database, String collection)">
    public StringBuilder inject(Repository repository, RepositoryData repositoryData, String database, String collection) {
        StringBuilder builder = new StringBuilder();
        try {
            String code = "";

            code += "// <editor-fold defaultstate=\"collapsed\" desc=\"inject\">\n\n";
            code += "    @Inject\n"
                    + "    MongoClient mongoClient;\n";
            if (repository.configEngine() == ConfigEngine.MICROPROFILE_CONFIG) {
                code += "/**\n"
                        + "* Microprofile Config\n"
                        + "*/\n"
                        + "    @Inject\n"
                        + "    Config config;\n"
                        + "    @Inject\n"
                        + "    @ConfigProperty(name = \"" + database + "\")\n"
                        + "    String mongodbDatabase;\n\n";
            } else {
                code += "/**\n"
                        + "* Jettra Config\n"
                        + "*/\n"
                        + " String mongodbDatabase = getMicroprofileConfig(\"" + database + "\");\n";
            }

            code += "    String mongodbCollection = \"" + collection + "\";\n"
                    + "/**\n"
                    + "* AutogeneratedRepository\n"
                    + "*/\n"
                    + "    @Inject\n"
                    + "    " + repositoryData.getPackageOfRepository() + ".AutogeneratedRepository autogeneratedRepository;\n"
                    + "/**\n"
                    + "* Supplier\n"
                    + "*/\n"
                    + "    @Inject\n"
                    + "    " + repositoryData.getNameOfPackage() + repositoryData.getNameOfEntity() + "Supplier " + repositoryData.getNameOfEntityLower() + "Supplier;\n"
                    + "// </editor-fold>\n";
            builder.append(code);

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="StringBuilder exception(Repository repository, RepositoryData repositoryData, String database, String collection)">
    public StringBuilder exception(Repository repository, RepositoryData repositoryData, String database, String collection) {
        StringBuilder builder = new StringBuilder();
        try {
            String code = "";

            code += "// <editor-fold defaultstate=\"collapsed\" desc=\"Exception\">\n\n";

            code += "   private JmoordbException exception;\n"
                    + "public JmoordbException getJmoordbException() {\n"
                    + "   if(exception == null || exception.getLocalizedMessage()== null ){\n"
                    + "    exception = new JmoordbException(\"\");\n"
                    + "   }\n"
                    + "    return exception;\n"
                    + " }\n"
                    + "public void setJmoordbException(JmoordbException exception) {"
                    + "    this.exception = exception;"
                    + " }\n"
                    + "// </editor-fold>\n";
            builder.append(code);

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

// </editor-fold>
}
