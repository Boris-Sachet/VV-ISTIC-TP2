package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;


public class NoPublicGettersWithPrivateElements extends VoidVisitorWithDefaults<Void> {
    private final String resultPath;

    public NoPublicGettersWithPrivateElements(String path) {
        this.resultPath = path;
    }

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {

        // Create result file
        String fileName = resultPath + "Output.txt";
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("Created file : " + file.getName());
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!declaration.isPublic()) return;

        List<String> variablesList = new ArrayList<>();
        List<String> listOfVariables = new ArrayList<>();

        // Parse all the fields to find the private ones
        for (FieldDeclaration fieldDeclaration : declaration.getFields()) {
            if (fieldDeclaration.isPrivate()) {
                listOfVariables.add(fieldDeclaration.getVariable(0).getName().toString());
            }
        }


        if (!listOfVariables.isEmpty()) {
            variablesList = new ArrayList<>(listOfVariables);
            System.out.println("Checking any private fields without getters : ");
            for (String variable : listOfVariables) {
                for (MethodDeclaration method : declaration.getMethods()) {
                    if (method.getNameAsString().equalsIgnoreCase("get" + variable)) {
                        variablesList.remove(variable);
                    }
                }
            }
        }

        if (!variablesList.isEmpty()) {
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write("The analysis revealed " + variablesList.size() + " privates variables " +
                        "without getters : \n");
                for (String result : variablesList) {
                    fileWriter.write(result + "\n");
                }
                fileWriter.close();
                System.out.println("File written.");
            } catch (IOException e) {
                System.out.println("Error I/O during writing or closing.");
                e.printStackTrace();
            }
        }
    }

        @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        if(!declaration.isPublic()) return;
    }
}