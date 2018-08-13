package com.example.myprocessor;

import com.example.great_annotations.ZSCAnnotation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by zhoushicheng on 2018/6/17.
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class MyProcessor extends AbstractProcessor {

    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(ZSCAnnotation.class.getCanonicalName());
        return annotations;
    }

    //    @Override
    //    public SourceVersion getSupportedSourceVersion() {
    //        return SourceVersion.RELEASE_7;
    //    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 遍历所有被注解了@ZSCAnnotation的元素
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(ZSCAnnotation.class)) {
            //检查被注解为@Factory的元素是否是一个类
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s",
                        ZSCAnnotation.class.getSimpleName());
                return true; // 退出处理
            }

            //解析，并生成代码
            analysisAnnotated(annotatedElement);
        }

        return false;
    }

    private void error(Element e, String msg, Object... args) {
        mMessager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }

    private static final String SUFFIX = "$tuogusa";

    private void analysisAnnotated(Element classElement) {
        ZSCAnnotation annotation = classElement.getAnnotation(ZSCAnnotation.class);
        String name = annotation.name();
        String text = annotation.text();

        //        TypeElement superClassName = mElementUtils.getTypeElement(name);
        String newClassName = name + SUFFIX;

        //        StringBuilder builder = new StringBuilder()
        //                .append("package com.example.zhoushicheng.myapplication.CodeAutoCreateSample;\n\n")
        //                .append("public class ")
        //                .append(newClassName)
        //                .append(" {\n\n") // open class
        //                .append("\tpublic String getMessage() {\n") // open method
        //                .append("\t\treturn \"");
        //
        //        // this is appending to the return statement
        //        builder.append(text).append(name).append(" !\\n");
        //
        //
        //        builder.append("\";\n") // end return
        //                .append("\t}\n") // close method
        //                .append("}\n"); // close class
        //
        //
        //        try { // write the file
        //            JavaFileObject source = mFiler.createSourceFile("com.example.zhoushicheng.myapplication.CodeAutoCreateSample."+newClassName);
        //            Writer writer = source.openWriter();
        //            writer.write(builder.toString());
        //            writer.flush();
        //            writer.close();
        //        } catch (IOException e) {
        //            // Note: calling e.printStackTrace() will print IO errors
        //            // that occur from the file already existing after its first run, this is normal
        //        }

        MethodSpec go = MethodSpec.methodBuilder("go")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        StringBuilder methodBuilder = new StringBuilder();
        methodBuilder.append("\tHashMap<String, String> map = new HashMap<>();")
                .append("map.put(\"abc\",\"ABC\");")
                .append("\n").append("return map");

        MethodSpec getParams = MethodSpec.methodBuilder("getParams")
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(HashMap.class, String.class, String.class))
                .addStatement(methodBuilder.toString())
                .build();


        ClassName templateName = ClassName.get("com.example.zhoushicheng.myapplication.CodeAutoCreateSample", "Template");

        final TypeSpec target =
                TypeSpec.classBuilder(newClassName)
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.FINAL)
                        .addMethod(go)
                        .addMethod(getParams)
                        .superclass(templateName)
                        .build();

        final JavaFile javaFile = JavaFile.builder("com.example.zhoushicheng.myapplication.CodeAutoCreateSample", target)
                .addFileComment("自动生成tuogusa")
                .build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //        info(">>> analysisAnnotated is finish... <<<");
    }


}
