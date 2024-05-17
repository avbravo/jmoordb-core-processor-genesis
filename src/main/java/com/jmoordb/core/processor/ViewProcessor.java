package com.jmoordb.core.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import com.jmoordb.core.annotation.view.View;

import static com.jmoordb.core.annotation.app.MyAnnotationTypeProcessor.mirror;
import com.jmoordb.core.processor.model.view.ViewData;
import com.jmoordb.core.processor.model.view.ViewDataSupplier;
import com.jmoordb.core.processor.builder.view.ViewSourceBuilder;
import com.jmoordb.core.processor.analizer.view.ViewAnalizer;
import com.jmoordb.core.processor.methods.view.ViewMethod;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.io.File;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

@SupportedAnnotationTypes(
        {"com.jmoordb.core.annotation.view.View"})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class ViewProcessor extends AbstractProcessor {

    private Messager messager;
    private ViewDataSupplier viewDataSupplier = new ViewDataSupplier();

    // <editor-fold defaultstate="collapsed" desc=" boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)">
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            MessagesUtil.box("Iniciando proceso de analisis de @View");

            if (annotations.size() == 0) {
                return false;
            }
            /**
             * Lee los elementos que tengan la anotacion @View
             */
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(View.class);

            List<String> uniqueIdCheckList = new ArrayList<>();

            for (Element element : elements) {
                View view = element.getAnnotation(View.class);

                /*
                Busca el archivo index.html
                 */
                System.out.println("Quitar-->===========@ViewProcessor==============");
                Filer filer = processingEnv.getFiler();
                FileObject fileObject = filer.getResource(StandardLocation.CLASS_OUTPUT, "", "index.html");
                File extensionsFile = new File(fileObject.toUri());
                System.out.println("Quitar--> extensionsFile >> " + extensionsFile);
                System.out.println("Quitar--> fileObject.getName()>> " + fileObject.getName());
                System.out.println("Quitar--> fileObject.toUri()>> " + fileObject.toUri());

                System.out.println("Quitar--> =====================r==============");

                /**
                 * Evalua la entidad definida en el
                 *
                 * @View(entity=MyEntity.class)
                 */
                TypeMirror typeEntity = mirror(view::entity);
                if (typeEntity == null) {
//                     error("Error processing the view entity",   element);

                }
                if (element.getKind() != ElementKind.CLASS) {

                    error("The annotation @View can only be applied on Java Class",
                            element);

                } else {
                    boolean error = false;
                    /**
                     * Obtener datos del view para ViewData
                     */

                    ViewData viewData = viewDataSupplier.get(ViewData::new, element);

                    if (uniqueIdCheckList.contains(viewData.getNameOfEntity())) {
                        error("View has should be uniquely defined", element);
                        error = true;
                    }

                    error = !checkIdValidity(viewData.getNameOfEntity(), element);
                    if (!error) {
                        uniqueIdCheckList.add(viewData.getNameOfEntity());
                        try {

                            builderClass(view, viewData, element, typeEntity);

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
// <editor-fold defaultstate="collapsed" desc="builderClass(View view, ViewData viewData,Element element, TypeMirror typeEntity)">
    private void builderClass(View view, ViewData viewData, Element element, TypeMirror typeEntity)
            throws Exception {
        try {

            Boolean generate = view.generate();
        

            /**
             * List<ViewMethod> almacena la información de los métodos de los
             * repositorios
             */
            List<ViewMethod> viewMethodList = new ArrayList<>();
            /**
             *
             * Procesa el contenido de la interface
             */
            ViewAnalizer viewAnalizer = ViewAnalizer.get(element, messager, generate, typeEntity, viewMethodList);

            /**
             * Construye la clase
             */
            ViewSourceBuilder viewSourceBuilder = new ViewSourceBuilder();

            viewSourceBuilder.init(view, viewData, viewMethodList);

            generateJavaFile(viewData.getPackageOfView() + "." + viewData.getInterfaceName() + "Impl", viewSourceBuilder.end());
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
                    error("View as should be valid java "
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

        // get messager for printing errors
        messager = processingEnvironment.getMessager();
    }
// </editor-fold>    

// <editor-fold defaultstate="collapsed" desc="printError(Element element, String message)">
    private void printError(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
// </editor-fold>
}
