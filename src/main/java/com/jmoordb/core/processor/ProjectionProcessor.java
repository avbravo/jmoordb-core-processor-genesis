package com.jmoordb.core.processor;

import com.jmoordb.core.annotation.Projection;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.*;

import com.jmoordb.core.processor.model.ProjectionData;
import com.jmoordb.core.processor.model.ProjectionDataSupplier;
import com.jmoordb.core.processor.projection.supplier.ProjectionSupplierSource;
import com.jmoordb.core.processor.projection.analizer.ProjectionAnalizer;
import com.jmoordb.core.processor.methods.ProjectionField;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.io.IOException;
import java.io.Writer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes(
        {"com.jmoordb.core.annotation.Projection"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class ProjectionProcessor extends AbstractProcessor {

    private Messager messager;
    private ProjectionDataSupplier projectionDataSupplier = new ProjectionDataSupplier();

    // <editor-fold defaultstate="collapsed" desc=" boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)">
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            MessagesUtil.box("Iniciando proceso de analisis de @Projection");

            if (annotations.size() == 0) {
                return false;
            }
            /**
             * Lee los elementos que tengan la anotacion @Projection
             */
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Projection.class);

            List<String> uniqueIdCheckList = new ArrayList<>();

            for (Element element : elements) {
                Projection projection = element.getAnnotation(Projection.class);

                if (element.getKind() != ElementKind.CLASS) {

                    error("The annotation @Projection can only be applied on class: ",
                            element);

                } else {
                    boolean error = false;
                    /**
                     * Obtener datos del projection para ProjectionData
                     */

                    ProjectionData projectionData = projectionDataSupplier.get(ProjectionData::new, element);
                    String nameOfProjection = "";



                    if (uniqueIdCheckList.contains(projectionData.getCollection())) {
                        error("Projection has should be uniquely defined", element);
                        error = true;
                    }

                    error = !checkIdValidity(projectionData.getCollection(), element);
                    if (!error) {
                        uniqueIdCheckList.add(projectionData.getCollection());
                        try {

                            builderClass(projection, projectionData, element);

                        } catch (Exception e) {
                            error(e.getMessage(), null);
                        }
                    }
                }
            }
            MessagesUtil.box("Proceso de analisis finalizado");
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());

        }
        return false;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="builderClass(Projection projection, ProjectionData projectionData,Element element, TypeMirror typeProjection)">
    private void builderClass(Projection projection, ProjectionData projectionData, Element element)
            throws Exception {
        try {

            /**
             *
             */

            /**
             * List<ProjectionField almacena la información de los atributos de los
             * projection
             */
            List<ProjectionField> projectionFieldList = new ArrayList<>();
            /**
             *
             * Procesa el contenido de la interface
             */
            ProjectionAnalizer projectionAnalizer = ProjectionAnalizer.get(element, messager, projectionData.getDatabase(), projectionFieldList, projectionData);


            /**
             * Construye la clase Supplier
             */
            ProjectionSupplierSource projectionSupplierSourceBuilder = new ProjectionSupplierSource();

            projectionSupplierSourceBuilder.init(projection, projectionData, projectionFieldList, projectionData.getDatabase(), projectionData.getCollection(),element);

            /**
             * SupplierServices
             */
            /**
             * Crea el archivo
             */
            generateJavaFile(projectionData.getPackageOfProjection() + "." + projectionData.getProjectionName() + "Supplier", projectionSupplierSourceBuilder.end());

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="generateJavaFile(String qfn, String end)">
    private void generateJavaFile(String qfn, String end) throws IOException {
        try {

            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(qfn);
            Writer writer = sourceFile.openWriter();
            writer.write(end);
            writer.close();

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="boolean checkIdValidity(String name, Element e)">
    /**
     * Checking if the class to be generated is a valid java identifier Also the
     * name should be not same as the target interface
     */
    private boolean checkIdValidity(String name, Element e) {
        boolean valid = true;
        try {

            for (int i = 0; i < name.length(); i++) {
                if (i == 0 ? !Character.isJavaIdentifierStart(name.charAt(i))
                        : !Character.isJavaIdentifierPart(name.charAt(i))) {
                    error("Projection as should be valid java "
                            + "identifier for code generation: " + name, e);

                    valid = false;
                }
            }
            if (name.equals(ProcessorUtil.getTypeName(e))) {
                error("as should be different than the Interface name ", e);
            }
        } catch (Exception ex) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + ex.getLocalizedMessage());

        }
        // testMsg("valid = " + valid, false);
        return valid;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="error(String msg, Element e)">
    private void error(String msg, Element e) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e);
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="init(ProcessingEnvironment processingEnvironment)">
    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        messager = processingEnvironment.getMessager();
    }
// </editor-fold>    
// <editor-fold defaultstate="collapsed" desc="printError(Element element, String message)">

    private void printError(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
// </editor-fold>

}
